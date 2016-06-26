package com.uber.vendingMachine.payment;

public interface PaymentModule {

    boolean makeTransaction(double amount);
}
