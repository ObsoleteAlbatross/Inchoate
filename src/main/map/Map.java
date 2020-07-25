package map;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Room> rooms;
    private int currentIndex;

    public Map(List<Room> rooms) {
        this.rooms = rooms;
        currentIndex = 0;
    }

    public Map() {
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    // Getters and setters
    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Room getCurrentRoom() {
        return rooms.get(currentIndex);
    }

    public Room getRoomByIndex(int index) {
        return rooms.get(index);
    }
}
