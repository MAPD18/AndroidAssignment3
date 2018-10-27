package com.example.rodrigosilva.shoppingapp.model;

import java.util.Date;

public class Order {

    private int id;
    private Customer customer;
    private Shoe shoe;
    private Date date;
    private int quantity;
    private Status status;

    public Order(Customer customer, Shoe shoe, int quantity) {
        this.customer = customer;
        this.shoe = shoe;
        this.date = new Date();
        this.quantity = quantity;
        this.status = Status.IN_PROGRESS;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatus(String statusStr) {
        if (statusStr.equals(Status.IN_PROGRESS.name())) {
            setStatus(Status.IN_PROGRESS);
        } else if (statusStr.equals(Status.SHIPPED.name())) {
            setStatus(Status.SHIPPED);
        } else if (statusStr.equals(Status.DELIVERED.name())) {
            setStatus(Status.DELIVERED);
            }
        }
}
