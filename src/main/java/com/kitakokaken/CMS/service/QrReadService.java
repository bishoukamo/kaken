package com.kitakokaken.CMS.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kitakokaken.CMS.dto.QrCResponseDto;
import com.kitakokaken.CMS.entity.C_itiran;
import com.kitakokaken.CMS.entity.Capa_itiran;
import com.kitakokaken.CMS.entity.QrLabel;
import com.kitakokaken.CMS.repository.CitiranRepository;
import com.kitakokaken.CMS.repository.CapaitiranRepository;
import com.kitakokaken.CMS.repository.QrLabelRepository;

@Service
public class QrReadService {

    private final QrLabelRepository qrLabelRepository;
    private final CapaitiranRepository capaitiranRepository;
    private final CitiranRepository citiranRepository;

    public QrReadService(
            QrLabelRepository qrLabelRepository,
            CapaitiranRepository capaitiranRepository,
            CitiranRepository citiranRepository) {

        this.qrLabelRepository = qrLabelRepository;
        this.capaitiranRepository = capaitiranRepository;
        this.citiranRepository = citiranRepository;
    }

    @Transactional(readOnly = true)
    public QrCResponseDto getChemicalByToken(String qrToken) {

        if (qrToken == null || qrToken.isBlank()) {
            throw new RuntimeException("QRコードがありません");
        }

        // CMS1: が付いていたら取り除く
        if (qrToken.startsWith("CMS1:")) {
            qrToken = qrToken.substring("CMS1:".length());
        }

        // QRトークンからQR情報を取得
        QrLabel qrLabel = qrLabelRepository.findById(qrToken)
                .orElseThrow(() ->
                    new RuntimeException("QRコードが見つかりません"));

        Long materialId = qrLabel.getMaterialId();

        if (materialId == null) {
            throw new RuntimeException("このQRコードには容器IDが登録されていません");
        }

        // 容器情報を取得
        Capa_itiran capa = capaitiranRepository.findById(materialId)
                .orElseThrow(() ->
                    new RuntimeException("容器情報が見つかりません"));

        Long chemicalId = capa.getC_id();

        // 薬品情報を取得
        C_itiran chemical = citiranRepository.findById(chemicalId)
                .orElseThrow(() ->
                    new RuntimeException("薬品情報が見つかりません"));

        // 画面へ返すデータ
        QrCResponseDto response = new QrCResponseDto();

        response.setMaterialId(materialId);
        response.setChemicalId(chemicalId);
        response.setChemicalName(chemical.getC_name());
        response.setPrd(capa.getPRD());
        response.setCapacity(capa.getCapa());
        response.setPurchaseWeight(capa.getPur_weight());
        response.setExpiryDate(capa.getExpiryDate());

        return response;
    }
}