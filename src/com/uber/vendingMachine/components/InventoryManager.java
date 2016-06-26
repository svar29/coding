package com.uber.vendingMachine.components;

import com.uber.vendingMachine.Item;
import com.uber.vendingMachine.exceptions.NoItemsInShelf;
import com.uber.vendingMachine.exceptions.ShelfNotFound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {

    private final Map<String, Shelf> shelfMap;

    public InventoryManager(List<Shelf> shelfs) {
        shelfMap = new HashMap<String, Shelf>();
        for (Shelf shelf: shelfs)
            shelfMap.put(shelf.getId(), shelf);
    }

    public Item getItemAndRemoveItemAtShelf(String shelfId) throws ShelfNotFound, NoItemsInShelf {
        if (!shelfMap.containsKey(shelfId))
            throw new ShelfNotFound("Shelf with shelfId " + shelfId + " not found");
        Item item = shelfMap.get(shelfId).getItemAtHead();
        if (item == null)
            throw new NoItemsInShelf("No items present in shelf " + shelfId);
        item = shelfMap.get(shelfId).removeItemAtHead();
        return item;
    }

    public double getItemPriceAtShelf(String shelfId) throws ShelfNotFound, NoItemsInShelf {
        if (!shelfMap.containsKey(shelfId))
            throw new ShelfNotFound("Shelf with shelfId " + shelfId + " not found");
        Item item = shelfMap.get(shelfId).getItemAtHead();
        if (item == null)
            throw new NoItemsInShelf("No items present in shelf " + shelfId);

        return item.getPrice();
    }

}
