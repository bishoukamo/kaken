package com.kitakokaken.CMS.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kitakokaken.CMS.dto.NewChemicalForm;
import com.kitakokaken.CMS.entity.C_itiran;
import com.kitakokaken.CMS.entity.Capa_itiran;
import com.kitakokaken.CMS.repository.CapaitiranRepository;
import com.kitakokaken.CMS.repository.CitiranRepository;

@Service
public class NewChemicalService {

    private final CitiranRepository citiranRepository;
    private final CapaitiranRepository capaitiranRepository;
    private final QrLabelService qrLabelService;

    public NewChemicalService(
            CitiranRepository citiranRepository,
            CapaitiranRepository capaitiranRepository,
            QrLabelService qrLabelService) {

        this.citiranRepository = citiranRepository;
        this.capaitiranRepository = capaitiranRepository;
        this.qrLabelService = qrLabelService;
    }

    @Transactional
    public void register(NewChemicalForm form) {

        // =========================
        // M_idの入力チェック
        // =========================

        if (form.getM_Id() == null) {
            throw new RuntimeException(
                    "容器IDを入力してください");
        }
        
        if (form.getM_Id() == null || form.getM_Id() < 1) {
                throw new IllegalArgumentException(
            "容器IDは1以上の数値を入力してください");
        }


        // =========================
        // QRチェック
        // =========================

        qrLabelService.verify(
                form.getQrToken(),
                form.getM_Id()
        );


        // =========================
        // M_idの重複チェック
        // =========================

        if (capaitiranRepository.existsById(
                form.getM_Id())) {

            throw new RuntimeException(
                    "この容器IDはすでに使用されています");
        }


        // =========================
        // 薬品種類を登録
        // =========================

        C_itiran chemical = new C_itiran();

        chemical.setC_name(
                form.getC_name());

        chemical.setPoisonType(
                form.getPoisonType());

        chemical.setSituation(
                form.getSituation());

        chemical.setRemarks(
                form.getRemarks());

        chemical.setC_set(
                LocalDateTime.now());

        chemical =
                citiranRepository.save(chemical);


        // =========================
        // 容器情報を登録
        // =========================

        Capa_itiran container =
                new Capa_itiran();

        container.setM_id(
                form.getM_Id());

        container.setC_id(
                chemical.getC_id());

        container.setPRD(
                form.getPRD());

        container.setExpiryDate(
                form.getExpiryDate());

        container.setCapa(
                form.getCapa());

        container.setPur_weight(
                form.getPur_weight());

        capaitiranRepository.save(container);


        // =========================
        // 薬品登録成功後にQRを使用済みにする
        // =========================

        qrLabelService.assignToMaterial(
                form.getQrToken(),
                form.getM_Id());
    }
}