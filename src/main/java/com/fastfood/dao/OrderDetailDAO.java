package com.fastfood.dao;

import com.fastfood.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailDAO extends DAO {
    public void insertOrderDetail(OrderDetail detail) {
        String sql = "INSERT INTO orderdetail (IdOrder, IdItem, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, detail.getOrderId());
            ps.setInt(2, detail.getItem().getIdItem());
            ps.setInt(3, detail.getQuantity());
            double totalPrice = detail.getPrice() * detail.getQuantity();
            ps.setDouble(4, totalPrice);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
