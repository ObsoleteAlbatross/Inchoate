package model.item;

import java.util.LinkedList;
import java.util.List;

// Inventory class, holds a list of items (a container for items)
public class Inventory {
    private final List<Item> items;

    public Inventory() {
        items = new LinkedList<>();
    }

    // EFFECTS: Return an item that has matching to given name
    public Item getItem(String name) throws IllegalArgumentException {
        for (Item item : items) {
            if (item.getName().toLowerCase().equals(name.toLowerCase())) {
                return item;
            }
        }
        throw new IllegalArgumentException("Can't get item. `" + name + "` isn't here");
    }

    // MODIFIES: this
    // EFFECTS: Add given item to inventory items list
    public void addItem(Item item) {
        items.add(item);
    }

    // Overloaded method of above for multiple items
    // EFFECTS: Add multiple given items to inventory items list
    public void addItem(List<Item> items) {
        this.items.addAll(items);
    }

    // MODIFIES: this
    // EFFECTS: Remove given item name from inventory
    public void removeItem(String name) throws IllegalArgumentException {
        for (Item item : items) {
            if (item.getName().toLowerCase().equals(name.toLowerCase())) {
                items.remove(item);
                return;
            }
        }
        throw new IllegalArgumentException("Can't remove Item. `" + name + "` isn't here");
    }

    // EFFECTS: Return the list of items in inventory
    public List<Item> getItems() {
        return items;
    }
}
