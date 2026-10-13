package com.kitakokaken.CMS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(HttpServletResponse response) {
    System.out.println("★★★ LOGIN CONTROLLER reached");
    System.out.println("★★★ status before view = " + response.getStatus());
    return "login";
    }
}
