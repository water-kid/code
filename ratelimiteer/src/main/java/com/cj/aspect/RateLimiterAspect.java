package com.cj.aspect;

import com.cj.IpUtils;
import com.cj.annotation.RateLimiter;
import com.cj.enums.LimitType;
import com.cj.exception.RateLimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Collections;
import java.util.List;

@Aspect
@Component
public class RateLimiterAspect {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterAspect.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisScript redisScript;

    /**
     * 拦截 rateLimiter注解   限流的话，还没进入方法就拦截
     * @param jp
     * @param rateLimiter
     */
    @Before("@annotation(rateLimiter)")
    public void before(JoinPoint jp, RateLimiter rateLimiter) throws RateLimitException {

        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String combineKey = getCombineKey(rateLimiter,jp);

        try {
            // Collections.singletonList被限定只被分配一个内存空间，也就是只能存放一个元素的内容。
            Long number = (Long) redisTemplate.execute(redisScript, Collections.singletonList(combineKey), time, count);
            // number 什么情况下为 null
            if(number == null || number.intValue() > count){
                logger.info("当前接口已达到最大限流次数");
                throw  new RateLimitException("访问频繁，请稍后访问");
            }
            logger.info("一个时间窗内请求次数：{},当前请求次数：{} ，缓存key为：{}",count,number,combineKey);
        } catch (Exception e) {

           throw  e;
        }

    }

    private String getCombineKey(RateLimiter rateLimiter, JoinPoint jp) {
        StringBuilder sb = new StringBuilder(rateLimiter.key());

        LimitType limitType = rateLimiter.limitType();
        // 拼接 ip
        if(limitType == LimitType.IP){
            // 获取ip
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            sb.append(IpUtils.getIpAddress(request)).append("-");
        }

        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        sb.append(method.getDeclaringClass().getName()).append("-").append(method.getName());
        return sb.toString();
    }

}
