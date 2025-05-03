package com.fastfood.controller;

import com.fastfood.dao.IngredientDAO;
import com.fastfood.model.Ingredient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/ImportServlet")
public class ImportServlet extends HttpServlet {

    private IngredientDAO ingredientDAO = new IngredientDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String searchName = request.getParameter("searchName");

        if ("search".equals(action) && searchName != null) {
            List<Ingredient> searchResults = ingredientDAO.getIngredientByName(searchName);
            request.setAttribute("searchResults", searchResults);
        }

        HttpSession session = request.getSession();

        Map<Integer, Ingredient> selectedIngredients = (Map<Integer, Ingredient>) session.getAttribute("selectedIngredients");
        Map<Integer, Integer> selectedQuantities = (Map<Integer, Integer>) session.getAttribute("selectedQuantities");

        if (selectedIngredients == null) selectedIngredients = new HashMap<>();
        if (selectedQuantities == null) selectedQuantities = new HashMap<>();

        request.setAttribute("selectedIngredients", selectedIngredients);
        request.setAttribute("selectedQuantities", selectedQuantities);

        double totalPrice = 0;
        for (Map.Entry<Integer, Integer> entry : selectedQuantities.entrySet()) {
            int id = entry.getKey();
            int qty = entry.getValue();
            Ingredient ing = selectedIngredients.get(id);
            if (ing != null) {
                totalPrice += ing.getPrice() * qty;
            }
        }
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("Import.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        Map<Integer, Ingredient> selectedIngredients = (Map<Integer, Ingredient>) session.getAttribute("selectedIngredients");
        Map<Integer, Integer> selectedQuantities = (Map<Integer, Integer>) session.getAttribute("selectedQuantities");

        if (selectedIngredients == null) selectedIngredients = new HashMap<>();
        if (selectedQuantities == null) selectedQuantities = new HashMap<>();

        if ("add".equals(action)) {
            int id = Integer.parseInt(request.getParameter("idIngredient"));
            Ingredient ing = ingredientDAO.getIngredientById(id);
            if (ing != null) {
                selectedIngredients.putIfAbsent(id, ing);
                selectedQuantities.put(id, selectedQuantities.getOrDefault(id, 0) + 1);
            }
        } else if ("remove".equals(action)) {
            int id = Integer.parseInt(request.getParameter("idIngredient"));
            selectedIngredients.remove(id);
            selectedQuantities.remove(id);
        } else if ("confirm".equals(action)) {
            System.out.println("Confirmed import:");
            for (Map.Entry<Integer, Integer> entry : selectedQuantities.entrySet()) {
                int id = entry.getKey();
                int qty = entry.getValue();
                Ingredient ing = selectedIngredients.get(id);
                if (ing != null) {
                    System.out.println(ing.getName() + " x " + qty);
                }
            }
            selectedIngredients.clear();
            selectedQuantities.clear();
        }

        session.setAttribute("selectedIngredients", selectedIngredients);
        session.setAttribute("selectedQuantities", selectedQuantities);

        response.sendRedirect("ImportServlet");
    }
}
