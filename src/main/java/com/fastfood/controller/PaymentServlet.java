package com.fastfood.controller;

import com.fastfood.dao.ItemDAO;
import com.fastfood.dao.OrderDetailDAO;
import com.fastfood.model.Item;
import com.fastfood.model.OrderDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<Item> selectedItems = (List<Item>) session.getAttribute("selectedItems");
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ItemDAO itemDAO = new ItemDAO();

        if (selectedItems != null) {
            for (Item item : selectedItems) {
                int itemId = item.getIdItem();
                String qtyParam = request.getParameter("quantity_" + itemId);
                int quantity = (qtyParam != null) ? Integer.parseInt(qtyParam) : 1;

                OrderDetail detail = new OrderDetail();
                detail.setItem(item);
                detail.setQuantity(quantity);
                detail.setPrice(quantity * item.getPrice());
                orderDetails.add(detail);
            }
        }

        System.out.println("Số món nhận được: " + orderDetails.size());
        session.setAttribute("orderDetails", orderDetails);
        response.sendRedirect("Payment.jsp");
    }
}

