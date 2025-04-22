package com.airline.reservation.util;

public class PaymentContext {

    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public String processPayment(double amount) {
        return strategy.pay(amount);
    }
}
