package com.cj.configuration;

import com.cj.realm.CustomRealm;
import com.cj.realm.Realm01;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;

@Configuration
public class ShiroConfig {



    @Autowired
    CustomRealm customRealm;

    @Autowired
    Realm01 realm01;

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(customRealm);

        // 同时返回所有 登录成功realm信息
        AtLeastOneSuccessfulStrategy atLeastOneSuccessfulStrategy = new AtLeastOneSuccessfulStrategy();

        // 只返回第一个 认证成功的realm信息,,即使已经有 realm 认证成功了，还是会去继续调用后面剩下的Realm 进行认证操作
        FirstSuccessfulStrategy firstSuccessfulStrategy = new FirstSuccessfulStrategy();



        // 所有成功
        AllSuccessfulStrategy allSuccessfulStrategy = new AllSuccessfulStrategy();
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        securityManager.setAuthenticator(authenticator);
        authenticator.setAuthenticationStrategy(atLeastOneSuccessfulStrategy);
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(realm01);
        realms.add(customRealm);
        securityManager.setRealms(realms);
        return securityManager;
    }

    // filter工厂，，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager());


        /**
         * 添加shiro  内置过滤器
         * anon  匿名
         * authc  认证了
         * users  必须拥有记住我 功能 才能访问
         * perms：  拥有对某个资源的权限才能访问
         * role： 拥有某个角色权限
         */
        HashMap<String, String> filterMap = new HashMap<>();
        // 登出
        filterMap.put("logout","logout");
        filterMap.put("/**","authc");
        // 访问不了的时候，跳转到登录页面
        bean.setLoginUrl("/doLogin");
        // 登录成功
        bean.setSuccessUrl("/index");
        bean.setFilterChainDefinitionMap(filterMap);

        return bean;
    }


    /**
     * 开启 shiro aop 注解支持，否则 @RequiresRoles 等注解无法生效
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * 开启 shiro aop 注解支持，否则 @RequiresRoles 等注解无法生效,,,这个不写@RequirePermission生效，@RequiresRoles不生效
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}

