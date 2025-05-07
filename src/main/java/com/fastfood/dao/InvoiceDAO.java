package com.fastfood.dao;

import com.fastfood.model.Invoice;

import java.sql.*;

public class InvoiceDAO extends DAO {
    public void insertInvoice(Invoice invoice) {
        String sql = "INSERT INTO invoice (ImportDate, Total, IdManager, IdSupplier) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, new Timestamp(invoice.getImportDate().getTime()));
            stmt.setDouble(2, invoice.getTotal());
            stmt.setInt(3, invoice.getManager().getIdManager());
            stmt.setInt(4, invoice.getSupplier().getIdSupplier());
            stmt.executeUpdate();

            // Lấy IdInvoice vừa tạo
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                invoice.setIdInvoice(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}