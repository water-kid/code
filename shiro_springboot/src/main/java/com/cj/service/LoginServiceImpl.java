package com.cj.service;

import com.cj.model.Permission;
import com.cj.model.Role;
import com.cj.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;

@Service
public class LoginServiceImpl implements LoginService{


    @Override
    public User getUserByName(String username) {
        return getMapByName(username);
    }


    public User getMapByName(String username){
        Permission permission1 = new Permission("1", "query");
        Permission permission2 = new Permission("2", "add");

        HashSet<Permission> permissionSet = new HashSet<>();
        permissionSet.add(permission1);
        permissionSet.add(permission2);

        Role role = new Role("!", "admin", permissionSet);
        HashSet<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        User user = new User("1", "cc", "123", roleSet);

        HashMap<String, User> map = new HashMap<>();
        map.put(user.getUsername(),user);

//        权限2
        HashSet<Permission> permissionSet2 = new HashSet<>();
        // query
        permissionSet2.add(permission1);
//        角色2
        Role role2 = new Role("2", "user", permissionSet2);
        HashSet<Role> roleSet2 = new HashSet<>();
        roleSet2.add(role2);

        // 只读 user
        User user1 = new User("2", "zs", "123", roleSet2);

        map.put(user1.getUsername(),user1);

        return map.get(username);
    }
}
