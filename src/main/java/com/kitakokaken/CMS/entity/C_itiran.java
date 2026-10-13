package com.kitakokaken.CMS.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="c_itiran") //""のテーブルに対応
public class C_itiran{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "C_id")
    private Long C_id;

    @Column(name = "C_name")
    private String C_name;

    @Column(name = "DS")
    @Enumerated(EnumType.STRING)
    private Enum.DS poisonType;

    public Enum.DS getPoisonType() {
       return poisonType;
    }

    public void setPoisonType(Enum.DS poisonType) {
       this.poisonType = poisonType;
    }

    @Column(name = "situation")
    @Enumerated(EnumType.STRING)
    private Enum.Situation situation;

    public Enum.Situation getSituation() {
        return situation;
    }

    public void setSituation(Enum.Situation situation) {
        this.situation = situation;
    }

    @Column(name = "C_set")
    private LocalDateTime C_set;

    @Column(name = "remarks")
    private String remarks;

    public Long getC_id(){
        return C_id;
    }
    public void setC_id(Long C_id){
        this.C_id = C_id;
    }

    public String getC_name(){
        return C_name;
    }
    public void setC_name(String C_name){
        this.C_name = C_name;
    }

    public LocalDateTime getC_set(){
        return C_set;
    }
    public void setC_set(LocalDateTime C_set){
        this.C_set = C_set;
    }

    public String getRemarks(){
        return remarks;
    }
    public void setRemarks(String remarks){
        this.remarks = remarks;
    }

}
