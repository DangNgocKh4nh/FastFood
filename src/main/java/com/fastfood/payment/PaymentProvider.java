// PaymentProvider.java
package com.fastfood.payment;

public interface PaymentProvider {
    boolean pay(double amount) throws PaymentException;
    String getProviderName();
}