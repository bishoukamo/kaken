package com.kitakokaken.CMS.controller;//ユーザー一覧テーブル専用

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import java.util.List;

import com.kitakokaken.CMS.entity.Enum.Yakuwari;
import com.kitakokaken.CMS.entity.User;
import com.kitakokaken.CMS.repository.UserRepository;
import com.kitakokaken.CMS.service.TeachApprovalService;
import com.kitakokaken.CMS.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService service;//これも無視していい
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeachApprovalService teachApprovalService;

    @GetMapping("/admin/user")
    public String user(
        @RequestParam(defaultValue="user_id") String sort,
        @RequestParam(defaultValue="ASC") String order,
        Model model){

            model.addAttribute(
                "userList",
                service.findData(sort,order));

            return "Usertable";
    
    }



    @GetMapping("/mail/change")
    public String showMailChange(
        Authentication authentication,
        Model model) {

    String mail = authentication.getName();

    model.addAttribute("currentMail", mail);

    return "MailChange";
    }

    @PostMapping("/mail/change")
    public String changeMail(
        @RequestParam String password,
        @RequestParam String newMail,
        @RequestParam String newMailConfirm,
        Authentication authentication,
        HttpServletRequest request,
        HttpServletResponse response,
        Model model) {

    String currentMail = authentication.getName();

    User user = userRepository.findByMail(currentMail)
            .orElseThrow(() ->
                new RuntimeException("ユーザーが見つかりません"));

    if (!passwordEncoder.matches(password, user.getPass())) {
        model.addAttribute("currentMail", currentMail);
        model.addAttribute("error", "現在のパスワードが正しくありません");
        return "MailChange";
    }

    if (!newMail.equals(newMailConfirm)) {
        model.addAttribute("currentMail", currentMail);
        model.addAttribute("error", "新しいメールアドレスが一致していません");
        return "MailChange";
    }

    if (userRepository.findByMail(newMail).isPresent()) {
        model.addAttribute("currentMail", currentMail);
        model.addAttribute("error",
                "そのメールアドレスはすでに使用されています");
        return "MailChange";
    }

    user.setMail(newMail);
    userRepository.save(user);

    new SecurityContextLogoutHandler()
            .logout(request, response, authentication);

    return "redirect:/login?mailChanged";
    }
    
    //アカウント削除
    @GetMapping("/account/delete")
    public String showAccountDelete(
        Authentication authentication,
        Model model) {

        String mail = authentication.getName();

        User user = userRepository.findByMail(mail)
            .orElseThrow(() ->
                new RuntimeException("ユーザーが見つかりません"));

        model.addAttribute("user", user);

        return "AccountDelete";
    }

    @PostMapping("/account/delete")
    public String deleteAccount(
    @RequestParam String password,
    Authentication authentication,
    Model model) {

        String mail = authentication.getName();

        User user = userRepository.findByMail(mail)
            .orElseThrow(() ->
                new RuntimeException("ユーザーが見つかりません"));

        // パスワード確認
        if (!passwordEncoder.matches(password, user.getPass())) {

            model.addAttribute("user", user);
            model.addAttribute("error",
                "パスワードが正しくありません");

            return "AccountDelete";
        }

        // 関連データの存在確認
        long relatedCount =
            userRepository.countRelatedData(user.getUser_id());

        if (relatedCount > 0) {

            model.addAttribute("user", user);
            model.addAttribute("error",
                "このアカウントには使用履歴などの関連データが存在するため削除できません。"
                + "関連データが残っている場合は、削除できない項目を変更してから再度お試しください。");

            return "AccountDelete";
        }

        // 関連データがない場合のみアカウント削除
        userRepository.delete(user);

        return "redirect:/login";
    }

    //管理ユーザ申請画面用
    @GetMapping("/admin/teach")
    public String teachRequestList(Model model) {

        List<User> requestList =
            userRepository.findByTeachRequestTrue();

        model.addAttribute("requestList", requestList);

        return "TeachRequest";
    }

    @PostMapping("/admin/teach/approve")
    public String approveTeach(
        @RequestParam Long userId,
        Authentication authentication) {

        teachApprovalService.approve(userId, authentication.getName());

        return "redirect:/admin/teach";
    }
    
}