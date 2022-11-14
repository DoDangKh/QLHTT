package com.example.qlhtt.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class Type {

    private String Type_id;
    private String name;

    public String getType_id() {
        return Type_id;
    }

    public void setType_id(String type_id) {
        Type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
