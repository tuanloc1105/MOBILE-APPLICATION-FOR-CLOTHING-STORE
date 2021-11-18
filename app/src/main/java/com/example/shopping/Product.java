package com.example.shopping;

import java.io.Serializable;

public class Product implements Serializable {
    String id, name, image, size;
    int price;

    public Product(){}

    public Product(String id, String image, String name, int price, String size) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.size = size;
    }

    public String gethatid() {
        return id;
    }

    public String gethatname() {
        return name;
    }

    public String getimage() {
        return image;
    }

    public int getprice() {
        return price;
    }

    public String getsize() {
        return size;
    }

    public void setid(String id) {
        this.id = id;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setimage(String image) {
        this.image = image;
    }

    public void setprice(int price) {
        this.price = price;
    }

    public void setsize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';
    }
}
