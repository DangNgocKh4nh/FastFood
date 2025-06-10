package com.fastfood.payment;

public class InternetBankingPayment implements PaymentProvider {
    @Override
    public boolean pay(double amount) {
        return true; // Giả định thành công
    }

    @Override
    public String getProviderName() {
        return "InternetBanking";
    }
}