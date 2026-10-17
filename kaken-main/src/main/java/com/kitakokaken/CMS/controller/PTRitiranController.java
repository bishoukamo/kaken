package com.kitakokaken.CMS.controller;//実習室一覧テーブル専用

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kitakokaken.CMS.service.PTRitiranService;

@Controller
public class PTRitiranController {

    @Autowired
    private PTRitiranService service;

    @GetMapping("/ptr_itiran")
    public String user(
        @RequestParam(defaultValue="PTR_id") String sort,
        @RequestParam(defaultValue="ASC") String order,
        Model model) {

    model.addAttribute(
        "ptritiranList",
        service.findData(sort, order));

    return "PTRitirantable";
    }
    
}