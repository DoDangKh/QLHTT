package com.example.qlhtt.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
 @Table(name="Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "nvarchar(30) not null")
    private String name;

    @Pattern(regexp="Nam | Nữ",message = "chỉ nhận giá trị Nam hoặc Nữ")
    @Column(columnDefinition = "nvarchar(4) not null")
    private String Gender;

    @Column(columnDefinition = "nvarchar(13) not null ")
    private String identity_card;

    @Temporal(TemporalType.DATE)
    private Date day_of_birth;

    @Column(columnDefinition = "nvarchar(10) not null")
    private String phone_num;

    @Column(columnDefinition = "nvarchar(50) not null")
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
}
