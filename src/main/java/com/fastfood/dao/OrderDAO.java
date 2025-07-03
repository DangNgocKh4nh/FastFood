package com.fastfood.dao;

import com.fastfood.model.Order;
import com.fastfood.model.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public void updateOrder(Order order) {
        String sql = "UPDATE `order` SET PaymentMethod = ?, state = ? WHERE IdOrder = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, order.getPaymentMethod());
            ps.setString(2, order.getState());
            ps.setInt(3, order.getIdOrder());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `order`";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setIdOrder(rs.getInt("IdOrder"));
                order.setCreateDate(rs.getTimestamp("CreateDate"));
                order.setAddress(rs.getString("Address"));
                order.setPaymentMethod(rs.getString("PaymentMethod"));
                order.setState(rs.getString("State"));
                orders.add(order);
                System.out.println("Loaded order: " + order.getIdOrder() + ", " + order.getPaymentMethod());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage());
        }
        return orders;
    }
}