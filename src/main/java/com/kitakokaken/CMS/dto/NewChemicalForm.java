package com.kitakokaken.CMS.dto;

import java.time.LocalDate;

import com.kitakokaken.CMS.entity.Enum.DS;
import com.kitakokaken.CMS.entity.Enum.Situation;

public class NewChemicalForm {

    // c_itiran
    private String C_name;
    private DS poisonType;          // 劇・毒・未選択(null)
    private Situation situation;   // 固体・液体・気体
    private String remarks;

    // capa_itiran
    private Long M_Id;        // M_id
    private LocalDate PRD;  // PRD
    private LocalDate expiryDate;   //expiryDate
    private double capa;        // capa
    private double pur_weight; // pur_weight

    // qr_label
    private String qrToken;

    public String getC_name() {
        return C_name;
    }

    public void setC_name(String C_name) {
        this.C_name = C_name;
    }

    public DS getPoisonType() {
        return poisonType;
    }

    public void setPoisonType(DS poisonType) {
        this.poisonType = poisonType;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getM_Id() {
        return M_Id;
    }

    public void setM_Id(Long M_Id) {
        this.M_Id = M_Id;
    }

    public LocalDate getPRD() {
        return PRD;
    }

    public void setPRD(LocalDate PRD) {
        this.PRD = PRD;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getCapa() {
        return capa;
    }

    public void setCapa(double capa) {
        this.capa = capa;
    }

    public double getPur_weight() {
        return pur_weight;
    }

    public void setPur_weight(double pur_weight) {
        this.pur_weight = pur_weight;
    }

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }
}
