package com.fastfood.model;

public class User {
    private int idUser;      // Thêm thuộc tính idUser
    private String username;
    private String password;
    private String role;

    // Constructor mặc định
    public User() {
    }

    // Constructor có tham số để khởi tạo thông tin User
    public User(int idUser, String username, String password, String role) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters và Setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
