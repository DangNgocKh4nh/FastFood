package com.fastfood.dao;

import com.fastfood.model.CustomerRevenueStats;
import com.fastfood.model.Order;
import com.fastfood.model.OrderDetail;
import com.fastfood.model.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRevenueStatsDAO extends DAO {
    public List<CustomerRevenueStats> getCustomerRevenueStats() {
        List<CustomerRevenueStats> statsList = new ArrayList<>();
        String sql = "SELECT c.IdCustomer, c.Name, c.PhoneNumber, c.Email, COALESCE(SUM(od.Price), 0) AS revenue " +
                "FROM customer c " +
                "LEFT JOIN `order` o ON c.IdCustomer = o.IdCustomer " +
                "LEFT JOIN orderdetail od ON o.IdOrder = od.IdOrder " +
                "GROUP BY c.IdCustomer, c.Name, c.PhoneNumber, c.Email " +
                "ORDER BY revenue DESC";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("Executing query: " + sql);
            while (rs.next()) {
                CustomerRevenueStats stats = new CustomerRevenueStats(
                        rs.getInt("IdCustomer"),
                        rs.getString("Name"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email"),
                        rs.getDouble("revenue")
                );
                statsList.add(stats);
                System.out.println("Found customer: " + stats);
            }
            if (statsList.isEmpty()) {
                System.out.println("No customer revenue stats found.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return statsList;
    }

    public List<Order> getOrdersByCustomerId(int idCustomer) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.IdOrder, o.CreateDate, o.Address, " +
                "od.IdOrderDetail, od.IdItem, i.Name AS ItemName, od.Quantity, i.Price " +
                "FROM `order` o " +
                "LEFT JOIN orderdetail od ON o.IdOrder = od.IdOrder " +
                "LEFT JOIN item i ON od.IdItem = i.IdItem " +
                "WHERE o.IdCustomer = ? " +
                "ORDER BY o.CreateDate DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCustomer);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Executing query: " + sql + " with idCustomer: " + idCustomer);
                Order currentOrder = null;
                while (rs.next()) {
                    int orderId = rs.getInt("IdOrder");
                    if (currentOrder == null || currentOrder.getIdOrder() != orderId) {
                        currentOrder = new Order();
                        currentOrder.setIdOrder(orderId);
                        currentOrder.setCreateDate(rs.getTimestamp("CreateDate"));
                        currentOrder.setAddress(rs.getString("Address"));
                        currentOrder.setOrderDetails(new ArrayList<>());
                        orders.add(currentOrder);
                    }

                    // Thêm chi tiết đơn hàng nếu có
                    int orderDetailId = rs.getInt("IdOrderDetail");
                    if (!rs.wasNull()) { // Kiểm tra nếu có chi tiết đơn hàng
                        OrderDetail detail = new OrderDetail();
                        detail.setIdOrderDetail(orderDetailId);
                        Item item = new Item();
                        item.setIdItem(rs.getInt("IdItem"));
                        item.setName(rs.getString("ItemName"));
                        detail.setItem(item);
                        detail.setQuantity(rs.getInt("Quantity"));
                        detail.setPrice(rs.getDouble("Price"));
                        currentOrder.getOrderDetails().add(detail);
                    }
                }
                if (orders.isEmpty()) {
                    System.out.println("No orders found for customer id: " + idCustomer);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }
}