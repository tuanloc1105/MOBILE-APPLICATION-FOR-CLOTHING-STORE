package com.example.shopping.model;

import java.util.List;

public class Order {
    private long id;
    private String username;
    private String paymentMethod;
    private String address;
    private String status;
    private int tien;
    private List<Cart> product;

    public Order(long id, String username, String paymentMethod, String address, String status, int tien, List<Cart> product) {
        this.id = id;
        this.username = username;
        this.paymentMethod = paymentMethod;
        this.address = address;
        this.status = status;
        this.tien = tien;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public List<Cart> getProduct() {
        return product;
    }

    public void setProduct(List<Cart> product) {
        this.product = product;
    }
}
