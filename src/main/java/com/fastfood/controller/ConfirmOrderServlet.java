package com.fastfood.controller;

import com.fastfood.dao.OrderDAO;
import com.fastfood.dao.OrderDetailDAO;
import com.fastfood.model.Customer;
import com.fastfood.model.Order;
import com.fastfood.model.OrderDetail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/ConfirmOrderServlet")
public class ConfirmOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy session hiện tại
        HttpSession session = request.getSession();

        // Lấy danh sách chi tiết đơn hàng từ session
        List<OrderDetail> orderDetails = (List<OrderDetail>) session.getAttribute("orderDetails");
        // Lấy thông tin khách hàng từ session
        Customer customer = (Customer) session.getAttribute("customer");

        // Kiểm tra nếu không có chi tiết đơn hàng hoặc khách hàng
        if (orderDetails == null || customer == null) {
            response.sendRedirect("Order.jsp?error=missingData");
            return;
        }

        // Lấy địa chỉ từ request
        String address = request.getParameter("address");

        // Kiểm tra xem địa chỉ có được nhập không
        if (address == null || address.trim().isEmpty()) {
            response.sendRedirect("Payment.jsp?error=missingAddress");
            return;
        }

        // Tính tổng tiền đơn hàng và cập nhật lại số lượng cho các chi tiết đơn hàng
        double total = 0;
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetail detail = orderDetails.get(i);

            // Lấy số lượng từ request
            String quantityParam = request.getParameter("quantity_" + i);
            if (quantityParam != null && !quantityParam.trim().isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityParam);
                    detail.setQuantity(quantity);  // Cập nhật số lượng mới vào detail
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            // Tính tổng tiền cho mỗi chi tiết đơn hàng
            total += detail.getPrice() * detail.getQuantity();
        }

        // Tạo đối tượng Order
        Order order = new Order();
        order.setTotal(total);
        order.setCreateDate(new Date());
        order.setCustomer(customer);
        order.setAddress(address);
        order.setOrderDetails(orderDetails);

        // Tạo đối tượng OrderDAO để lưu đơn hàng vào cơ sở dữ liệu
        OrderDAO orderDAO = new OrderDAO();
        int orderId = orderDAO.insertOrder(order);  // Lưu đơn hàng và lấy ID của đơn hàng mới

        // Kiểm tra nếu lưu đơn hàng thành công
        if (orderId != -1) {
            // Sau khi lưu đơn hàng, lưu các chi tiết đơn hàng
            OrderDetailDAO detailDAO = new OrderDetailDAO();
            for (int i = 0; i < orderDetails.size(); i++) {
                OrderDetail detail = orderDetails.get(i);
                detail.setOrderId(orderId); // Gán orderId cho từng chi tiết đơn hàng
                // Lưu chi tiết đơn hàng vào cơ sở dữ liệu
                detailDAO.insertOrderDetail(detail);
            }

            // Xóa giỏ hàng (clear orderDetails trong session)
            session.removeAttribute("orderDetails");

            // Chuyển tiếp đến trang PrintOrder.jsp để hiển thị thông tin đơn hàng
            request.setAttribute("order", order);
            request.setAttribute("orderId", orderId);
            request.setAttribute("address", address);
            request.getRequestDispatcher("PrintOrder.jsp").forward(request, response);
        } else {
            // Nếu lưu đơn hàng không thành công, chuyển về trang Payment.jsp với thông báo lỗi
            response.sendRedirect("Payment.jsp?error=saveFail");
        }
    }
}
