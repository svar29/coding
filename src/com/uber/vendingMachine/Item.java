package com.uber.vendingMachine;

public class Item {

    private String name;

    private int price;

    private String itemDescription;

    public Item(String name, int price, String itemDescription) {
        this.name = name;
        this.price = price;
        this.itemDescription = itemDescription;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", itemDescription='" + itemDescription + '\'' +
                '}';
    }
}