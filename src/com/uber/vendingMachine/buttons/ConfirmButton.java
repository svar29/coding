package com.uber.vendingMachine.buttons;

import com.uber.vendingMachine.components.Display;
import com.uber.vendingMachine.components.OrderManager;

public class ConfirmButton implements Button {

    private final Display display;
    private final OrderManager orderManager;

    public ConfirmButton(Display display, OrderManager orderManager) {
        this.display = display;
        this.orderManager = orderManager;
    }

    @Override
    public void execute() {
        orderManager.manageOrderForItemAtShelf(display.getMessage().toString());
    }
}
