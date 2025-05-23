package com.fastfood.dao;

import com.fastfood.model.Order;
import com.fastfood.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailDAO extends DAO {
    public void insertOrderDetail(Order order, OrderDetail detail) {
        String sql = "INSERT INTO orderdetail (IdOrder, IdItem, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, order.getIdOrder());
            ps.setInt(2, detail.getItem().getIdItem());
            ps.setInt(3, detail.getQuantity());
            ps.setDouble(4, detail.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}