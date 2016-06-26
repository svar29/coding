package com.uber.vendingMachine.buttons;

import com.uber.vendingMachine.components.Display;

public class ResetButton implements Button {

    private final Display display;

    public ResetButton(Display display) {
        this.display = display;
    }

    @Override
    public void execute() {
        display.clear();
    }
}
