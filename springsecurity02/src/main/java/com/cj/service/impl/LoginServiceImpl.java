package com.cj.service.impl;

import com.cj.ResponseResult;
import com.cj.model.LoginUser;
import com.cj.model.User;
import com.cj.service.LoginService;
import com.cj.util.JwtUtils;
import com.cj.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        // 认证的时候：  是将 用户名  和  密码  封装到一个 Authentication 对象中
        // alt + ctrl + 鼠标左键  ： 找实现类
        // @Param1  认证主体  @param2 ：凭证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        // 通过 AuthenticationManager 认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        System.out.println("authenticate = " + authenticate);

        // 认证不通过 为 null
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或者密码错误");
        }

        //  认证通过，生成jwt， 将完整的用户信息存入redis中
        // getPrincipal() 返回的是 实现UserDetails的 实体
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long id = loginUser.getUser().getUserId();
        // 生成jwt
        String jwt = JwtUtils.getJwtToken(id.toString(), user.getUsername());

        Map<String, String> map = new HashMap<>();
        map.put("token",jwt);

        // 存入 redis
        redisCache.setCacheObject("login:"+user.getUserId(),loginUser);

        return new ResponseResult("200","登录成功", map);
    }
}
