package com.fastfood.dao;

import com.fastfood.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO extends DAO {

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
    public void insertCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (IdCustomer, IdUser, Name, PhoneNumber, Email) VALUES (?, ?, ?, ?, ?)";
        if (this.con == null) {
            System.out.println("Connection is null in CustomerDAO");
            return;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customer.getIdCustomer());
            ps.setInt(2, customer.getIdUser());
            ps.setString(3, customer.getName());
            ps.setString(4, customer.getPhoneNumber());
            ps.setString(5, customer.getEmail());
            ps.executeUpdate();
            System.out.println("Customer inserted successfully for IdCustomer: " + customer.getIdCustomer());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

}