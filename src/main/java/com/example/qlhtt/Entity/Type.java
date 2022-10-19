package com.example.qlhtt.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Type")
public class Type {

    @Id
    @Column(name="type_id",columnDefinition = "nvarchar(15)")
    private String Type_id;
    @Column(name="name",columnDefinition = "nvarchar(30) not null")
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
