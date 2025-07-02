package com.fastfood.model;

public class Customer extends User {
    private int idCustomer;
    private int idUser;
    private String name;
    private String phoneNumber;
    private String email;

    public Customer() {
    }

    public Customer(int idCustomer, String name, String phone, String email) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.phoneNumber = phone;
        this.email = email;
    }
    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
