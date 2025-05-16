package com.fastfood.dao;

import com.fastfood.model.Invoice;
import com.fastfood.model.InvoiceDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InvoiceDetailDAO extends DAO {
    public void insertInvoiceDetail(Invoice invoice, InvoiceDetail detail) {
        String sql = "INSERT INTO invoicedetail (IdInvoice, IdIngredient, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, invoice.getIdInvoice());
            stmt.setInt(2, detail.getIngredient().getIdIngredient());
            stmt.setInt(3, detail.getQuantity());
            stmt.setDouble(4, detail.getPrice()); // Lưu giá mỗi nguyên liệu, không nhân số lượng
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}