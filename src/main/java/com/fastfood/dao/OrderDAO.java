package com.fastfood.dao;

import com.fastfood.model.Order;

import java.sql.*;

public class OrderDAO extends DAO {
    public int insertOrder(Order order) {
        int generatedId = -1;
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }
}
