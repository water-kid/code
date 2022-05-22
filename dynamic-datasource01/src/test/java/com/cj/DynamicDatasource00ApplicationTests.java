package com.cj;

import com.cj.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DynamicDatasource00ApplicationTests {
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        System.out.println(userService.getAllUsers());
    }

}
