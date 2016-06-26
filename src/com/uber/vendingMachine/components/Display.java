package com.uber.vendingMachine.components;

public class Display {

    private StringBuffer message;

    public Display() {
        message = new StringBuffer("");
    }

    public void clear() {
        message = new StringBuffer("");
        updateDisplay(message.toString());
    }

    private void updateDisplay(String message) {
        System.out.println(message);
    }

    public void appendCharacter(char value) {
        message.append(value);
        updateDisplay(message.toString());
    }

    public StringBuffer getMessage() {
        return message;
    }
}
