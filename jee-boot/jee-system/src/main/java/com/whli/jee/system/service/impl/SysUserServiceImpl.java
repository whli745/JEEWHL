package com.whli.jee.system.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public IBaseDao<SysUser> getDao() {
        return sysUserDao;
    }

    @Override
    public int add(SysUser entity) {
        if (StringUtils.isNullOrBlank(entity.getLoginName()) || StringUtils.isNullOrBlank(entity.getEmail())
                || StringUtils.isNullOrBlank(entity.getPhone())){
            throw new BusinessException("用户名、邮箱、联系方式不能为空！");
        }

        SysUser temp = findByLoginNameOrEmailOrPhone(entity.getLoginName());
        if (BeanUtils.isNotNull(temp)) {
            throw new BusinessException("【"+entity.getLoginName()+"】该用户已存在！");
        }

        temp = findByLoginNameOrEmailOrPhone(entity.getEmail());
        if (BeanUtils.isNotNull(temp)) {
            throw new BusinessException("【"+entity.getEmail()+"】邮箱已被其他用户绑定！");
        }

        temp = findByLoginNameOrEmailOrPhone(entity.getPhone());
        if (BeanUtils.isNotNull(temp)) {
            throw new BusinessException("【"+entity.getPhone()+"】联系方式已被其他用户绑定！");
        }

        entity.setEnable(1);
        entity.setPassword(PwdUtils.md5Encode("123456", entity.getLoginName()));
        int rows = super.add(entity);
        //设置默认角色USER
        if (rows > 0){
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId("2");
            userRole.setUserId(entity.getId());
            sysUserRoleDao.add(userRole);
        }

        return rows;
    }

    @Override
    public void delete(SysUser entity) {
        if (entity == null || entity.getId() == null){
            throw new BusinessException("修改数据不能为空！");
        }
        SysUser user = sysUserDao.findByPK(entity.getId());
        user.setEnable(0);
        sysUserDao.update(user);
    }

    @Override
    public void deleteMore(SysUser entity) {
        if (entity == null || CollectionUtils.isNullOrEmpty(entity.getIds())){
            throw new BusinessException("修改数据不能为空！");
        }
        List<SysUser> users = sysUserDao.findByPKs(entity.getIds());
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
        if (StringUtils.isNullOrBlank(loginName) || StringUtils.isNullOrBlank(password)){
            throw new BusinessException("用户名或密码不能为空！");
        }

        SysUser sysUser = findByLoginNameOrEmailOrPhone(loginName);
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

        if (StringUtils.isNotNullOrBlank(RedisConfig.clusterNodes)){
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
        if (StringUtils.isNotNullOrBlank(RedisConfig.clusterNodes)){
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
    public SysUser findByLoginNameOrEmailOrPhone(String loginName) {
        SysUser currentUser = null;
        if (StringUtils.isNotNullOrBlank(loginName)) {
            currentUser = sysUserDao.findByLoginNameOrEmailOrPhone(loginName);
        }
        return currentUser;
    }

    @Override
    public SysUser findByEmail(String email) {
        if(StringUtils.isNullOrBlank(email)){
            throw new BusinessException("请选择需要查询的数据！");
        }
        SysUser entity = sysUserDao.findByEmail(email);
        if(BeanUtils.isNull(entity)){
            throw  new BusinessException("查询的数据不存在或已删除！");
        }
        return entity;
    }

    @Override
    public SysUser findByPhone(String phone) {
        if(StringUtils.isNullOrBlank(phone)){
            throw new BusinessException("请选择需要查询的数据！");
        }
        SysUser entity = sysUserDao.findByPhone(phone);
        if(BeanUtils.isNull(entity)){
            throw  new BusinessException("查询的数据不存在或已删除！");
        }
        return entity;
    }

    @Override
    public int grantByUser(String userId, List<String> roleIds) {

        if (StringUtils.isNullOrBlank(userId)){
            throw new BusinessException("请选择需要授权的用户！");
        }

        int rows = 0;
        if (CollectionUtils.isNotNullOrEmpty(roleIds)) {
            List<SysUserRole> userRoles = new ArrayList<SysUserRole>();
            for (String roleId : roleIds) {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                List<SysUserRole> sysUserRoles = sysUserRoleDao.findAll(userRole);
                if (CollectionUtils.isNotNullOrEmpty(sysUserRoles)){
                    SysRole role = sysRoleService.findByPK(roleId);
                    throw new BusinessException("角色【"+role.getName()+"】已存在！");
                }
                userRoles.add(userRole);
                sysUserRoleDao.add(userRole);
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
        if (CollectionUtils.isNullOrEmpty(entity.getIds())){
            throw new BusinessException("请选择需要重置密码的用户！");
        }
        for (String id : entity.getIds()){
            SysUser user = findByPK(id);
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
            if (CollectionUtils.isNotNullOrEmpty(users)){
                for (SysUser entity : users){
                    rows += this.add(entity);
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
                    try {
                        CollectionLikeType type = objectMapper.getTypeFactory().constructCollectionLikeType(List.class,Map.class);
                        return objectMapper.readValue(objectMapper.writeValueAsString(lines),type);
                    } catch (IOException e) {
                        throw new BusinessException(e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            throw new BusinessException("导出用户模板错误："+e.getMessage());
        }
    }
}
