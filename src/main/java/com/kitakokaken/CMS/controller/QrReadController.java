package com.kitakokaken.CMS.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kitakokaken.CMS.dto.QrCResponseDto;
import com.kitakokaken.CMS.service.QrReadService;

@RestController
@RequestMapping("/api/qr")
public class QrReadController {

    private final QrReadService qrReadService;

    public QrReadController(QrReadService qrReadService) {
        this.qrReadService = qrReadService;
    }

    @GetMapping("/{token}")
    public QrCResponseDto getChemical(
            @PathVariable String token) {

        return qrReadService.getChemicalByToken(token);
    }

    
}
