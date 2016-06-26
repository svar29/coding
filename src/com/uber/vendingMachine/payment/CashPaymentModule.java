package com.uber.vendingMachine.payment;

public class CashPaymentModule implements PaymentModule {

    @Override
    public boolean makeTransaction(double amount) {
        return true;
    }
}
