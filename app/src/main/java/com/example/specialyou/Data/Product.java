package com.example.specialyou.Data;

import java.io.Serializable;

public class Product implements Serializable {
    public String id;
    public String link;
    public String name;
    public String stock;
    public String price;


    public Product(){

    }

    public Product(String id, String link, String name, String stock, String price) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }
}
