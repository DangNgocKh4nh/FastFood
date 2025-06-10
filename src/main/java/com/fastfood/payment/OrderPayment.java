package com.fastfood.payment;

public class OrderPayment extends Payment {
    public OrderPayment(PaymentProvider paymentProvider) {
        super(paymentProvider);
    }

    @Override
    public boolean processPayment(double amount) throws PaymentException {
        return paymentProvider.pay(amount);
    }
}