package com.fastfood.dao;

import com.fastfood.model.Ingredient;
import com.fastfood.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO extends DAO {

    public List<Ingredient> getIngredientByName(String name) {
        List<Ingredient> list = new ArrayList<>();
        try {
            String sql = "SELECT i.*, s.IdSupplier, s.Name as SupplierName FROM ingredient i JOIN supplier s ON i.IdSupplier = s.IdSupplier WHERE i.Name LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ingredient ing = new Ingredient();
                ing.setIdIngredient(rs.getInt("IdIngredient"));
                ing.setName(rs.getString("Name"));
                ing.setPrice(rs.getDouble("Price"));

                Supplier supplier = new Supplier();
                supplier.setIdSupplier(rs.getInt("IdSupplier"));
                supplier.setName(rs.getString("SupplierName"));
                ing.setSupplier(supplier);

                list.add(ing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Ingredient getIngredientById(int id) {
        String sql = "SELECT i.IdIngredient, i.Name, i.Price, s.IdSupplier, s.Name AS SupplierName " +
                "FROM ingredient i JOIN supplier s ON i.IdSupplier = s.IdSupplier " +
                "WHERE i.IdIngredient = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Ingredient ing = new Ingredient();
                ing.setIdIngredient(rs.getInt("IdIngredient"));
                ing.setName(rs.getString("Name"));
                ing.setPrice(rs.getDouble("Price"));

                Supplier supplier = new Supplier();
                supplier.setIdSupplier(rs.getInt("IdSupplier"));
                supplier.setName(rs.getString("SupplierName"));
                ing.setSupplier(supplier);

                return ing;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
