package com.example.qlhtt.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class Type {

    private Long Type_id;
    private String name;

    public Long getType_id() {
        return Type_id;
    }

    public void setType_id(Long type_id) {
        Type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type(Long type_id, String name) {
        Type_id = type_id;
        this.name = name;
    }

    public Type() {
    }
}
