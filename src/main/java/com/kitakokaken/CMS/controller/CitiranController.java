package com.kitakokaken.CMS.controller;//薬品一覧テーブル専用

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kitakokaken.CMS.service.CitiranService;

@Controller
public class CitiranController {

    @Autowired
    private CitiranService service;//この警告も無視していい

    @GetMapping("/citiran")
    public String citiran(

    @RequestParam(defaultValue="C_id") String sort,

    @RequestParam(defaultValue="ASC") String order,

    Model model){

    model.addAttribute(
        "itiranList",
        service.findData(sort, order));

    return "Citirantable";
    }
    
}