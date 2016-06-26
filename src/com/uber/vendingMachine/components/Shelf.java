package com.uber.vendingMachine.components;

import com.uber.vendingMachine.Item;

import java.util.LinkedList;
import java.util.Queue;

public class Shelf {

    private String id;

    private Queue<Item> items;

    public Shelf(String id) {
        this.id = id;
        items = new LinkedList<Item>();
    }

    public String getId() {
        return id;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item getItemAtHead() {
        if (items.size() > 0)
            return items.peek();
        return null;
    }

    public Item removeItemAtHead() {
        if (items.size() > 0)
            return items.poll();
        return null;
    }
}
