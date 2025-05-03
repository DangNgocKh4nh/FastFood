package com.fastfood.controller;

import com.fastfood.dao.CustomerDAO;
import com.fastfood.dao.ItemDAO;
import com.fastfood.model.Customer;
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

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    private ItemDAO itemDAO;

    @Override
    public void init() throws ServletException {
        itemDAO = new ItemDAO(); // khởi tạo DAO
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");

        if (keyword != null && !keyword.isEmpty()) {
            List<Item> searchResults = itemDAO.getItemsByName(keyword);
            request.setAttribute("searchResults", searchResults);
        }

        request.getRequestDispatcher("Order.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Item> selectedItems = (List<Item>) session.getAttribute("selectedItems");

        if (selectedItems == null) {
            selectedItems = new ArrayList<>();
        }
        // Lấy keyword để giữ lại sau khi xử lý
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = (String) session.getAttribute("lastKeyword");
        }
        if (keyword != null) {
            session.setAttribute("lastKeyword", keyword);
        }

        // Thêm món
        String addItemId = request.getParameter("addItemId");
        if (addItemId != null) {
            int id = Integer.parseInt(addItemId);
            Item item = itemDAO.getItemById(id);
            if (item != null) {
                selectedItems.add(item);
            }
        }

        // Hủy món
        String removeItemId = request.getParameter("removeItemId");
        if (removeItemId != null) {
            int id = Integer.parseInt(removeItemId);
            selectedItems.removeIf(item -> item.getIdItem() == id);
        }

        // Thanh toán
        String action = request.getParameter("action");
        if ("checkout".equals(action)) {
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (Item item : selectedItems) {
                OrderDetail detail = new OrderDetail();
                detail.setItem(item);
                detail.setQuantity(1); // số lượng mặc định
                detail.setPrice(item.getPrice());
                orderDetails.add(detail);
            }
            session.setAttribute("orderDetails", orderDetails);
            response.sendRedirect("Payment.jsp");
            return;
        }



        session.setAttribute("selectedItems", selectedItems);
        // Chuyển sang GET để tái hiện lại kết quả tìm kiếm
        request.setAttribute("keyword", keyword);
        doGet(request, response);
    }
}
