package com.kitakokaken.CMS.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="c_delete") //""のテーブルに対応
public class C_delete{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "D_id")
    private Long D_id;

    @Column(name = "M_id")
    private Long M_id;

    @Column(name = "D_date")
    private LocalDate D_date;

    public Long getD_id(){
        return D_id;
    }
    public void setD_id(Long D_id){
        this.D_id = D_id;
    }

    public Long getM_id(){
        return M_id;
    }
    public void setM_id(Long M_id){
        this.M_id = M_id;
    }

    public LocalDate getD_date(){
        return D_date;
    }
    public void setD_date(LocalDate D_date){
        this.D_date = D_date;
    }
}