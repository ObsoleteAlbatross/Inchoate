package model.map;

import model.map.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Map class, a container for all the rooms in the game
public class Map implements Serializable  {

    private final List<Room> rooms;
    private int currentIndex;

    // EFFECTS: Construct a map with given list of rooms
    //          Set currentIndex (room) to 0
    public Map(List<Room> rooms) {
        this.rooms = rooms;
        currentIndex = 0;
    }

    // EFFECTS: Construct a map with no starting rooms (have to manually add later)
    public Map() {
        this.rooms = new ArrayList<>();
        currentIndex = 0;
    }

    // MODIFIES: this
    // EFFECTS: Try to set current room to the given direction room
    public void move(Direction direction) {
        currentIndex = getCurrentRoom().getDestinationFromDirection(direction);
    }

    // EFFECTS: Return the room in the given direction from the current room
    public Room getRoomInDirection(Direction direction) {
        return getRoomByIndex(getCurrentRoom().getDestinationFromDirection(direction));
    }

    // MODIFIES: this
    // EFFECTS: Add a room to the list of rooms
    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    // EFFECTS: Return currentIndex
    public int getCurrentIndex() {
        return currentIndex;
    }

    // EFFECTS: Set currentIndex to the given int
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    // EFFECTS: Return list of rooms
    public List<Room> getRooms() {
        return rooms;
    }

    // EFFECTS: Return the current room (the room in rooms list of index currentIndex)
    public Room getCurrentRoom() {
        return rooms.get(currentIndex);
    }

    // EFFECTS: Return room of the given index
    public Room getRoomByIndex(int index) {
        return rooms.get(index);
    }
}
