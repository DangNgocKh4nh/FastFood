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
            ps.setDouble(4, detail.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OrderDetail getOrderDetail(int idOrderDetail) {
        // Phần này bạn có thể hoàn thiện sau nếu cần lấy thông tin chi tiết từ DB
        // Tạm thời mình để return null vì chưa có yêu cầu cụ thể
        return null;
    }
}
