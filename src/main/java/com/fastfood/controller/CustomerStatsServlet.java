package com.fastfood.controller;

import com.fastfood.dao.CustomerRevenueStatsDAO;
import com.fastfood.model.CustomerRevenueStats;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/CustomerStatsServlet")
public class CustomerStatsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerRevenueStatsDAO statsDAO = new CustomerRevenueStatsDAO();
        List<CustomerRevenueStats> statsList = statsDAO.getCustomerRevenueStats();
        request.setAttribute("statsList", statsList);
        request.getRequestDispatcher("CustomerStats.jsp").forward(request, response);
    }
}