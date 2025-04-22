package com.airline.reservation.util;

public class PayPalPayment implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        return "Paid $" + amount + " using PayPal.";
    }
}
