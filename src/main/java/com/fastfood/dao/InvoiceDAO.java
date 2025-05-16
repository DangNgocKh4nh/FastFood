package com.fastfood.dao;

import com.fastfood.model.Invoice;
import com.fastfood.model.InvoiceDetail;

import java.sql.*;

public class InvoiceDAO extends DAO {
    public void insertInvoice(Invoice invoice) {
        String sql = "INSERT INTO invoice (ImportDate, IdManager, IdSupplier) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, new Timestamp(invoice.getImportDate().getTime()));
            stmt.setInt(2, invoice.getManager().getIdManager());
            stmt.setInt(3, invoice.getSupplier().getIdSupplier());
            stmt.executeUpdate();

            // Lấy IdInvoice vừa tạo
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    invoice.setIdInvoice(rs.getInt(1));
                }
            }

            // Chèn các InvoiceDetail liên quan
            if (invoice.getInvoiceDetails() != null && !invoice.getInvoiceDetails().isEmpty()) {
                InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();
                for (InvoiceDetail detail : invoice.getInvoiceDetails()) {
                    invoiceDetailDAO.insertInvoiceDetail(invoice, detail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}