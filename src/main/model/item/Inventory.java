package model.item;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// Inventory class, holds a list of items (a container for items)
public class Inventory implements Serializable {
    private Map<String, Item> items;

    public Inventory() {
        items = new HashMap<>();
    }

    // EFFECTS: Return an item that has matching to given name
    public Item getItem(String name) throws IllegalArgumentException {
        Item item = items.get(name);
        if (item == null) {
            throw new IllegalArgumentException("Can't get item. `" + name + "` isn't here");
        }
        return item;
    }

    // MODIFIES: this
    // EFFECTS: Add given item to inventory items list
    public void addItem(Item item) {
        items.put(item.getName().toLowerCase(), item);
    }

    // Overloaded method of above for multiple items
    // EFFECTS: Add multiple given items to inventory items list
    public void addItem(List<Item> items) {
        for (Item item : items) {
            addItem(item);
        }
    }

    // MODIFIES: this
    // EFFECTS: Remove given item name from inventory
    public void removeItem(String name) throws IllegalArgumentException {
        if (null == items.remove(name.toLowerCase())) {
            throw new IllegalArgumentException("Can't remove Item. `" + name + "` isn't here");
        }
    }

    // EFFECTS: Return the list of items in inventory
    public Map<String, Item> getItems() {
        return items;
    }
}
