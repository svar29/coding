package com.uber.vendingMachine.components;

import com.uber.vendingMachine.Item;

public class Receipt {

    private final String value;

    public Receipt(Item item) {
        this.value = "Payment done for: " + item;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "value='" + value + '\'' +
                '}';
    }
}
