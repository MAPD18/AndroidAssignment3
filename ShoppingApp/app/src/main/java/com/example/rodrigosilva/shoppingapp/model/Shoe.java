package com.example.rodrigosilva.shoppingapp.model;

public class Shoe {

    private int id;
    private String name;
    private int category;
    private int size;
    private float price;

    public Shoe(String name, int category, int size, float price) {
        this.name = name;
        this.category = category;
        this.size = size;
        this.price = price;
    }

    public Shoe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
