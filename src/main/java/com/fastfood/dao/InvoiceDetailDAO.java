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
}