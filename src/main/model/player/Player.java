package model.player;

import model.item.Inventory;
import model.item.Item;
import model.map.Direction;
import model.map.Map;

import java.util.ArrayList;

// Player class, has the map, an inventory, and hidden quest inventory for progression
public class Player {
    private final Map map;
    private final Inventory inventory;
    private final Inventory quest;

    // EFFECTS: Make a new player with empty map, inv, quest
    public Player() {
        this.map = new Map(new ArrayList<>());
        this.inventory = new Inventory();
        this.quest = new Inventory();
        // This is a default item that all rooms will check for
        this.quest.addItem(new Item("none", "none"));
    }

    // MODIFIES: this
    // EFFECTS: Try to move player in given direction, throw exception accordingly if can't
    public void move(Direction direction) throws IllegalArgumentException {
        int destination = map.getCurrentRoom().getDestinationFromDirection(direction);
        // Check if direction is valid
        if (map.getCurrentRoom().getDestinationFromDirection(direction) == -1) {
            throw new IllegalArgumentException("You can't go there! There's nothing there!");
        }
        if (!hasItemByName(map.getRoomByIndex(destination).getRequired())) {
            throw new IllegalArgumentException("That area is locked! You require `"
                    + map.getRoomByIndex(destination).getRequired().getName() + "`");
        }
        map.setCurrentIndex(destination);
    }

    // EFFECTS: Return if player has the given item in either quest or inventory
    public boolean hasItemByName(Item item) {
        return hasItemLoop(item, quest) || hasItemLoop(item, inventory);
    }

    // EFFECTS: Return if a given item is in a given inventory
    private boolean hasItemLoop(Item item, Inventory inventory) {
        for (Item i : inventory.getItems()) {
            if (i.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Return the map
    public Map getMap() {
        return map;
    }

    // EFFECTS: Return the player inventory
    public Inventory getInventory() {
        return inventory;
    }

    // EFFECTS: Return the hidden quest inventory
    public Inventory getQuest() {
        return quest;
    }
}

