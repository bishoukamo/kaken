package com.kitakokaken.CMS.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kitakokaken.CMS.dto.QrCResponseDto;
import com.kitakokaken.CMS.entity.QrLabel;
import com.kitakokaken.CMS.service.QrLabelService;

@RestController
@RequestMapping("/admin/qr-labels")
public class QrLabelController {

    private final QrLabelService qrLabelService;

    public QrLabelController(QrLabelService qrLabelService) {
        this.qrLabelService = qrLabelService;
    }

    @PostMapping
    public Map<String, String> issue(
            @RequestParam Long materialId) {

        QrLabel qrLabel =
                qrLabelService.issue(materialId);

        return Map.of(
                "qrToken", qrLabel.getQrToken(),
                "qrValue", "CMS1:" + qrLabel.getQrToken()
        );
    }

    @PostMapping("/reissue")
    public Map<String, String> reissue(@RequestParam Long materialId) {

        QrLabel qrLabel = qrLabelService.reissue(materialId);

        return Map.of(
            "qrToken", qrLabel.getQrToken(),
            "qrValue", "CMS1:" + qrLabel.getQrToken()
        );
    }

}