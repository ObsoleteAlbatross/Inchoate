package player;

import items.Inventory;
import items.Item;
import map.Map;

import java.util.ArrayList;

public class Player {
    private final Map map;
    private final Inventory inventory;
    private final Inventory quest;

    // EFFECTS: Make a new player
    public Player() {
        this.map = new Map(new ArrayList<>());
        this.inventory = new Inventory();
        this.quest = new Inventory();
        this.quest.addItem(new Item("none", "none"));
    }

    // MODIFIES: this
    // EFFECTS: Try to move player in given direction, throw exception accordingly if can't
    public void move(int direction) throws IllegalArgumentException {
        int destination = map.getCurrentRoom().getDestinationFromDirection(direction);
        // Check if direction is valid
        if (map.getCurrentRoom().getDestinationFromDirection(direction) == -1) {
            throw new IllegalArgumentException("You can't go there! There's nothing there!");
        }
        if (!hasItemByName(map.getRoomByIndex(destination).getRequired())) {
            throw new IllegalArgumentException("That area is locked! You require "
                    + map.getRoomByIndex(destination).getRequired().getName());
        }
        map.setCurrentIndex(destination);
    }

    private boolean hasItemByName(Item item) {
        for (Item i : quest.getItems()) {
            if (i.getName().equals(item.getName())) {
                return true;
            }
        }
        for (Item i : inventory.getItems()) {
            if (i.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    // Getters and setters
    public Map getMap() {
        return map;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Inventory getQuest() {
        return quest;
    }
}

