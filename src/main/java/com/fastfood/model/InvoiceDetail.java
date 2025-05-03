package com.fastfood.model;

import java.util.List;

public class InvoiceDetail {
    private int idInvoiceDetail;
    private int quantity;
    private double price;
    private List<Ingredient> ingredients;

    public int getIdInvoiceDetail() {
        return idInvoiceDetail;
    }

    public void setIdInvoiceDetail(int idInvoiceDetail) {
        this.idInvoiceDetail = idInvoiceDetail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}