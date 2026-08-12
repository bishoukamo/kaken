package com.kanta.kaken.controller;

import com.kanta.kaken.dto.LoginRequest;
import com.kanta.kaken.dto.LoginResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        if ("Taro".equals(request.getUsername())
                && "12345678".equals(request.getPassword())) {

            return new LoginResponse(true, "ログイン成功");
        }

        return new LoginResponse(false, "ログイン失敗");
    }
}