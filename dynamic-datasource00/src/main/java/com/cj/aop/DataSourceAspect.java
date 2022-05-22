package com.cj.aop;

import com.cj.DataSource;
import com.cj.DynamicDatasource00Application;
import com.cj.datasource.DynamicDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DataSourceAspect {
    @Pointcut("@annotation(com.cj.DataSource) || @within(com.cj.DataSource)")
    public void pointcut(){}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        // 获取注解
        DataSource dataSource = getDataSource(joinPoint);

        // 根据@DataSource拦截的，dataSource一定存在
        String dataSourceName = dataSource.value();
        DynamicDataSourceHolder.setDataSourceName(dataSourceName);
    }

    private DataSource getDataSource(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 判断方法上有没有 @DataSource
        DataSource annotation = AnnotationUtils.findAnnotation(method, DataSource.class);
        if(annotation != null){
            return annotation;
        }

        return AnnotationUtils.findAnnotation(signature.getDeclaringType(),DataSource.class);
    }

    @After("pointcut()")
    public void after(){
        DynamicDataSourceHolder.removeDataSourceName();
    }
}
