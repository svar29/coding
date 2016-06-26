package com.uber.vendingMachine.exceptions;

public class NoItemsInShelf extends Exception {

    public NoItemsInShelf(String message) {
        super(message);
    }
}
