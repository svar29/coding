package com.uber.vendingMachine.payment;

public class CardPaymentModule implements PaymentModule {
    @Override
    public boolean makeTransaction(double amount) {
        return false;
    }
}
