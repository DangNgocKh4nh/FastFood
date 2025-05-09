package com.fastfood.dao;

import com.fastfood.model.InvoiceDetail;

import java.sql.PreparedStatement;

public class InvoiceDetailDAO extends DAO {
    public void insertInvoiceDetail(InvoiceDetail detail) {
        String sql = "INSERT INTO invoicedetail (IdInvoice, IdIngredient, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, detail.getIdInvoice());
            stmt.setInt(2, detail.getIngredients().get(0).getIdIngredient());
            stmt.setInt(3, detail.getQuantity());
            double totalPrice = detail.getPrice() * detail.getQuantity();
            stmt.setDouble(4, totalPrice);
            stmt.setDouble(4, totalPrice);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}