package com.kitakokaken.CMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kitakokaken.CMS.dto.SiyoulogDto;
import com.kitakokaken.CMS.entity.C_itiran;
import com.kitakokaken.CMS.entity.Capa_itiran;
import com.kitakokaken.CMS.entity.Siyoulog;
import com.kitakokaken.CMS.entity.User;
import com.kitakokaken.CMS.repository.CapaitiranRepository;
import com.kitakokaken.CMS.repository.CitiranRepository;
import com.kitakokaken.CMS.repository.UserRepository;
import com.kitakokaken.CMS.service.SiyoulogService;

@Controller
public class SiyoulogController {

    @Autowired
    private SiyoulogService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private CapaitiranRepository capaitiranRepository;

    @Autowired 
    private CitiranRepository citiranRepository;


    // 管理ユーザ用：使用log一覧
    @GetMapping("/admin/siyoulog")
    public String showSiyoulog(
            @RequestParam(defaultValue = "asc") String order,
            Model model) {

        List<SiyoulogDto> list =
                service.findSiyoulogData(order);

        model.addAttribute("siyoulogList", list);

        return "Siyoulogtable";
    }


    // 一般ユーザ用：自分の使用log一覧
    @GetMapping("/mysiyoulog")
    public String showMySiyoulog(
            Model model,
            Authentication authentication) {

        String mail = authentication.getName();

        User user = userRepository.findByMail(mail)
                .orElseThrow(() ->
                    new RuntimeException("ユーザーが見つかりません"));

        Long userId = user.getUser_id();

        List<SiyoulogDto> list =
                service.findSiyoulogByUserId(userId);

        if (list.isEmpty()) {
            model.addAttribute("message", "自分の使用記録がありません");
        }

        model.addAttribute("siyoulogList", list);

        return "MySiyoulogtable";
    }


    // 使用log編集画面
    @GetMapping("/siyoulog/edit/{id}")
    public String editSiyoulog(
            @PathVariable Long id,
            Model model,
            Authentication authentication) {

            // 編集対象の使用記録を取得
            Siyoulog log = service.findById(id);

            // ログイン中のユーザを取得
            String mail = authentication.getName();

            User user = userRepository.findByMail(mail)
                .orElseThrow(() ->
                    new RuntimeException("ユーザーが見つかりません"));

            // 管理ユーザではない場合
            if (!user.getYakuwari().name().equals("teach")) {

                // 自分の記録か確認
                if (!log.getUser_id().equals(user.getUser_id())) {
                    throw new RuntimeException(
                        "他のユーザーの使用記録は編集できません");
                }
            }

        model.addAttribute("siyoulog", log);

        return "Siyoulogedit";
    }

    // 使用log新規登録画面
   @GetMapping("/siyoulog/new")
    public String newSiyoulog(
        @RequestParam(required = false) Long M_id,
        @RequestParam(required = false) Long C_id,
        Model model) {

        Siyoulog siyoulog = new Siyoulog();

        siyoulog.setM_id(M_id);
        siyoulog.setC_id(C_id);

        model.addAttribute("siyoulog", siyoulog);

        // QRから来た場合
        if (M_id != null) {

            Capa_itiran capa =
                capaitiranRepository.findById(M_id)
                .orElse(null);

            if (capa != null) {

                model.addAttribute(
                    "capacity",
                    capa.getCapa());

                model.addAttribute(
                    "expiryDate",
                    capa.getExpiryDate());

                C_itiran chemical =
                    citiranRepository.findById(
                        capa.getC_id())
                    .orElse(null);

            if (chemical != null) {

                model.addAttribute(
                        "chemicalName",
                        chemical.getC_name());
            }
        }
    }

    return "kiroku";
    }

    // 使用log新規登録
    @PostMapping("/siyoulog/new")
    public String createSiyoulog(
        @ModelAttribute Siyoulog siyoulog,
        Authentication authentication,
        Model model,
        RedirectAttributes redirectAttributes) {

    String mail = authentication.getName();

    User user = userRepository.findByMail(mail)
            .orElseThrow(() ->
                new RuntimeException("ユーザーが見つかりません"));

    // user_idはログイン中のユーザーから設定
    siyoulog.setUser_id(user.getUser_id());

    try {
        String warning = service.createSiyoulog(siyoulog);

        if (warning != null) {
            redirectAttributes.addFlashAttribute("warning", warning);
        }

        return "redirect:/mysiyoulog";

    } catch (IllegalArgumentException e) {
        model.addAttribute("siyoulog", siyoulog);
        model.addAttribute("error", e.getMessage());

        addContainerInfo(model, siyoulog.getM_id());

        return "kiroku";
    }
    }

    // 使用log変更
    @PostMapping("/siyoulog/edit")
    public String updateSiyoulog(
        @ModelAttribute Siyoulog siyoulog,
        Authentication authentication,
        Model model,
        RedirectAttributes redirectAttributes) {

    String mail = authentication.getName();

    User user = userRepository.findByMail(mail)
            .orElseThrow(() ->
                new RuntimeException("ユーザーが見つかりません"));

    Siyoulog original = service.findById(siyoulog.getLog_id());

    // 管理ユーザー以外は自分の記録だけ編集可能
    if (!user.getYakuwari().name().equals("teach")) {
        if (!original.getUser_id().equals(user.getUser_id())) {
            throw new RuntimeException(
                "他のユーザーの使用記録は編集できません");
        }
    }

    // 元のuser_idを維持
    siyoulog.setUser_id(original.getUser_id());

    try {
        String warning = service.updateSiyoulog(siyoulog);

        if (warning != null) {
            redirectAttributes.addFlashAttribute("warning", warning);
        }

        return "redirect:/mysiyoulog";

    } catch (IllegalArgumentException e) {
        model.addAttribute("siyoulog", siyoulog);
        model.addAttribute("error", e.getMessage());

        return "Siyoulogedit";
    }
    }
    private void addContainerInfo(Model model, Long M_id) {

    if (M_id == null) {
        return;
    }

    Capa_itiran capa = capaitiranRepository.findById(M_id)
            .orElse(null);

    if (capa == null) {
        return;
    }

    model.addAttribute("capacity", capa.getCapa());
    model.addAttribute("expiryDate", capa.getExpiryDate());

    C_itiran chemical = citiranRepository
            .findById(capa.getC_id())
            .orElse(null);

    if (chemical != null) {
        model.addAttribute("chemicalName", chemical.getC_name());
    }
}

}