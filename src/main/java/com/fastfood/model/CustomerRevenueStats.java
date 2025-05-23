package com.fastfood.model;

public class CustomerRevenueStats extends Customer {
    private double revenue;

    public CustomerRevenueStats() {
        super();
        this.revenue = 0.0;
    }

    public CustomerRevenueStats(int idCustomer, String name, String phone, String email, double revenue) {
        super(idCustomer, name, phone, email);
        this.revenue = revenue;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "CustomerRevenueStats{" +
                "idCustomer=" + getIdCustomer() +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhoneNumber() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", revenue=" + revenue +
                '}';
    }
}