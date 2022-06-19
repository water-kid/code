package com.cj.service;

import com.cj.ResponseResult;
import com.cj.model.User;

public interface LoginService {
    ResponseResult login(User user);
}
