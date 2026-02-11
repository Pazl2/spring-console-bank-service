package com.pazl2.services;

import com.pazl2.models.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService {

    private final AccountService accountService;

    private Map<Integer,User> users = new HashMap<>();

    public UserService(@Lazy AccountService accountService){
        this.accountService = accountService;
    }

    public void createUser(String username){
        User user = new User(username);
        users.put(user.getId(), user);
        accountService.createAccount(user.getId());

    }

    public User findUserById(Integer id){
        return users.get(id);
    }


    public List<User> getAllUsers(){
        return users.values().stream().toList();
    }

    public Map<Integer, User> getMapUsers() {
        return users;
    }
}
