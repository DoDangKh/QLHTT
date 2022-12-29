package com.example.qlhtt.Entity;

public class Cart {
    private int customer_id;
    private Product product;
    private int quantity;


    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart(int customer_id,int product_id, int quantity) {
        this.customer_id = customer_id;
        this.product= new Product();
        this.product.setProduct_id(product_id);
        this.quantity = quantity;
    }
}
