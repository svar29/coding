package com.uber.vendingMachine.components;

import com.uber.vendingMachine.Item;

public class ReceiptGenerator {
    public Receipt generateReceipt(Item item) {
        return new Receipt(item);
    }
}
