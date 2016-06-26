package com.uber.vendingMachine;

import com.uber.vendingMachine.buttons.Button;
import com.uber.vendingMachine.buttons.ConfirmButton;
import com.uber.vendingMachine.buttons.NumberButton;
import com.uber.vendingMachine.buttons.ResetButton;
import com.uber.vendingMachine.components.*;
import com.uber.vendingMachine.exceptions.NoItemsInShelf;
import com.uber.vendingMachine.exceptions.ShelfNotFound;
import com.uber.vendingMachine.payment.CashPaymentModule;
import com.uber.vendingMachine.payment.PaymentCallback;

import java.util.*;

public class VendingMachine {

    private final ControlPanel controlPanel;
    private final ItemDispatcher itemDispatcher;
    private final OrderManager orderManager;
    private final InventoryManager inventoryManager;
    private final ResetButton resetButton;
    private final ReceiptGenerator receiptGenerator;
    private final Map<Character, Button> codeButtons;
    private final ConfirmButton confirmButton;
    private List<Shelf> shelves;

    public VendingMachine() {

        addShelvesAndUpdateInventory();
        Display display = new Display();
        inventoryManager = new InventoryManager(shelves);
        orderManager = new OrderManager(new CashPaymentModule(), inventoryManager, new PaymentCallback() {
            @Override
            public void successFulPaymentForItem(double itemPrice, String shelfId) {
                System.out.println("Payment successfull");
                dispatchItemAndPrintReceipt(itemPrice, shelfId);
            }

            @Override
            public void unsuccessFulPaymentForItemAtShelf(String shelfId) {
                System.out.println("Payment unsuccessfull");
                reset();
            }
        });

        resetButton = new ResetButton(display);
        codeButtons = new HashMap<Character, Button>();
        List<Button> buttons = new ArrayList<Button>();
        for (int i=0;i<10;i++) {
            NumberButton numberButton = new NumberButton(display, (char) (i + '0'));
            buttons.add(numberButton);
            codeButtons.put((char) (i + '0'), numberButton);
        }

        confirmButton = new ConfirmButton(display, orderManager);
        buttons.add(confirmButton);
        buttons.add(resetButton);

        controlPanel = new ControlPanel(new Keypad(buttons), display);
        itemDispatcher = new ItemDispatcher(inventoryManager);
        receiptGenerator = new ReceiptGenerator();
    }

    private void dispatchItemAndPrintReceipt(double itemPrice, String shelfId) {
        try {
            Item item = itemDispatcher.dispatchItemAtShelf(shelfId);
            Receipt receipt = receiptGenerator.generateReceipt(item);
            System.out.println("Item: " + item + ", Receipt: " + receipt);
        } catch (ShelfNotFound shelfNotFound) {
            reset();
        } catch (NoItemsInShelf noItemsInShelf) {
            reset();
        }

    }

    private void reset() {
        resetButton.execute();
    }

    private void addShelvesAndUpdateInventory() {
        Shelf shelf1 = new Shelf("1");
        Shelf shelf2 = new Shelf("2");

        shelf1.addItem(new Item("Lays", 20, "Chips"));
        shelf1.addItem(new Item("Lays", 20, "Chips"));
        shelf1.addItem(new Item("Lays", 20, "Chips"));

        shelf2.addItem(new Item("Pepsi", 30, "Soft Drink"));
        shelf2.addItem(new Item("Pepsi", 30, "Soft Drink"));
        shelf2.addItem(new Item("Pepsi", 30, "Soft Drink"));
        this.shelves = Arrays.asList(shelf1, shelf2);
    }

    public void inputShelf(String shelfId) {
        for (int i = 0;i < shelfId.length(); i++) {
            char c = shelfId.charAt(i);
            codeButtons.get(c).execute();
        }
        confirmButton.execute();
    }
}
