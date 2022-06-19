package com.cj;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cj.mapper.UserMapper;
import com.cj.model.LoginUser;
import com.cj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(queryWrapper);
        if(Objects.isNull(user)){
            throw new RuntimeException("silly b");
        }


        return new LoginUser(user);
    }
}
