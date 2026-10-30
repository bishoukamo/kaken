package com.kitakokaken.CMS.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kitakokaken.CMS.entity.Enum.Yakuwari;
import com.kitakokaken.CMS.entity.User;
import com.kitakokaken.CMS.repository.UserRepository;

@Controller
public class NewuserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // 新規ユーザ登録画面
    @GetMapping("/user/new")
    public String showNewUser(Model model) {

        System.out.println("★★★ GET /user/new Controller reached");

        model.addAttribute("user", new User());

        return "newuser";
    }


    // 新規ユーザ登録
    @PostMapping("/user/new")
    public String createUser(
            @ModelAttribute User user,
            @RequestParam String passwordConfirm,
            @RequestParam String yakuwariSelect,
            Model model) {

        // パスワード確認
        if (!user.getPass().equals(passwordConfirm)) {

            model.addAttribute("error",
                    "パスワードが一致していません");

            return "newuser";
        }


        // メールアドレス重複確認
        if (userRepository.findByMail(user.getMail()).isPresent()) {

            model.addAttribute("error",
                    "このメールアドレスは既に登録されています");

            return "newuser";
        }


        // パスワードを暗号化
        user.setPass(
                passwordEncoder.encode(user.getPass())
        );


        // 登録日時
        user.setSetting(LocalDateTime.now());


        // 権限設定
        if ("teach".equals(yakuwariSelect)) {
            // teachを選択した場合でも、承認されるまでは一般ユーザ
            user.setYakuwari(Yakuwari.seito);
            user.setTeachRequest(true);
        } else {
            // seitoを選択
            user.setYakuwari(Yakuwari.seito);
            user.setTeachRequest(false);
        }

        // DB登録
        userRepository.save(user);


        // 登録完了後ログイン画面へ
        return "redirect:/login";
    }
}
