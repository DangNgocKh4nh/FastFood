package com.fastfood.controller;

import com.fastfood.dao.IngredientDAO;
import com.fastfood.model.Ingredient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ImportServlet")
public class ImportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String supplierIdStr = request.getParameter("supplierId");
        int supplierId = Integer.parseInt(supplierIdStr);
        List<Ingredient> ingredientList = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            IngredientDAO dao = new IngredientDAO();
            ingredientList = dao.getIngredientByName(keyword, supplierId);
        }

        request.setAttribute("ingredientList", ingredientList);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("Import.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String supplierId = request.getParameter("supplierId");
        String keyword = request.getParameter("keyword");
        HttpSession session = request.getSession();
        List<Ingredient> selectedIngredients = (List<Ingredient>) session.getAttribute("selectedIngredients");
        List<Integer> quantities = (List<Integer>) session.getAttribute("quantities");

        if (selectedIngredients == null) {
            selectedIngredients = new ArrayList<>();
            quantities = new ArrayList<>();
            session.setAttribute("selectedIngredients", selectedIngredients);
            session.setAttribute("quantities", quantities);
        }

        IngredientDAO dao = new IngredientDAO();
        if ("add".equals(action)) {
            int ingredientId = Integer.parseInt(request.getParameter("ingredientId"));
            Ingredient ingredient = dao.getIngredientById(ingredientId);
            if (ingredient != null && !selectedIngredients.contains(ingredient)) {
                selectedIngredients.add(ingredient);
                quantities.add(1); // Mặc định số lượng là 1
            }
        } else if ("update".equals(action)) {
            int index = Integer.parseInt(request.getParameter("index"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity > 0 && index >= 0 && index < quantities.size()) {
                quantities.set(index, quantity);

                // Tính tổng tiền
                double total = 0.0;
                for (int i = 0; i < selectedIngredients.size(); i++) {
                    total += selectedIngredients.get(i).getPrice() * quantities.get(i);
                }

                // Kiểm tra nếu là yêu cầu AJAX
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    response.setContentType("application/json");
                    JSONObject json = new JSONObject();
                    json.put("success", true);
                    json.put("index", index);
                    json.put("quantity", quantity);
                    json.put("total", total);
                    response.getWriter().write(json.toString());
                    return; // Kết thúc xử lý, không chuyển hướng
                }
            } else {
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    response.setContentType("application/json");
                    JSONObject json = new JSONObject();
                    json.put("success", false);
                    response.getWriter().write(json.toString());
                    return;
                }
            }
        } else if ("remove".equals(action)) {
            int index = Integer.parseInt(request.getParameter("index"));
            selectedIngredients.remove(index);
            quantities.remove(index);
        } else if ("confirm".equals(action)) {
            // Chuyển tiếp đến PrintInvoice.jsp
            request.setAttribute("supplierId", supplierId);
            request.setAttribute("keyword", keyword);
            request.getRequestDispatcher("PrintInvoice.jsp").forward(request, response);
            return; // Kết thúc xử lý, không chuyển hướng
        }

        // Chuyển hướng cho các hành động không phải AJAX hoặc confirm
        String redirectUrl = "ImportServlet?supplierId=" + supplierId;
        if (keyword != null && !keyword.trim().isEmpty()) {
            redirectUrl += "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
        }
        response.sendRedirect(redirectUrl);
    }
}