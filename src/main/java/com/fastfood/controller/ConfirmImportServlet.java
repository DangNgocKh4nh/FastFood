package com.fastfood.controller;

import com.fastfood.dao.InvoiceDAO;
import com.fastfood.dao.InvoiceDetailDAO;
import com.fastfood.model.Ingredient;
import com.fastfood.model.Invoice;
import com.fastfood.model.InvoiceDetail;
import com.fastfood.model.Manager;
import com.fastfood.model.Supplier;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/ConfirmImportServlet")
public class ConfirmImportServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ConfirmImportServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Processing ConfirmImportServlet POST request");

        String action = request.getParameter("action");
        String supplierId = request.getParameter("supplierId");
        String keyword = request.getParameter("keyword");
        HttpSession session = request.getSession();

        if (!"confirm".equals(action)) {
            LOGGER.warning("Invalid action: " + action);
            response.sendRedirect("Import.jsp");
            return;
        }

        // Lấy danh sách nguyên liệu và số lượng từ session
        List<Ingredient> selectedIngredients = (List<Ingredient>) session.getAttribute("selectedIngredients");
        List<Integer> quantities = (List<Integer>) session.getAttribute("quantities");

        if (selectedIngredients == null || selectedIngredients.isEmpty()) {
            LOGGER.warning("No ingredients selected");
            request.setAttribute("error", "Chưa chọn nguyên liệu nào!");
            request.getRequestDispatcher("Import.jsp").forward(request, response);
            return;
        }

        // Tính tổng tiền
        double total = 0.0;
        for (int i = 0; i < selectedIngredients.size(); i++) {
            total += selectedIngredients.get(i).getPrice() * quantities.get(i);
        }

        // Lấy thông tin quản lý từ session
        Manager manager = (Manager) session.getAttribute("manager");
        if (manager == null) {
            LOGGER.warning("No manager found in session");
            request.setAttribute("error", "Vui lòng đăng nhập!");
            request.getRequestDispatcher("Import.jsp").forward(request, response);
            return;
        }

        // Tạo Supplier
        Supplier supplier = (Supplier) session.getAttribute("selectedSupplier");
        if (supplier == null) {
            LOGGER.warning("No supplier found in session");
            request.setAttribute("error", "Vui lòng chọn nhà cung cấp!");
            request.getRequestDispatcher("Import.jsp").forward(request, response);
            return;
        }

        // Tạo Invoice
        Invoice invoice = new Invoice();
        invoice.setImportDate(new Date());
        invoice.setTotal(total);
        invoice.setManager(manager);
        invoice.setSupplier(supplier);

        // Lưu Invoice và InvoiceDetail
        try {
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceDAO.insertInvoice(invoice);

            InvoiceDetailDAO detailDAO = new InvoiceDetailDAO();
            for (int i = 0; i < selectedIngredients.size(); i++) {
                InvoiceDetail detail = new InvoiceDetail();
                detail.setIdInvoice(invoice.getIdInvoice());
                detail.setQuantity(quantities.get(i));
                detail.setPrice(selectedIngredients.get(i).getPrice());
                List<Ingredient> ingredientList = new ArrayList<>();
                ingredientList.add(selectedIngredients.get(i));
                detail.setIngredients(ingredientList);
                detailDAO.insertInvoiceDetail(detail);
            }

            // Gửi dữ liệu qua request để hiển thị trên PrintInvoice.jsp
            request.setAttribute("selectedIngredients", selectedIngredients);
            request.setAttribute("quantities", quantities);
            request.setAttribute("managerName", manager.getName());
            request.setAttribute("supplierName", supplier.getName());
            request.setAttribute("keyword", keyword);

            // Xóa dữ liệu trong session sau khi gửi
            session.removeAttribute("selectedIngredients");
            session.removeAttribute("quantities");

            LOGGER.info("Forwarding to PrintInvoice.jsp");
            request.getRequestDispatcher("PrintInvoice.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.severe("Error processing invoice: " + e.getMessage());
            request.setAttribute("error", "Lỗi khi lưu hóa đơn: " + e.getMessage());
            request.getRequestDispatcher("Import.jsp").forward(request, response);
        }
    }
}