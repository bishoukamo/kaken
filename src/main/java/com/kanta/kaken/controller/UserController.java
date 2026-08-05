package com.kanta.kaken.controller;

import com.kanta.kaken.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @RequestMapping("/kaken")
    public String kaken(){
        return "kaken";
    }

    @GetMapping("/api/users")
    public List<User> getUsers() {
        return List.of(
                new User("Taro",20,"japan",false),
                new User("Hanako",18,"japan",true)
        );
    }
    public String users(){
        return "users";
    }
}