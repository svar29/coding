package com.uber.vendingMachine.components;

import com.uber.vendingMachine.buttons.Button;

import java.util.List;

public class Keypad {

    private List<Button> buttons;

    public Keypad(List<Button> buttons) {
        this.buttons = buttons;
    }
}
