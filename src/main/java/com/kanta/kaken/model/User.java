package com.kanta.kaken.model;

public class User {

    private String name;
    private int age;
    private String country;
    private boolean admin;

    public User(String name, int age, String country, boolean admin) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public  String getCountry() {
        return country;
    }

    public boolean isAdmin() {
        return admin;
    }

}