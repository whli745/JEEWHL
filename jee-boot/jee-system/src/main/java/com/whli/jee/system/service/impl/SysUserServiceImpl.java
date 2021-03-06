package com.whli.jee.system.service.impl;

import com.whli.jee.core.cache.RedisConfig;
import com.whli.jee.core.constant.SysConstants;
import com.whli.jee.core.exception.BusinessException;
import com.whli.jee.core.util.*;
import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysUserDao;
import com.whli.jee.system.dao.ISysUserRoleDao;
import com.whli.jee.system.entity.SysRole;
import com.whli.jee.system.entity.SysUser;
import com.whli.jee.system.entity.SysUserRole;
import com.whli.jee.system.service.ISysRoleService;
import com.whli.jee.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@Service(value = "sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements ISysUserService{

    @Autowired
    private ISysUserDao sysUserDao;
    @Autowired
    private ISysUserRoleDao sysUserRoleDao;
    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public IBaseDao<SysUser> getDao() {
        return sysUserDao;
    }

    @Override
    public int save(SysUser entity) {
        if (StringUtils.isBlank(entity.getLoginName()) || StringUtils.isBlank(entity.getEmail())
                || StringUtils.isBlank(entity.getPhone())){
            throw new BusinessException("用户名、邮箱、联系方式不能为空！");
        }

        SysUser temp = getByLoginNameOrEmailOrPhone(entity.getLoginName());
        if (BeanUtils.isNotNull(temp)) {
            throw new BusinessException("【"+entity.getLoginName()+"】该用户已存在！");
        }

        temp = getByLoginNameOrEmailOrPhone(entity.getEmail());
        if (BeanUtils.isNotNull(temp)) {
            throw new BusinessException("【"+entity.getEmail()+"】邮箱已被其他用户绑定！");
        }

        temp = getByLoginNameOrEmailOrPhone(entity.getPhone());
        if (BeanUtils.isNotNull(temp)) {
            throw new BusinessException("【"+entity.getPhone()+"】联系方式已被其他用户绑定！");
        }

        entity.setEnable(1);
        entity.setPassword(PwdUtils.md5Encode("123456", entity.getLoginName()));
        int rows = super.save(entity);
        //设置默认角色USER
        if (rows > 0){
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId("2");
            userRole.setUserId(entity.getId());
            sysUserRoleDao.save(userRole);
        }

        return rows;
    }

    @Override
    public void delete(SysUser entity) {
        if (entity == null || entity.getId() == null){
            throw new BusinessException("修改数据不能为空！");
        }
        SysUser user = sysUserDao.getByPK(entity.getId());
        user.setEnable(0);
        sysUserDao.update(user);
    }

    @Override
    public void deleteMore(SysUser entity) {
        if (entity == null || CollectionUtils.isEmpty(entity.getIds())){
            throw new BusinessException("修改数据不能为空！");
        }
        List<SysUser> users = sysUserDao.listByPKs(entity.getIds());
        for (SysUser user : users){
            user.setEnable(0);
            sysUserDao.update(user);
        }
    }

    /**
     * @Desc 用户登录
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 0:20
     * @Params [loginName, password]
     * @Return java.lang.String
     */
    @Override
    public SysUser login(String loginName, String password) {
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)){
            throw new BusinessException("用户名或密码不能为空！");
        }

        SysUser sysUser = getByLoginNameOrEmailOrPhone(loginName);
        if (BeanUtils.isNull(sysUser)){
            throw new BusinessException("用户名或密码错误！");
        }

        if (!sysUser.getPassword().equals(PwdUtils.md5Encode(password,loginName))){
            throw new BusinessException("用户名或密码错误！");
        }

        Integer enabled = sysUser.getEnable();
        if (BeanUtils.isNull(enabled) || enabled.compareTo(0) == 0){
            throw new BusinessException("该用户禁止登录，请联系管理人员！");
        }
        String token = BeanUtils.getUUID();

        if (StringUtils.isNotBlank(RedisConfig.clusterNodes)){
            JedisClusterUtils.hSet(token, SysConstants.LOGIN_NAME, sysUser.getLoginName());
            JedisClusterUtils.hSet(token, SysConstants.LOGIN_USERID, sysUser.getId());
            JedisClusterUtils.expireDefault(token);
        }else {
            JedisUtils.hSet(token, SysConstants.LOGIN_NAME, sysUser.getLoginName());
            JedisUtils.hSet(token, SysConstants.LOGIN_USERID, sysUser.getId());
            JedisUtils.expireDefault(token);
        }

        sysUser.setToken(token);
        sysUser.setPassword("");
        return sysUser;
    }

    /**
     * @Desc 用户退出登录
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 0:20
     * @Params [token]
     * @Return java.lang.String
     */
    @Override
    public boolean logout(HttpServletRequest request) {
        String token = request.getHeader(SysConstants.AUTHORIZATION);
        if (StringUtils.isNotBlank(RedisConfig.clusterNodes)){
            return JedisClusterUtils.delete(token);
        }
        return JedisUtils.delete(token);
    }

    /**
     * 根据登录名检查用户是否存在
     *
     * @param loginName
     * @return
     */
    @Override
    public SysUser getByLoginNameOrEmailOrPhone(String loginName) {
        SysUser currentUser = null;
        if (StringUtils.isNotBlank(loginName)) {
            currentUser = sysUserDao.getByLoginNameOrEmailOrPhone(loginName);
        }
        return currentUser;
    }

    @Override
    public SysUser getByEmail(String email) {
        if(StringUtils.isBlank(email)){
            throw new BusinessException("请选择需要查询的数据！");
        }
        SysUser entity = sysUserDao.getByEmail(email);
        if(BeanUtils.isNull(entity)){
            throw  new BusinessException("查询的数据不存在或已删除！");
        }
        return entity;
    }

    @Override
    public SysUser getByPhone(String phone) {
        if(StringUtils.isBlank(phone)){
            throw new BusinessException("请选择需要查询的数据！");
        }
        SysUser entity = sysUserDao.getByPhone(phone);
        if(BeanUtils.isNull(entity)){
            throw  new BusinessException("查询的数据不存在或已删除！");
        }
        return entity;
    }

    @Override
    public int grantRolesByUser(String userId, List<String> roleIds) {

        if (StringUtils.isBlank(userId)){
            throw new BusinessException("请选择需要授权的用户！");
        }

        int rows = 0;
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<SysUserRole> userRoles = new ArrayList<SysUserRole>();
            for (String roleId : roleIds) {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                List<SysUserRole> sysUserRoles = sysUserRoleDao.listAll(userRole);
                if (CollectionUtils.isNotEmpty(sysUserRoles)){
                    SysRole role = sysRoleService.getByPK(roleId);
                    throw new BusinessException("角色【"+role.getName()+"】已存在！");
                }
                userRoles.add(userRole);
                sysUserRoleDao.save(userRole);
                rows++;
            }
        }
        return rows;
    }

    @Override
    public int importExcel(File file) {
        return super.importExcel(file);
    }

    @Override
    public int resetPassword(SysUser entity) {
        int rows = 0;
        if (CollectionUtils.isEmpty(entity.getIds())){
            throw new BusinessException("请选择需要重置密码的用户！");
        }
        for (String id : entity.getIds()){
            SysUser user = getByPK(id);
            sysUserDao.resetPassword(id,PwdUtils.md5Encode("123456",user.getLoginName()));
            rows++;
        }

        return rows;
    }

    //@Transactional
    @Override
    public int importExcel(InputStream stream) {
        int rows = 0;
        try {
            List<SysUser> users = ExcelUtils.importExcel(stream,SysUser.class,new String[]{"loginName",
                    "no","name","email","phone"});
            if (CollectionUtils.isNotEmpty(users)){
                for (SysUser entity : users){
                    rows += this.save(entity);
                }

            }
            return rows;
        } catch (Exception e) {
            throw new BusinessException("导入用户错误："+e.getMessage());
        }
    }

    @Override
    public void exportTemplate(SysUser entity, HttpServletResponse response) {
        try {
            ExcelUtils.exportExcel(response, new BaseExcel() {
                @Override
                public String fileName() {
                    return "用户模板";
                }

                @Override
                public LinkedHashMap<String, String> headers() {
                    LinkedHashMap<String,String> headers = new LinkedHashMap<String, String>();
                    headers.put("loginName","用户名");
                    headers.put("no","工号");
                    headers.put("name","用户姓名");
                    headers.put("email","邮件");
                    headers.put("phone","联系方式");
                    return headers;
                }

                @Override
                public List<Map<String, Object>> datas() {
                    SysUser user = new SysUser();
                    user.setLoginName("whli");
                    user.setEmail("914164909@qq.com");
                    user.setPhone("13000000000");
                    List<SysUser> lines = new ArrayList<SysUser>();
                    lines.add(user);
                    return JacksonUtils.jsonToPojo(JacksonUtils.pojoToJsonIgnoreNull(lines),List.class);
                }
            });
        } catch (Exception e) {
            throw new BusinessException("导出用户模板错误："+e.getMessage());
        }
    }

    @Override
    public void exportExcel(SysUser entity, HttpServletResponse response) {
        super.exportExcel(entity, response);
    }
}
