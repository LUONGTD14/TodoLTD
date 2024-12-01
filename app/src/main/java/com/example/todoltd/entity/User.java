package com.example.todoltd.entity;

import java.util.ArrayList;

public class User {
    private String idUser;
    private String nameUser;
    private String emailUser;
    private String pwUser;
    private String imgUser;
    private ArrayList<TodoEntity> arr;

    public User() {
    }

    public User(String idUser, String nameUser, String emailUser, String pwUser) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.pwUser = pwUser;
        this.arr = new ArrayList<TodoEntity>();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPwUser() {
        return pwUser;
    }

    public void setPwUser(String pwUser) {
        this.pwUser = pwUser;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }
}
