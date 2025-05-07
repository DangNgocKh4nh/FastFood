package com.fastfood.controller;

import com.fastfood.dao.ManagerDAO;
import com.fastfood.dao.UserDAO;
import com.fastfood.dao.CustomerDAO;
import com.fastfood.model.Manager;
import com.fastfood.model.User;
import com.fastfood.model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByName(username);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // Lưu User

            if ("customer".equalsIgnoreCase(user.getRole())) {
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerByUserId(user.getIdUser());
                if (customer != null) {
                    session.setAttribute("customer", customer); // Lưu Customer
                }
                response.sendRedirect("Order.jsp");
            } else if ("manager".equalsIgnoreCase(user.getRole())) {
                ManagerDAO managerDAO = new ManagerDAO();
                Manager manager = managerDAO.getManagerByUserId(user.getIdUser());
                if (manager != null) {
                    session.setAttribute("manager", manager); // Lưu Manager
                }
                response.sendRedirect("SelectSupplier.jsp");
            } else {
                response.sendRedirect("login.jsp?error=unknown_role");
            }
        } else {
            response.sendRedirect("login.jsp?error=true");
        }
    }
}
