package com.fastfood.controller;

import com.fastfood.dao.UserDAO;
import com.fastfood.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            // Nếu mật khẩu không khớp, quay lại trang đăng ký kèm thông báo
            response.sendRedirect("register.jsp?error=confirm");
            return;
        }

        UserDAO userDAO = new UserDAO();
        if (userDAO.getUserByName(username) != null) {
            // Nếu username đã tồn tại, quay lại trang đăng ký kèm thông báo
            response.sendRedirect("register.jsp?error=exists");
            return;
        }

        // Tạo user mới với role mặc định là "customer"
        User user = new User(0, username, password, "customer");
        userDAO.register(user);

        response.sendRedirect("login.jsp");
    }
}
