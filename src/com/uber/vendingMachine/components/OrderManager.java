package com.uber.vendingMachine.components;

import com.uber.vendingMachine.exceptions.NoItemsInShelf;
import com.uber.vendingMachine.exceptions.ShelfNotFound;
import com.uber.vendingMachine.payment.PaymentCallback;
import com.uber.vendingMachine.payment.PaymentModule;

public class OrderManager {

    private final PaymentModule paymentModule;
    private final InventoryManager inventoryManager;
    private final PaymentCallback paymentCallback;

    public OrderManager(PaymentModule paymentModule, InventoryManager inventoryManager, PaymentCallback paymentCallback) {
        this.paymentModule = paymentModule;
        this.inventoryManager = inventoryManager;
        this.paymentCallback = paymentCallback;
    }

    public void manageOrderForItemAtShelf(String shelfId) {
        try {
            double itemPrice = 0;
            try {
                itemPrice = inventoryManager.getItemPriceAtShelf(shelfId);
            } catch (NoItemsInShelf noItemsInShelf) {
                paymentCallback.unsuccessFulPaymentForItemAtShelf(shelfId);
                return;
            }

            boolean success = paymentModule.makeTransaction(itemPrice);
            if (success) {
                System.out.println("Payment successful for amount: " + itemPrice);
                paymentCallback.successFulPaymentForItem(itemPrice, shelfId);

            }
            else paymentCallback.unsuccessFulPaymentForItemAtShelf(shelfId);

        } catch (ShelfNotFound shelfNotFound) {
            paymentCallback.unsuccessFulPaymentForItemAtShelf(shelfId);
        }
    }
}
