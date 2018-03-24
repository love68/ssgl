package com.ssgl.realm;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/16 0016
 * Time: 21:20
 */

import com.ssgl.bean.TUser;
import com.ssgl.mapper.CustomerPrivilegeMapper;
import com.ssgl.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SsglRealm extends AuthorizingRealm {

    @Autowired
    public UserService userService;

    @Autowired
    public CustomerPrivilegeMapper customerPrivilegeMapper;
    
    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        TUser subject = (TUser) principalCollection.getPrimaryPrincipal();

        List<String> codes = customerPrivilegeMapper.selectPrivileges(subject.getId());
        info.addStringPermissions(codes);

        return info;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        if(null==username){
            return null;
        }

        try {
            TUser user = userService.selectUserByUsername(username);
            if(null == user){
                return null;
            }
            String password = user.getPassword();
            AuthenticationInfo info = new SimpleAuthenticationInfo(user,password,getName());
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
