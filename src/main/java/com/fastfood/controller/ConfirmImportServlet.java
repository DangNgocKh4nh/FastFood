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

        // Tạo Invoice và danh sách InvoiceDetail
        Invoice invoice = new Invoice();
        invoice.setImportDate(new Date());
        invoice.setManager(manager);
        invoice.setSupplier(supplier);

        List<InvoiceDetail> invoiceDetails = new ArrayList<>();
        for (int i = 0; i < selectedIngredients.size(); i++) {
            InvoiceDetail detail = new InvoiceDetail();
            detail.setQuantity(quantities.get(i));

            // Lấy giá tùy chỉnh từ request, nếu không có thì dùng giá từ Ingredient
            String customPriceParam = request.getParameter("price_" + i);
            double price = selectedIngredients.get(i).getPrice(); // Giá mặc định từ Ingredient
            if (customPriceParam != null && !customPriceParam.trim().isEmpty()) {
                try {
                    double customPrice = Double.parseDouble(customPriceParam);
                    if (customPrice >= 0) {
                        price = customPrice; // Sử dụng giá tùy chỉnh nếu hợp lệ
                    } else {
                        LOGGER.warning("Invalid custom price for ingredient " + i + ": " + customPriceParam);
                    }
                } catch (NumberFormatException e) {
                    LOGGER.warning("Invalid number format for custom price " + i + ": " + customPriceParam);
                }
            }
            detail.setPrice(price);
            detail.setIngredient(selectedIngredients.get(i));
            invoiceDetails.add(detail);
        }
        invoice.setInvoiceDetails(invoiceDetails);

        // Lưu Invoice và InvoiceDetail
        try {
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceDAO.insertInvoice(invoice);

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