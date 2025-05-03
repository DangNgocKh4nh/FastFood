package com.fastfood.dao;

import com.fastfood.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO extends DAO {
    public Customer getCustomerById(int idCustomer) {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE IdCustomer = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCustomer);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setIdCustomer(idCustomer);
                    customer.setIdUser(rs.getInt("IdUser"));
                    customer.setName(rs.getString("Name"));
                    customer.setPhoneNumber(rs.getString("PhoneNumber"));
                    customer.setEmail(rs.getString("Email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    public Customer getCustomerByUserId(int idUser) {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE IdUser = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setIdCustomer(rs.getInt("IdCustomer"));
                    customer.setIdUser(idUser);
                    customer.setName(rs.getString("Name"));
                    customer.setPhoneNumber(rs.getString("PhoneNumber"));
                    customer.setEmail(rs.getString("Email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }


}