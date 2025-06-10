package com.fastfood.dao;

import com.fastfood.model.Order;
import com.fastfood.model.OrderDetail;

import java.sql.*;

public class OrderDAO extends DAO {
    public int insertOrder(Order order) {
        int generatedId = -1;
        String sql = "INSERT INTO `order` (CreateDate, IdCustomer, Address, PaymentMethod) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, new Timestamp(order.getCreateDate().getTime()));
            ps.setInt(2, order.getCustomer().getIdCustomer());
            ps.setString(3, order.getAddress());
            ps.setString(4, order.getPaymentMethod());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    order.setIdOrder(generatedId); // Cập nhật idOrder vào đối tượng order
                }
            }

            // Chèn các OrderDetail liên quan
            if (order.getOrderDetails() != null && !order.getOrderDetails().isEmpty()) {
                OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                for (OrderDetail detail : order.getOrderDetails()) {
                    orderDetailDAO.insertOrderDetail(order, detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }
}