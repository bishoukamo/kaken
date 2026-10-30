package com.kitakokaken.CMS.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="capa_itiran") //""のテーブルに対応
public class Capa_itiran{

    @Id

    @Column(name = "M_id")
    private Long M_id;

    @Column(name = "C_id")
    private Long C_id;

    @Column(name = "PRD")
    private LocalDate PRD;

    @Column(name = "capa")
    private double capa;

    @Column(name = "pur_weight")
    private double pur_weight;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getM_id(){
        return M_id;
    }
    public void setM_id(Long M_id){
        this.M_id = M_id;
    }

    public Long getC_id(){
        return C_id;
    }
    public void setC_id(Long C_id){
        this.C_id = C_id;
    }

    public LocalDate getPRD() {
        return PRD;
    }

    public void setPRD(LocalDate PRD) {
        this.PRD = PRD;
    }

    public double getCapa(){
        return capa;
    }
    public void setCapa(double capa){
        this.capa = capa;
    }

    public double getPur_weight(){
        return pur_weight;
    }
    public void setPur_weight(double pur_weight){
        this.pur_weight = pur_weight;
    }
}