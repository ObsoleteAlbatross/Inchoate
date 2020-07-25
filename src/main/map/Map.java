package map;

import java.util.ArrayList;
import java.util.List;

public class Map {

    public static final int NORTH = 0;
    public static final int WEST = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    private final List<Room> rooms;
    private int currentIndex;

    public Map(List<Room> rooms) {
        this.rooms = rooms;
        currentIndex = 0;
    }

    public Map() {
        this.rooms = new ArrayList<>();
    }

    public static int stringToInt(String str) {
        String s = str.toLowerCase();
        return s.equals("north") ? NORTH : s.equals("west") ? WEST :
                s.equals("south") ? SOUTH : EAST;
    }

    public static String intToString(int dir) {
        return dir == NORTH ? "north" : dir == WEST ? "west" :
                dir == SOUTH ? "south" : "east";
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
