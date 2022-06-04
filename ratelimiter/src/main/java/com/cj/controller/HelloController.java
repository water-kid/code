package com.cj.controller;

import com.cj.IpUtils;
import com.cj.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
    /**
     * 10s 内，访问 3 次
     * @return
     */
    @RateLimiter(time = 10,count = 3)
    @GetMapping("/hello")
    public String hello(HttpServletRequest request){
        String ip = IpUtils.getIpAddress(request);
        return "hello  "+ip;
    }
}
