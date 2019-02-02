package com.whli.jee.core.web.interceptor;

import com.whli.jee.core.constant.SysConstants;
import com.whli.jee.core.page.Page;
import com.whli.jee.core.util.*;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Created by whli on 2018/1/19.
 */
@Intercepts({
    @Signature(type = StatementHandler.class, method = "prepare",
            args = {Connection.class,Integer.class})
})
public class PageInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String dialect;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info("进入 PageInterceptor 拦截器...");
        //是通过Interceptor的plugin方法进行包裹的，所以我们这里拦截到的目标对象肯定是RoutingStatementHandler对象。
        RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget();
        //通过反射获取到当前RoutingStatementHandler对象的delegate属性,mappedStatement属性
        StatementHandler delegate = (StatementHandler) ReflectUtils.getFieldValue(statementHandler, "delegate");
        MappedStatement mappedStatement = (MappedStatement)ReflectUtils.getFieldValue(delegate, "mappedStatement");

        //获取连接
        Connection connection = (Connection)invocation.getArgs()[0];

        //RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法
        BoundSql boundSql = statementHandler.getBoundSql();

        String sqlId = mappedStatement.getId();
        //拿到当前绑定Sql的参数对象，就是我们在调用对应的Mapper映射语句时所传入的参数对象
        Object obj = boundSql.getParameterObject();

        //增加系统操作日志
        addSysLog(connection,sqlId,boundSql,mappedStatement.getConfiguration());

        //这里我们简单的通过传入的是Page对象就认定它是需要进行分页操作的。
        Page page = findPageableObject(obj);
        if (page != null && page.getPageSize() != -1){
            logger.info("执行分页查询...");
            String sql = boundSql.getSql();
            String pageSql = sql;
            //获取总数量
            setTotalRecord(page, boundSql, mappedStatement, connection);
            //设置分页语句
            pageSql = getPageSql(page, sql);
            //利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
            ReflectUtils.setFieldValue(boundSql, "sql", pageSql);
        }
        logger.info("结束 PageInterceptor 拦截器...");
        return invocation.proceed();
    }

    //判断是否分页
    private Page findPageableObject(Object params) {
        if (params != null) {
            HashMap<String,Object> results = (HashMap<String, Object>) params;

            for(Object value : results.values()){
                if(value instanceof Page){
                    Page page = (Page)value;
                    return page;
                }
            }
        }
        return null;
    }

    //查询数据总数
    private void setTotalRecord(Page page, BoundSql boundSql, MappedStatement mappedStatement, Connection connection){
        String sql = boundSql.getSql();

        String countSql = getCountSql(sql);

        List parameterMappings = boundSql.getParameterMappings();

        Object parameterObject = boundSql.getParameterObject();

        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, parameterObject);

        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBoundSql);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);

            parameterHandler.setParameters(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);

                page.setTotal(totalRecord);
            }
        } catch (Exception e) {
            logger.error("setTotalRecord;bug:{}", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null)
                    pstmt.close();
            }
            catch (Exception e) {
                logger.error("setTotalRecord;bug:{}", e);
            }
        }
    }

    //根据数据库类型拼接查询总数sql语句
    private String getCountSql(String sql){
        if ("sqlServer".equalsIgnoreCase(dialect)) {
            StringBuilder countSql = new StringBuilder(sql);
            int distinct = countSql.toString().toUpperCase().indexOf("DISTINCT");
            if (distinct > 0) {
                if (distinct < 20)
                    countSql = countSql.insert(distinct + 8, " top 100 percent ");
                else
                    countSql = countSql.insert(7, " top 100 percent ");
            }
            else {
                countSql = countSql.insert(7, " top 100 percent ");
            }
            return new StringBuilder().append("select count(1) from (").append(countSql.toString()).append(") tmp").toString();
        }
        return new StringBuilder().append("select count(1) from (").append(sql).append(") tmp").toString();
    }

    //根据dialect获取相应分页语句
    private String getPageSql(Page page, String sql){
        StringBuilder sqlBuffer = new StringBuilder(sql);
        String resultSql = null;
        if ("mysql".equalsIgnoreCase(dialect))
            resultSql = getMysqlPageSql(page, sqlBuffer);
        else if ("oracle".equalsIgnoreCase(dialect))
            resultSql = getOraclePageSql(page, sqlBuffer);
        else if ("sqlServer".equalsIgnoreCase(dialect)) {
            resultSql = getSqlServerPageSql(page, sqlBuffer);
        }
        return resultSql;
    }

    //mysql分页语句
    private String getMysqlPageSql(Page page, StringBuilder sqlBuffer)
    {
        int offset = (page.getCurrentPage() - 1) * page.getPageSize();
        if (offset < 0)
            sqlBuffer.append(" limit ").append(0).append(",").append(0);
        else {
            sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        }
        return sqlBuffer.toString();
    }

    //oracle分页语句
    private String getOraclePageSql(Page page, StringBuilder sqlBuffer)
    {
        int offset = (page.getCurrentPage() - 1) * page.getPageSize() + 1;
        sqlBuffer.insert(0, "select u.*,rownum r from (").append(") u where rownum < ").append(offset + page.getPageSize());
        sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
        return sqlBuffer.toString();
    }

    //mssql分页语句
    private String getSqlServerPageSql(Page page, StringBuilder sqlBuffer)
    {
        int offset = (page.getCurrentPage() - 1) * page.getPageSize();
        if (offset < 0)
            sqlBuffer.append(" offset ").append(0).append(",").append(0);
        else {
            sqlBuffer.append(" offset ").append(offset).append(" rows fetch next ").append(page.getPageSize()).append(" rows only ");
        }
        return sqlBuffer.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        setDialect(properties.getProperty("dialect"));
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        if(StringUtils.isNullOrBlank(dialect)) {
            this.dialect = "mysql";
        }
        this.dialect = dialect.toLowerCase();
    }

    /**
     * 保存系统操作日志
     * @author whli
     * @param connection
     */
    @Async
    void addSysLog(Connection connection,String sqlId,BoundSql boundSql,Configuration configuration) {
        if (StringUtils.isNullOrBlank(sqlId)){
            return;
        }

        HttpServletRequest request = WebUtils.getRequest();
        if (request == null){
            return;
        }

        String type = "";

        sqlId = sqlId.toLowerCase();

        if (sqlId.indexOf("add") != -1 || sqlId.indexOf("insert") != -1 || sqlId.indexOf("create") != -1
                || sqlId.indexOf("new ") != -1){
            type = "ADD";
        }else if (sqlId.indexOf("update") != -1 || sqlId.indexOf("modify") != -1){
            type = "UPDATE";
        }else if (sqlId.indexOf("delete") != -1 || sqlId.indexOf("del") != -1
                || sqlId.indexOf("remove") != -1){
            type = "DELETE";
        }

        if(StringUtils.isNullOrBlank(type)){
            return;
        }

        PreparedStatement pstmt = null;
        try {
            String msg = getParameterObject(boundSql,configuration);
            String sysLogSql = "insert into tl_sys_log (tl_sys_log_id,type,table_name,operation_detail,request_uri,ip,host_name,create_by,create_date) values(?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sysLogSql);
            pstmt.setString(1, BeanUtils.getUUID());
            pstmt.setString(2,type);
            pstmt.setString(3,boundSql.getSql());
            pstmt.setString(4,msg);
            pstmt.setString(5,WebUtils.getRequestURI(request));
            pstmt.setString(6,WebUtils.getRemoteIp(request));
            pstmt.setString(7,request.getLocalName());
            pstmt.setString(8,WebUtils.getLoginName(request));
            pstmt.setTimestamp(9,new Timestamp(SysConstants.currentTimeMillis));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("addSysLog;bug:{}", e);
        } finally {
            try{
                if (pstmt != null){
                    pstmt.close();
                }
            }catch (Exception e){
                logger.error("addSysLog;bug:{}", e);
            }
        }
    }

    /**
     * 组合参数及值
     * @param boundSql
     * @return
     */
    private String getParameterObject(BoundSql boundSql, Configuration configuration){
        String msg = "{";
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        if (parameterMappings != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    if(value != null){
                        //判断value类型并做相应处理
                        if(value instanceof Date){
                            value = DateUtils.dateToString((Date) value,DateUtils.DEFAULT_YYYY_MM_DD_HH_MM_SS);
                        }else {
                            value = value.toString();
                        }
                    }

                    msg += value+", ";
                }
            }
        }
        msg = msg.substring(0, msg.lastIndexOf(",")) + "}";
        return msg;
    }
}
