package com.fastfood.controller;

import com.fastfood.model.Supplier;
import com.fastfood.dao.SupplierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        }

        request.setAttribute("supplierList", supplierList);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("SelectSupplier.jsp").forward(request, response);
    }
}
