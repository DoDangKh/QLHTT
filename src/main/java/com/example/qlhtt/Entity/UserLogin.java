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

    public UserLogin() {
    }

    public UserLogin(int person_id, String username, String password, int role_id) {
        this.person_id = person_id;
        this.username = username;
        this.password = password;
        this.role_id = role_id;
    }
}
