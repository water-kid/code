package com.cj.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {


    @PostMapping("/doLogin")
    public String login(String username, String password, Model model){
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
//            subject.checkRole("admin");
//            subject.checkPermissions("query","add");
            // 获取登录成功的用户信息
            // 如果是多个
            PrincipalCollection principals = subject.getPrincipals();
            List list = principals.asList();
            System.out.println(list);
            for (Object o : list) {
                System.out.println(o.getClass()+"=======>"+o);
            }

            return "login success";
        } catch (UnknownAccountException e) {
           return "unknown account";
        }catch (IncorrectCredentialsException e){
            return "incorrect credentials";
        }catch (AuthorizationException e){
            return "no authorization";
        }

    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "logout";
    }

    @RequiresRoles("admin")
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequiresPermissions("query")
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @RequiresPermissions("add")
    @GetMapping("/add")
    public String add(){
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("add");
        return "add success";
    }
}
