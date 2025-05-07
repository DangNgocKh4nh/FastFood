package com.fastfood.dao;

import com.fastfood.model.Ingredient;
import com.fastfood.model.InvoiceDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO extends DAO {
    public void insertInvoiceDetail(InvoiceDetail detail) {
        String sql = "INSERT INTO invoicedetail (IdInvoice, IdIngredient, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, detail.getIdInvoice());
            stmt.setInt(2, detail.getIngredients().get(0).getIdIngredient());
            stmt.setInt(3, detail.getQuantity());
            stmt.setDouble(4, detail.getPrice());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InvoiceDetail getInvoiceDetail(int id) {
        String sql = "SELECT * FROM invoicedetail WHERE IdInvoiceDetail = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                InvoiceDetail detail = new InvoiceDetail();
                detail.setIdInvoiceDetail(rs.getInt("IdInvoiceDetail"));
                detail.setQuantity(rs.getInt("Quantity"));
                detail.setPrice(rs.getDouble("Price"));

                // Lấy Ingredient từ IdIngredient
                IngredientDAO ingredientDAO = new IngredientDAO();
                Ingredient ingredient = ingredientDAO.getIngredientById(rs.getInt("IdIngredient"));
                List<Ingredient> ingredients = new ArrayList<>();
                ingredients.add(ingredient);
                detail.setIngredients(ingredients);

                return detail;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}