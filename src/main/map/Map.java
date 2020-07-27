package map;

import java.util.ArrayList;
import java.util.List;

// Map class, a container for all the rooms in the game
public class Map {

    public static final int NORTH = 0;
    public static final int WEST = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
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

    // REQUIRES: Given string is one of: north, west, south, east
    //           This is enforced by the UI/GUI since the commands will throw the appropriate exceptions
    //           Hence this will only be called if the command is of valid type, which fulfills this REQUIRES
    // EFFECTS: Return the direction (NWSE) from the given string
    public static int stringToInt(String str) {
        String s = str.toLowerCase();
        return s.equals("north") ? NORTH : s.equals("west") ? WEST :
                s.equals("south") ? SOUTH : EAST;
    }

    // REQUIRES: Given direction is one of Map.NORTH, Map.WEST, Map.SOUTH, Map.EAST
    //           Enforcement is similar to stringToInt(String str)
    //           This method should only be called using the above static ints
    // EFFECTS: Return the string representation of a given direction
    public static String intToString(int dir) {
        return dir == NORTH ? "north" : dir == WEST ? "west" :
                dir == SOUTH ? "south" : "east";
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
