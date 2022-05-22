package com.cj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DataSource("slave")
public class UserService {
    @Autowired
    UserMapper userMapper;

    @DataSource("master")
    public List<User> getAllUsers(){
        return userMapper.getAllUsers();
    }
}
