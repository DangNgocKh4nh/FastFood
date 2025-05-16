package com.fastfood.dao;

import com.fastfood.model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO extends DAO {

    public List<Supplier> getSupplierByName(String name) {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM supplier WHERE Name LIKE ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Supplier supplier = new Supplier();
                    supplier.setIdSupplier(rs.getInt("IdSupplier"));
                    supplier.setName(rs.getString("Name"));
                    suppliers.add(supplier);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public Supplier getSupplierById(int id) {
        String sql = "SELECT * FROM supplier WHERE IdSupllier = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setIdSupplier(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                return supplier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
