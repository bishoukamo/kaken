package com.kitakokaken.CMS.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="ptr_itiran") //""のテーブルに対応
public class PTR_itiran{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "PTR_id")
    private Long PTR_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "PTR_name")
    private String PTR_name;

    public Long getPTR_id(){
        return PTR_id;
    }
    public void setPTR_id(Long PTR_id){
        this.PTR_id = PTR_id;
    }

    public Long getUser_id(){
        return user_id;
    }
    public void setUser_id(Long user_id){
        this.user_id = user_id;
    }

    public String getPTR_name() {
        return PTR_name;
    }

    public void setPTR_name(String PTR_name) {
        this.PTR_name = PTR_name;
    }
}