package com.fastfood.controller;

import com.fastfood.dao.ItemDAO;
import com.fastfood.model.Item;
import com.fastfood.dao.OrderDAO;
import com.fastfood.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ItemServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ItemServlet extends HttpServlet {
    private ItemDAO itemDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        itemDAO = new ItemDAO();
        orderDAO = new OrderDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            itemDAO.deleteItem(id);
        } else if ("updateOrder".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String paymentMethod = request.getParameter("paymentMethod");
            String state = request.getParameter("state");
            Order order = new Order();
            order.setIdOrder(orderId);
            order.setPaymentMethod(paymentMethod);
            order.setState(state);
            try {
                orderDAO.updateOrder(order);
                out.write("success");
            } catch (Exception e) {
                out.write("error");
            }
            return; // Kết thúc xử lý AJAX
        }
        List<Item> items = itemDAO.getAllItems();
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("items", items);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/ManagerCMS.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Đảm bảo mã hóa UTF-8
        String action = request.getParameter("action");
        int id = 0; // Giá trị mặc định cho thêm mới
        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            id = Integer.parseInt(request.getParameter("id"));
        }
        String name = request.getParameter("name");
        double price = 0.0;
        if (request.getParameter("price") != null && !request.getParameter("price").isEmpty()) {
            price = Double.parseDouble(request.getParameter("price"));
        }

        Item item = new Item();
        item.setIdItem(id);
        item.setName(name);
        item.setPrice(price);

        // Lấy thông tin item hiện tại từ cơ sở dữ liệu nếu là cập nhật
        String existingImage = null;
        if (id > 0) {
            Item existingItem = itemDAO.getItemById(id);
            if (existingItem != null) {
                existingImage = existingItem.getImage();
            }
        }

        Part filePart = request.getPart("imageFile");
        String fileName = filePart != null ? filePart.getSubmittedFileName() : null;
        if (fileName != null && !fileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);
            item.setImage("/images/" + fileName); // Sử dụng ảnh mới
        } else {
            item.setImage(existingImage); // Giữ nguyên ảnh cũ nếu không upload mới
        }

        if ("add".equals(action)) {
            itemDAO.addItem(item);
        } else if ("update".equals(action)) {
            itemDAO.updateItem(item);
        }

        response.sendRedirect("ItemServlet");
    }
}