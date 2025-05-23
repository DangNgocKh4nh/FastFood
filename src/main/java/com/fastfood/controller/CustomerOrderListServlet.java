package com.fastfood.controller;

import com.fastfood.dao.CustomerRevenueStatsDAO;
import com.fastfood.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/CustomerOrderListServlet")
public class CustomerOrderListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCustomerStr = request.getParameter("idCustomer");
        int idCustomer;
        try {
            idCustomer = Integer.parseInt(idCustomerStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("CustomerStats.jsp");
            return;
        }

        CustomerRevenueStatsDAO statsDAO = new CustomerRevenueStatsDAO();
        List<Order> orders = statsDAO.getOrdersByCustomerId(idCustomer);
        request.setAttribute("orders", orders);
        request.setAttribute("idCustomer", idCustomer);
        request.getRequestDispatcher("CustomerOrderList.jsp").forward(request, response);
    }
}