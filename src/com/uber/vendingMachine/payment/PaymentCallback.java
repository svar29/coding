package com.uber.vendingMachine.payment;

public interface PaymentCallback {

    public void successFulPaymentForItem(double itemPrice, String shelfId);

    public void unsuccessFulPaymentForItemAtShelf(String shelfId);
}
