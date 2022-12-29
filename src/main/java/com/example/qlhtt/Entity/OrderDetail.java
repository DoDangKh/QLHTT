package com.example.qlhtt.Entity;

public class OrderDetail {
    private int order_id;
    private int product_id;
    private int num_of_product;
    private int cur_price;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getNum_of_product() {
        return num_of_product;
    }

    public void setNum_of_product(int num_of_product) {
        this.num_of_product = num_of_product;
    }

    public int getCur_price() {
        return cur_price;
    }

    public void setCur_price(int cur_price) {
        this.cur_price = cur_price;
    }

    public OrderDetail(int order_id, int product_id, int num_of_product) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.num_of_product = num_of_product;
    }
}
