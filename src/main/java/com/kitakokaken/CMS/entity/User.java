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
import com.kitakokaken.CMS.entity.Enum.Yakuwari;

@Entity
@Table(name="user") //""のテーブルに対応
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "u_name")
    private String u_name;

    @Column(name = "yakuwari")
    @Enumerated(EnumType.STRING)
    private Yakuwari yakuwari;

    public Yakuwari getYakuwari() {
       return yakuwari;
    }

    public void setYakuwari(Yakuwari yakuwari) {
       this.yakuwari = yakuwari;
    }

    @Column(name = "gakuban")
    private Long gakuban;

    @Column(name = "mail", unique = true)
    private String mail;

    @Column(name = "pass")
    private String pass;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Column(name = "setting")
    private LocalDateTime setting;

    public Long getUser_id(){
        return user_id;
    }
    public void setUser_id(Long user_id){
        this.user_id=user_id;
    }

    public String getU_name(){
        return u_name;
    }
    public void setU_name(String u_name){
        this.u_name=u_name;
    }


    public Long getGakuban(){
        return gakuban;
    }
    public void setGakuban(Long gakuban){
        this.gakuban=gakuban;
    }

    public LocalDateTime getSetting(){
        return setting;
    }
    public void setSetting(LocalDateTime setting){
        this.setting=setting;
    }

    //管理ユーザ申請中
    @Column(name = "teach_request")
    private boolean teachRequest;

    public boolean isTeachRequest() {
        return teachRequest;
    }

    public void setTeachRequest(boolean teachRequest) {
        this.teachRequest = teachRequest;
    }
}