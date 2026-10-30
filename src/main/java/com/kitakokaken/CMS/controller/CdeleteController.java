package com.kitakokaken.CMS.controller;//抹消一覧テーブル専用

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kitakokaken.CMS.service.CdeleteService;

@Controller
public class CdeleteController {

    @Autowired
    private CdeleteService service;

    
    @GetMapping("/admin/cdelete")
    public String user(
        @RequestParam(defaultValue="D_id") String sort,
        @RequestParam(defaultValue="ASC") String order,
        Model model){

    model.addAttribute(
        "deleteList",
        service.findData(sort,order));

    return "Cdeletetable";
    }
    
}