package com.fastfood.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/ResetOrderServlet")
public class ResetOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Xóa các attribute để reset trang Order.jsp như lúc mới đăng nhập
        session.removeAttribute("selectedItems");
        session.removeAttribute("orderDetails");
        session.removeAttribute("lastKeyword");

        // Chuyển hướng về Order.jsp
        response.sendRedirect("Order.jsp");
    }
}
