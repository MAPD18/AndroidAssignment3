package com.example.rodrigosilva.shoppingapp.model;

public class Customer {

    private int id;
    private String userName;
    private String password;
    private String firstName;
    private String LastName;
    private String address;
    private String city;
    private String postalCode;

    public Customer(String userName, String password, String firstName, String lastName, String address, String city, String postalCode) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        LastName = lastName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Customer() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
