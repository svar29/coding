package com.uber.vendingMachine.components;

import com.uber.vendingMachine.Item;
import com.uber.vendingMachine.exceptions.NoItemsInShelf;
import com.uber.vendingMachine.exceptions.ShelfNotFound;

public class ItemDispatcher {

    private final InventoryManager inventoryManager;

    public ItemDispatcher(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public Item dispatchItemAtShelf(String shelfId) throws ShelfNotFound, NoItemsInShelf {
        Item item = inventoryManager.getItemAndRemoveItemAtShelf(shelfId);
        System.out.println("Item dispatched: " + item);
        return item;
    }
}
