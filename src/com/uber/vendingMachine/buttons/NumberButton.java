package com.uber.vendingMachine.buttons;

import com.uber.vendingMachine.components.Display;

public class NumberButton implements Button {

    protected final Display display;
    private final char value;

    public NumberButton(Display display, char value) {
        this.display = display;
        this.value = value;
    }

    @Override
    public void execute() {
        display.appendCharacter(value);
    }
}
