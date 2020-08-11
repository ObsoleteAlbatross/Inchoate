package model.player;

import model.item.Inventory;
import model.item.Item;
import model.map.Direction;
import model.map.Map;

import javax.management.DescriptorRead;
import java.io.Serializable;
import java.util.ArrayList;

// Player class, has the map, an inventory, and hidden quest inventory for progression
public class Player implements Serializable {
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
        // Check if direction is valid
        if (!map.getCurrentRoom().isValidDirection(direction)) {
            throw new IllegalArgumentException("You can't go there! There's nothing there!");
        }
        if (!hasItem(map.getRoomInDirection(direction).getRequired())) {
            throw new IllegalArgumentException("That area is locked! You require `"
                    + map.getRoomInDirection(direction).getRequired().getName() + "`");
        }
        map.move(direction);
    }

    // EFFECTS: Return if player has the given item in either quest or inventory
    public boolean hasItem(Item item) {
        return quest.hasItem(item) || inventory.hasItem(item);
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

