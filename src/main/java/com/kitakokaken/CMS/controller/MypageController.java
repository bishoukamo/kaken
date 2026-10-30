package com.kitakokaken.CMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.kitakokaken.CMS.entity.User;
import com.kitakokaken.CMS.repository.UserRepository;

@Controller
public class MypageController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/mypage")
    public String showMypage(
            Model model,
            Authentication authentication) {

        String mail = authentication.getName();

        User user = userRepository.findByMail(mail)
                .orElseThrow(() ->
                    new RuntimeException("ユーザーが見つかりません"));

        model.addAttribute("user", user);

        return "Mypage";
    }

    @GetMapping("/mypage/password")
    public String showPasswordChange(){
        return "PasswordChange";
    }
    
    @PostMapping("/mypage/password")
    public String changePassword(
        @RequestParam String currentPassword,
        @RequestParam String newPassword,
        @RequestParam String confirmPassword,
        Authentication authentication,
        Model model) {

    String mail = authentication.getName();

    User user = userRepository.findByMail(mail)
            .orElseThrow(() ->
                new RuntimeException("ユーザーが見つかりません"));

    // 現在のパスワード確認
    if (!passwordEncoder.matches(currentPassword, user.getPass())) {

        model.addAttribute("error",
                "現在のパスワードが正しくありません");

        return "PasswordChange";
    }

    // 新しいパスワードの一致確認
    if (!newPassword.equals(confirmPassword)) {

        model.addAttribute("error",
                "新しいパスワードが一致していません");

        return "PasswordChange";
    }

    // 新しいパスワードを暗号化
    user.setPass(passwordEncoder.encode(newPassword));

    userRepository.save(user);

    return "redirect:/mypage";
    }
}
