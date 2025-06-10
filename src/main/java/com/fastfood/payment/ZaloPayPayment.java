package com.fastfood.payment;

public class ZaloPayPayment implements PaymentProvider {
    @Override
    public boolean pay(double amount) {
        return true; // Giả định thành công
    }

    @Override
    public String getProviderName() {
        return "Zalo";
    }
}