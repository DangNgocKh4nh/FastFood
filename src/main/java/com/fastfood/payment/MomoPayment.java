package com.fastfood.payment;

import java.util.Random;

public class MomoPayment implements PaymentProvider {
    @Override
    public boolean pay(double amount) throws PaymentException {
        return true; // Thành công
    }

    @Override
    public String getProviderName() {
        return "Momo";
    }
}