package com.kitakokaken.CMS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kitakokaken.CMS.dto.NewChemicalForm;
import com.kitakokaken.CMS.dto.NewChemicalForm;
import com.kitakokaken.CMS.service.NewChemicalService;

@Controller
public class NewChemicalController {

    private final NewChemicalService NewChemicalService;

    public NewChemicalController(NewChemicalService newChemicalService) {
        this.NewChemicalService = newChemicalService;
    }

    @GetMapping("/admin/chemical/new")
    public String showNewChemicalForm() {
        return "newCtouroku";
    }

    @PostMapping("/admin/chemical/new")
    public String register(
        @ModelAttribute NewChemicalForm form,
        Model model) {

        try {
            NewChemicalService.register(form);
            return "redirect:/admin/chemical/new?success";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "newCtouroku";
        }
    }
}