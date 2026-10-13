package com.kitakokaken.CMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kitakokaken.CMS.dto.UseCitiranDto;
import com.kitakokaken.CMS.repository.UseCitiranRepository;

@Controller
public class UseCitiranController {

    @Autowired
    private UseCitiranRepository repository;

    @GetMapping("/usecitiran")
    public String useCitiran(
            @RequestParam(defaultValue = "m_id") String sort,
            @RequestParam(defaultValue = "ASC") String order,
            Model model) {

        List<UseCitiranDto> list =
                repository.findCurrentChemical(sort, order);

        model.addAttribute("useList", list);

        return "UseCTable";
    }
}