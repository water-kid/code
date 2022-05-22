package com.cj.aop;

import com.cj.datasource.DataSourceType;
import com.cj.datasource.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Aspect
@Order(10)
public class GlobalDataSourceAspect {
    @Autowired
    HttpSession httpSession;

    @Pointcut("execution(* com.cj.service.*.*(..))")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp){
        DynamicDataSourceHolder.setDataSourceName((String)httpSession.getAttribute(DataSourceType.DS_SESSION_KEY));
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            DynamicDataSourceHolder.removeDataSourceName();
        }
        return null;
    }
}
