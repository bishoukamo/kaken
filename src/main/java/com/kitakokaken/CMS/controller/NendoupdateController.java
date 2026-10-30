package com.kitakokaken.CMS.controller;//年度更新一覧テーブル専用

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kitakokaken.CMS.service.NendoupdateService;


@Controller
public class NendoupdateController {

    @Autowired
    private NendoupdateService service;//これも無視していい

    @GetMapping("/nendo_update")
    public String user(
        @RequestParam(defaultValue="U_id") String sort,
        @RequestParam(defaultValue="ASC") String order,
        Model model) {

            model.addAttribute(
                "nendo_updateList",
                service.findData(sort, order));

            return "Nendoupdatetable";
        }
    
}