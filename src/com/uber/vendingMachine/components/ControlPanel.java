package com.uber.vendingMachine.components;

public class ControlPanel {

    private Keypad keypad;

    private Display display;

    public ControlPanel(Keypad keypad, Display display) {
        this.keypad = keypad;
        this.display = display;
    }
}
