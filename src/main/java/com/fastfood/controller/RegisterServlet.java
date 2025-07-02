package com.fastfood.controller;

import com.fastfood.dao.UserDAO;
import com.fastfood.dao.CustomerDAO;
import com.fastfood.model.Customer;
import com.fastfood.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("Customer");

        try {
            userDAO.register(user); // Lưu User và gán IdUser
            if (user.getIdUser() > 0) {
                Customer customer = new Customer();
                customer.setIdCustomer(user.getIdUser()); // Sử dụng IdUser làm IdCustomer
                customer.setIdUser(user.getIdUser()); // Đặt IdUser làm khóa ngoại
                customer.setName(name);
                customer.setPhoneNumber(phoneNumber);
                customer.setEmail(email);

                customerDAO.insertCustomer(customer); // Lưu Customer
                response.sendRedirect("login.jsp");
            } else {
                response.sendRedirect("register.jsp?error=exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=exists");
        }
    }
}