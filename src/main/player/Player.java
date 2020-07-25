package player;

import items.Inventory;
import map.Map;

import java.util.ArrayList;

public class Player {
    private Map map;
    private Inventory inventory;

    public Player() {
        this.map = new Map(new ArrayList<>());
        this.inventory = new Inventory();
    }

    // MODIFIES: this
    // EFFECTS: Try to move player in given direction, throw exception accordingly if can't
    public void move(int direction) throws IllegalArgumentException {
        // Check if direction is valid
        if (map.getCurrentRoom().getDestinationFromDirection(direction) == -1) {
            throw new IllegalArgumentException("You can't go there! There's nothing there!");
        }
        map.setCurrentIndex(map.getCurrentRoom().getDestinationFromDirection(direction));
    }

    // Getters and setters
    public Map getMap() {
        return map;
    }

    public Inventory getInventory() {
        return inventory;
    }
}

