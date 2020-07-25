package map;


import items.Inventory;
import items.Item;

public class Room {
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

    // EFFECTS: Locked room
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

    // EFFECTS: Riddle room
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

    // EFFECTS: No frills room :)
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

    // EFFECTS: return the destination in the given direction
    public int getDestinationFromDirection(int direction) {
        return direction == Direction.NORTH ? north : direction == Direction.WEST ? west :
                direction == Direction.SOUTH ? south : east;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public int getNorth() {
        return north;
    }

    public int getWest() {
        return west;
    }

    public int getSouth() {
        return south;
    }

    public int getEast() {
        return east;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Item getRequired() {
        return required;
    }

    public Riddle getRiddle() {
        return riddle;
    }
}
