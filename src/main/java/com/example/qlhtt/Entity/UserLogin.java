package com.example.qlhtt.Entity;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

public class UserLogin {
    private int person_id;

    private String username;

    private String password;

    private int role_id;

    public int getPerson_id() {
        return person_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole_id() {
        return role_id;
    }

    private int enable;

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public UserLogin() {
    }

    public UserLogin(int person_id, String username, String password, int enable,int role_id) {
        this.person_id = person_id;
        this.username = username;
        this.password = password;
        this.role_id = role_id;
        this.enable=enable;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
