package com.cj.service;

import com.cj.model.User;

public interface LoginService {
    User getUserByName(String username);
}
