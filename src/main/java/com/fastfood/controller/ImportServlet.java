package com.fastfood.controller;

import com.fastfood.dao.IngredientDAO;
import com.fastfood.model.Ingredient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
            if (quantity > 0) {
                quantities.set(index, quantity);
            }
        } else if ("remove".equals(action)) {
            int index = Integer.parseInt(request.getParameter("index"));
            selectedIngredients.remove(index);
            quantities.remove(index);
        }

        // Chuyển hướng với supplierId và keyword
        String redirectUrl = "ImportServlet?supplierId=" + supplierId;
        if (keyword != null && !keyword.trim().isEmpty()) {
            redirectUrl += "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
        }
        response.sendRedirect(redirectUrl);
    }
}