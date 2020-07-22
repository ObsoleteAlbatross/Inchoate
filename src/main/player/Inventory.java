package player;

import item.Item;

import java.util.LinkedList;
import java.util.List;

public class Inventory {
    private final int maxSize;
    private final List<Item> items;

    public Inventory(int maxSize) {
        items = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean addItem(Item item) {
        if (items.size() >= maxSize) {
            return false;
        }
        items.add(item);
        return true;
    }
}
