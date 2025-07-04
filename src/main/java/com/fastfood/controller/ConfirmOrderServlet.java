package com.fastfood.controller;

import com.fastfood.dao.OrderDAO;
import com.fastfood.model.*;
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

@WebServlet("/ConfirmOrderServlet")
public class ConfirmOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        List<OrderDetail> orderDetails = (List<OrderDetail>) session.getAttribute("orderDetails");

        if (customer == null || orderDetails == null || orderDetails.isEmpty()) {
            response.sendRedirect("Payment.jsp?error=saveFail");
            return;
        }

        String address = request.getParameter("address");
        String paymentMethod = request.getParameter("paymentMethod"); // Lấy phương thức thanh toán
        if (address == null || address.trim().isEmpty() || paymentMethod == null || paymentMethod.trim().isEmpty()) {
            response.sendRedirect("Payment.jsp?error=missingInfo");
            return;
        }

        double total = 0;
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetail detail = orderDetails.get(i);
            String quantityParam = request.getParameter("quantity_" + i);
            if (quantityParam != null && !quantityParam.trim().isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityParam);
                    if (quantity > 0) {
                        detail.setQuantity(quantity);  // Cập nhật số lượng mới vào detail
                        total += detail.getPrice() * quantity;
                    } else {
                        response.sendRedirect("Payment.jsp?error=invalidQuantity");
                        return;
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("Payment.jsp?error=invalidQuantity");
                    return;
                }
            } else {
                total += detail.getPrice() * detail.getQuantity(); // Giữ nguyên số lượng cũ nếu không thay đổi
            }
        }
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setCustomer(customer);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setOrderDetails(orderDetails);
        // Tạo đối tượng OrderDAO để lưu đơn hàng vào cơ sở dữ liệu
        OrderDAO orderDAO = new OrderDAO();
        int orderId = orderDAO.insertOrder(order);  // Lưu đơn hàng và các chi tiết liên quan
        // Kiểm tra nếu lưu đơn hàng thành công
        if (orderId != -1) {
            // Xóa giỏ hàng (clear orderDetails trong session)
            session.removeAttribute("orderDetails");

            // Chuyển tiếp đến trang OrderSuccess.jsp để hiển thị thông tin đơn hàng
            request.setAttribute("order", order);
            request.setAttribute("orderId", orderId);
            request.setAttribute("address", address);
            request.setAttribute("paymentMethod", paymentMethod);
            request.getRequestDispatcher("PrintOrder.jsp").forward(request, response);
        } else {
            // Nếu lưu đơn hàng không thành công, chuyển về trang Payment.jsp với thông báo lỗi
            response.sendRedirect("Payment.jsp?error=saveFail");
        }
    }
}