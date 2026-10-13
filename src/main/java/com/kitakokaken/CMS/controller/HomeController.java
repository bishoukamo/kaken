package com.kitakokaken.CMS.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kitakokaken.CMS.entity.User;
import com.kitakokaken.CMS.repository.UserRepository;
import com.kitakokaken.CMS.service.HomeService;

@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final HomeService homeService;

    public HomeController(
            UserRepository userRepository,
            HomeService homeService) {

        this.userRepository = userRepository;
        this.homeService = homeService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {

        User loginUser = userRepository.findByMail(authentication.getName())
                .orElseThrow(() ->
                    new RuntimeException("ログインユーザが見つかりません"));

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("noticeList", homeService.getNotices());

        return "homegamen";
    }
}