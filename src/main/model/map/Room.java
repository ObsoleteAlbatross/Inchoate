package model.map;


import model.item.Inventory;
import model.item.Item;

import java.io.Serializable;

// Room class, stores name, description, destination rooms in cardinal direction.
// Can have a Riddle (riddle room), a required item for entry (locked room), and can store items for player to pick up

public class Room implements Serializable {
    boolean isVisited;
    private final String name;
    private final String description;
    private final int north;
    private final int west;
    private final int south;
    private final int east;
    private final Inventory inventory;
    private final Item required;
    private Riddle riddle;

    // EFFECTS: A locked room, required a certain item to be present for passage
    public Room(String name, String description, int north, int west, int south, int east, Inventory inventory,
                Item required) {
        this.name = name;
        this.description = description;
        this.north = north;
        this.west = west;
        this.south = south;
        this.east = east;
        this.isVisited = false;
        this.inventory = inventory;
        this.required = required;
    }

    // EFFECTS: Riddle room, answering this riddle, a player gets a hidden item which allows access to locked rooms
    public Room(String name, String description, int north, int west, int south, int east, Inventory inventory,
                Riddle riddle) {
        this.name = name;
        this.description = description;
        this.north = north;
        this.west = west;
        this.south = south;
        this.east = east;
        this.isVisited = false;
        this.inventory = inventory;
        this.required = new Item("none", "none");
        this.riddle = riddle;
    }

    // EFFECTS: No frills room :) A basic room that can have some items (or treasure if you really want that)
    public Room(String name, String description, int north, int west, int south, int east, Inventory inventory) {
        this.name = name;
        this.description = description;
        this.north = north;
        this.west = west;
        this.south = south;
        this.east = east;
        this.isVisited = false;
        this.inventory = inventory;
        this.required = new Item("none", "none");
    }

    // EFFECTS: Return the destination in the given direction
    public int getDestinationFromDirection(Direction direction) {
        return direction == Direction.NORTH ? north : direction == Direction.WEST ? west :
                direction == Direction.SOUTH ? south : east;
    }

    // EFFECTS: Return if the given direction leads to a valid (non-empty) room
    public boolean isValidDirection(Direction direction) {
        return getDestinationFromDirection(direction) != -1;
    }

    // EFFECTS: Return the room name
    public String getName() {
        return name;
    }

    // EFFECTS: Return the room description
    public String getDescription() {
        return description;
    }

    // EFFECTS: Return whether the room has already been visited
    public boolean isVisited() {
        return isVisited;
    }

    // EFFECTS: Set whether room has been visited or not to given bool
    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    // EFFECTS: Get the index of the room to north
    public int getNorth() {
        return north;
    }

    // EFFECTS: Get the index of the room to west
    public int getWest() {
        return west;
    }

    // EFFECTS: Get the index of the room to south
    public int getSouth() {
        return south;
    }

    // EFFECTS: Get the index of the room to east
    public int getEast() {
        return east;
    }

    // EFFECTS: Return the inventory of the room (which houses items)
    public Inventory getInventory() {
        return inventory;
    }

    // EFFECTS: Return the item that is required to enter the room (locked room)
    public Item getRequired() {
        return required;
    }

    // EFFECTS: Get the riddle of the room (can be null since not all rooms have riddles)
    public Riddle getRiddle() {
        return riddle;
    }
}
