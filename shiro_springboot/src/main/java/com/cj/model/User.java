package com.cj.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;

@Data
@AllArgsConstructor
public class User {

    private String id;
    private String username;
    private String password;

    private HashSet<Role> roles;
}
