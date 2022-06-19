package com.cj;

import com.cj.model.User;
import com.cj.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return  loginService.login(user);
    }
}
