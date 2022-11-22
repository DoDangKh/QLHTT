package com.example.qlhtt.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;


public class Person {
    private int id;
    private String name;

    private String Gender;

    private String identity_card;

    private Date day_of_birth;

    private String phone_num;

    private String address;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public Date getDate_of_birth() {
        return day_of_birth;
    }

    public void setDate_of_birth(Date day_of_birth) {
        this.day_of_birth = day_of_birth;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person() {
    }

    public Person(int id, String name, String gender, String identity_card, Date day_of_birth, String phone_num, String address) {
        this.id = id;
        this.name = name;
        Gender = gender;
        this.identity_card = identity_card;
        this.day_of_birth = day_of_birth;
        this.phone_num = phone_num;
        this.address = address;
    }
}
