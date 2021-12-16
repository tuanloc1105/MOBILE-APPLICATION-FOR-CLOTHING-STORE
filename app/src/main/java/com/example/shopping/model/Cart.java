package com.example.shopping.model;

import java.io.Serializable;

public class Cart implements Serializable {

    private String id;
    private String name, image, size;
    private int price;
    private int count;

    public Cart(){}

    public Cart(String id, String name, String image, String size, int price, int count) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.size = size;
        this.price = price;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getSize() {
        return size;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}

