package com.fastfood.controller;

import com.fastfood.model.Supplier;
import com.fastfood.dao.SupplierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SelectSupplierServlet")
public class SelectSupplierServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Supplier> supplierList = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            SupplierDAO dao = new SupplierDAO();
            supplierList = dao.getSupplierByName(keyword);

            // Nếu có ít nhất một nhà cung cấp được tìm thấy, lưu vào session
            if (!supplierList.isEmpty()) {
                HttpSession session = request.getSession();
                session.setAttribute("selectedSupplier", supplierList.get(0)); // Lưu nhà cung cấp đầu tiên
            }
        }

        request.setAttribute("supplierList", supplierList);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("SelectSupplier.jsp").forward(request, response);
    }
}
