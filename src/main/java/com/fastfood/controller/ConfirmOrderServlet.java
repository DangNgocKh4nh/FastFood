package com.fastfood.controller;

import com.fastfood.dao.OrderDAO;
import com.fastfood.model.*;
import com.fastfood.payment.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
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
        if (address == null || address.trim().isEmpty()) {
            response.sendRedirect("Payment.jsp?error=missingAddress");
            return;
        }

        String paymentMethod = request.getParameter("paymentMethod");
        PaymentProvider provider;
        switch (paymentMethod) {
            case "Momo":
                provider = new MomoPayment();
                break;
            case "ZaloPay":
                provider = new ZaloPayPayment();
                break;
            case "InternetBanking":
                provider = new InternetBankingPayment();
                break;
            case "COD":
            default:
                provider = new CODPayment();
                break;
        }

        OrderPayment payment = new OrderPayment(provider);
        double total = 0;
        List<OrderDetail> updatedOrderDetails = new ArrayList<>();
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetail detail = orderDetails.get(i);
            int quantity = Integer.parseInt(request.getParameter("quantity_" + i));
            detail.setQuantity(quantity);
            total += detail.getPrice() * quantity;
            updatedOrderDetails.add(detail); // Cập nhật danh sách
        }

        try {
            boolean paymentSuccess = payment.processPayment(total);
            if (paymentSuccess) {
                Order order = new Order();
                order.setCustomer(customer);
                order.setAddress(address);
                order.setCreateDate(new java.util.Date());
                order.setOrderDetails(updatedOrderDetails);
                order.setPaymentMethod(paymentMethod); // Gán phương thức thanh toán

                // Lưu order vào cơ sở dữ liệu
                OrderDAO orderDAO = new OrderDAO();
                orderDAO.insertOrder(order);

                // Truyền dữ liệu sang OrderConfirmation.jsp
                request.setAttribute("order", order);
                request.setAttribute("address", address);
                request.getRequestDispatcher("PrintOrder.jsp").forward(request, response);
            } else {
                response.sendRedirect("Payment.jsp?error=paymentFailed");
            }
        } catch (PaymentException e) {
            response.sendRedirect("Payment.jsp?error=paymentFailed");
        }
    }
}