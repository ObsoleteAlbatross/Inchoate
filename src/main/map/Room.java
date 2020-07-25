package map;


import items.Inventory;

public class Room {
    boolean isVisited;
    private String name;
    private String description;
    private int north;
    private int west;
    private int south;
    private int east;
    private Inventory inventory;

    // Effects: New room with all fields.
    public Room(String name, String description, int north, int west, int south, int east, Inventory inventory) {
        this.name = name;
        this.description = description;
        this.north = north;
        this.west = west;
        this.south = south;
        this.east = east;
        this.isVisited = false;
        this.inventory = inventory;
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

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isVisited() {
        return isVisited;
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
}
