package com.example.qlhtt.Entity;

import java.util.Date;

public class CustomerOrder {
    private int order_id;
    private Date order_time;
    private int status_now;
    private int staff_id;
    private int customer_id;
    private int total_money;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public int getStatus_now() {
        return status_now;
    }

    public void setStatus_now(int status_now) {
        this.status_now = status_now;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getTotal_money() {
        return total_money;
    }

    public void setTotal_money(int total_money) {
        this.total_money = total_money;
    }
}
