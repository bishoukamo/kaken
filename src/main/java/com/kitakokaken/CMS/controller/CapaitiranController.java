package com.kitakokaken.CMS.controller;//容量一覧テーブル専用

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kitakokaken.CMS.service.CapaitiranService;

@Controller
public class CapaitiranController {

    @Autowired
    private CapaitiranService service;

@GetMapping("/capa")
public String capa(
        @RequestParam(defaultValue="M_id") String sort,
        @RequestParam(defaultValue="ASC") String order,
        Model model){

    model.addAttribute(
            "capaList",
            service.findData(sort, order));

    return "Capaitirantable";
}
    
}