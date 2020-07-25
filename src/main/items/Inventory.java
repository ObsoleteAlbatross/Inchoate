package items;

import items.Item;

import java.util.LinkedList;
import java.util.List;

public class Inventory {
    private List<Item> items;

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
    // EFFECTS: Add given item(s) to inventory items list
    public void addItem(Item item) {
        items.add(item);
    }

    // As above but for multiple items
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

    // Getters and setters
    public List<Item> getItems() {
        return items;
    }
}
