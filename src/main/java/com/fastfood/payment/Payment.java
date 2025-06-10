package com.fastfood.payment;

public abstract class Payment {
    protected PaymentProvider paymentProvider;

    public Payment(PaymentProvider paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public abstract boolean processPayment(double amount) throws PaymentException;

    public String getProviderName() {
        return paymentProvider.getProviderName();
    }
}