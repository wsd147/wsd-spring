package com.wsd.shiro.realm;

import com.wsd.admin.dao.PermissionMapper;
import com.wsd.admin.dao.RoleMapper;
import com.wsd.admin.dao.UserMapper;
import com.wsd.admin.pojo.Permission;
import com.wsd.admin.pojo.Role;
import com.wsd.admin.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserRealm extends AuthorizingRealm{

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<Role> roles =roleMapper.selectRolesByUserId(user.getUserId());
        if(roles.size()>0){
            Set<String> roleSet = new HashSet<>();
            for(Role role:roles){
                roleSet.add(role.getName());
            }
            simpleAuthorizationInfo.addRoles(roleSet);
           List<Permission> permissions = permissionMapper.selectByUserId(user.getUserId());
            if(permissions.size()>0){
                Set<String> permissionSet = new HashSet<>();
                for(Permission permission:permissions){
                    permissionSet.add(permission.getPermissionValue());
                }
                simpleAuthorizationInfo.setStringPermissions(permissionSet);
                return simpleAuthorizationInfo;
            }else{
                throw  new UnauthorizedException("该用户未授权");
            }
        }else{
            throw  new UnauthorizedException("该用户未分配角色");
        }
    }

    /**
     * 验证用户身份
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        String userName = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",userName);
        User user = userMapper.selectOneByExample(example);
        if(user==null){
            throw new UnknownAccountException("账号不存在");
        }
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex((password+user.getUsername()+user.getSalt()).getBytes()))){
            throw  new IncorrectCredentialsException("密码错误");
        }
        if(user.getLocked()==1){
            throw new LockedAccountException("账号已锁定，联系管理员解锁");
        }
        if(user!=null){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    user,password,getName()
            );
            return  simpleAuthenticationInfo;
        }
        return null;
    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
