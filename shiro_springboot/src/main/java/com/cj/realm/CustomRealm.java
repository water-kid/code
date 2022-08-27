package com.cj.realm;

import com.cj.model.Permission;
import com.cj.model.Role;
import com.cj.model.User;
import com.cj.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    LoginService loginService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取登录用户名
        String name = principals.getPrimaryPrincipal().toString();

        User user = loginService.getUserByName(name);

        if(user == null){
            return null;
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        for (Role role : user.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getRoleName());

            for (Permission permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission.getPermissionName());
            }
        }
        // 返回这个角色 具有的权限信息
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if(StringUtils.isEmpty(token.getPrincipal())){
            return null;
        }
        // 获取用户信息
        String name = token.getPrincipal().toString();
        User user = loginService.getUserByName(name);
        if(user == null){
            // 这里返回后会报出对应的异常
            return null;
        }else{
            // 返回数据库 查出来的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), this.getName());
            //  返回这个角色 具有的认证信息
            return  simpleAuthenticationInfo;
        }
    }
}
