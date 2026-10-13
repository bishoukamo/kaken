package com.kitakokaken.CMS.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="nendo_update") //""のテーブルに対応
public class Nendo_update{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "U_id")
    private Long U_id;

    @Column(name = "M_id")
    private Long M_id;

    @Column(name = "U_date")
    private LocalDate U_date;

    @Column(name = "U_weight")
    private double U_weight;

    @Column(name = "kousin_umu")
    @Enumerated(EnumType.STRING)
    private Enum.kousin_umu kousinType;

    public Enum.kousin_umu getKousinType() {
       return kousinType;
    }

    public void setKousinType(Enum.kousin_umu kousinType) {
       this.kousinType = kousinType;
    }
    public Long getU_id(){
        return U_id;
    }

    public Long getM_id(){
        return M_id;
    }
    public void setM_id(Long M_id){
        this.M_id = M_id;
    }

    public LocalDate getU_date(){
        return U_date;
    }
    public void setU_date(LocalDate U_date){
        this.U_date = U_date;
    }

    public double getU_weight() {
        return U_weight;
    }

    public void setU_weight(double U_weight) {
        this.U_weight = U_weight;
    }

}