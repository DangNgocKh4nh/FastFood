package com.fastfood.dao;

import com.fastfood.model.Order;
import com.fastfood.model.OrderDetail;

import java.sql.*;

public class OrderDAO extends DAO {
    public int insertOrder(Order order) {
        int generatedId = -1;
        // Tính lại Total từ orderDetails
        double calculatedTotal = 0;
        if (order.getOrderDetails() != null) {
            for (OrderDetail detail : order.getOrderDetails()) {
                calculatedTotal += detail.getPrice() * detail.getQuantity();
            }
        }
        order.setTotal(calculatedTotal);

        String sql = "INSERT INTO `order` (Total, CreateDate, IdCustomer, Address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, order.getTotal());
            ps.setTimestamp(2, new Timestamp(order.getCreateDate().getTime()));
            ps.setInt(3, order.getCustomer().getIdCustomer());
            ps.setString(4, order.getAddress());
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