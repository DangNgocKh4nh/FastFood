package com.fastfood.dao;

import com.fastfood.model.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO extends DAO {

    // Lấy danh sách nguyên liệu theo tên và IdSupplier
    public List<Ingredient> getIngredientByName(String keyword, int supplierId) {
        List<Ingredient> ingredientList = new ArrayList<>();
        String sql = "SELECT * FROM ingredient WHERE Name LIKE ? AND IdSupplier = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, supplierId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setIdIngredient(rs.getInt("IdIngredient"));
                    ingredient.setName(rs.getString("Name"));
                    ingredient.setPrice(rs.getDouble("Price"));
                    ingredientList.add(ingredient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredientList;
    }

    // Lấy nguyên liệu theo IdIngredient
    public Ingredient getIngredientById(int id) {
        Ingredient ingredient = null;
        String sql = "SELECT * FROM ingredient WHERE IdIngredient = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ingredient = new Ingredient();
                    ingredient.setIdIngredient(rs.getInt("IdIngredient"));
                    ingredient.setName(rs.getString("Name"));
                    ingredient.setPrice(rs.getDouble("Price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredient;
    }
}