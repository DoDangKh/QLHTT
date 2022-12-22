package com.example.qlhtt.Entity;

public class Product {
    private int product_id;
    private String name;
    private int quantity;
    private int price;
    private String img;
    private String describe;
    private int status;
    private int type_id;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public Product(int product_id, String name, int quantity, int price, String img, String describe, int status, int type_id) {
        this.product_id = product_id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.img = img;
        this.describe = describe;
        this.status = status;
        this.type_id = type_id;
    }

    public Product() {
    }
}
