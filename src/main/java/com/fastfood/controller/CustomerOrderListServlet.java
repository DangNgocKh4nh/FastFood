package com.fastfood.controller;

import com.fastfood.dao.CustomerRevenueStatsDAO;
import com.fastfood.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

        // Lấy ngày bắt đầu và ngày kết thúc từ request
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        CustomerRevenueStatsDAO statsDAO = new CustomerRevenueStatsDAO();
        List<Order> orders;
        if (startDateStr == null || startDateStr.isEmpty() || endDateStr == null || endDateStr.isEmpty()) {
            // Nếu không nhập ngày, lấy tất cả đơn hàng
            orders = statsDAO.getOrdersByCustomerId(idCustomer);
        } else {
            // Chuyển đổi định dạng ngày (giả định định dạng yyyy-MM-dd)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            orders = statsDAO.getOrdersByDate(idCustomer, startDateStr, endDateStr);
        }

        // Gửi lại các giá trị startDate và endDate để hiển thị trong form
        request.setAttribute("startDate", startDateStr);
        request.setAttribute("endDate", endDateStr);
        request.setAttribute("orders", orders);
        request.setAttribute("idCustomer", idCustomer);
        request.getRequestDispatcher("CustomerOrderList.jsp").forward(request, response);
    }
}