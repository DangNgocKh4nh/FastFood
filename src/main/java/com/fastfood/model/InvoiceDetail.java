package com.fastfood.model;

public class InvoiceDetail {
    private int idInvoiceDetail;
    private int quantity;
    private double price;
    private Ingredient ingredient;

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

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}