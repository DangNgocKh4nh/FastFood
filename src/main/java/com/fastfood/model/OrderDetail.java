package com.fastfood.model;

public class OrderDetail {
    private int idOrderDetail;   // Khóa chính trong bảng orderdetail
    private Item item;           // Mỗi dòng chi tiết là 1 món ăn
    private int quantity;        // Số lượng món
    private double price;        // Giá tại thời điểm đặt (lưu riêng, phòng trường hợp giá món thay đổi sau này)

    // Getters & Setters
    public int getIdOrderDetail() {
        return idOrderDetail;
    }

    public void setIdOrderDetail(int idOrderDetail) {
        this.idOrderDetail = idOrderDetail;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}