package com.kitakokaken.CMS.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="siyoulog") //""のテーブルに対応
public class Siyoulog{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "log_id")
    private Long log_id;

    @Column(name="M_id")
    private Long M_id;

    @Column(name = "C_id")
    private Long C_id;

    @Column(name = "PTR_id")
    private Long PTR_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "useday")
    private LocalDate useday;

    @Column(name = "bf_weight")
    private double bf_weight;

    @Column(name = "af_weight")
    private double af_weight;
    
    public Long getLog_id(){
        return log_id;
    }
    public void setLog_id(Long log_id){
        this.log_id = log_id;
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

    public Long getPTR_id() {
        return PTR_id;
    }

    public void setPTR_id(Long PTR_id) {
        this.PTR_id = PTR_id;
    }

    public Long getUser_id(){
        return user_id;
    }
    public void setUser_id(Long user_id){
        this.user_id = user_id;
    }

    public LocalDate getUseday(){
        return useday;
    }
    public void setUseday(LocalDate useday){
        this.useday = useday;
    }

    public double getBf_weight(){
        return bf_weight;
    }
    public void setBf_weight(double bf_weight){
        this.bf_weight = bf_weight;
    }

    public double getAf_weight(){
        return af_weight;
    }
    public void setAf_weight(double af_weight){
        this.af_weight = af_weight;
    }
}
