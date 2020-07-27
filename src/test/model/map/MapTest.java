package model.map;

import model.map.Map;
import model.map.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.item.Inventory;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    private Map map;
    Room room1;
    Room room2;

    @BeforeEach
    void runBefore() {
        map = new Map();
        room1 = new Room("1", "", 0, 0, 0, 0, new Inventory());
        room2 = new Room("2", "", 0, 0, 0, 0, new Inventory());
        map.addRoom(room1);
        map.addRoom(room2);
    }

    @Test
    void testAddRoom() {
        assertEquals(2, map.getRooms().size());
        map.addRoom(new Room("", "", 0, 0, 0, 0, new Inventory()));
        assertEquals(3, map.getRooms().size());
    }

    void testGetCurrentRoom() {
        assertEquals(room1, map.getCurrentRoom());
        map.setCurrentIndex(1);
        assertEquals(1, map.getCurrentIndex());
        assertEquals(room2, map.getCurrentRoom());
    }

    @Test
    void testStringToInt() {
        assertEquals(Map.NORTH, Map.stringToInt("NORTH"));
        assertEquals(Map.NORTH, Map.stringToInt("north"));
        assertEquals(Map.SOUTH, Map.stringToInt("SOUTH"));
        assertEquals(Map.SOUTH, Map.stringToInt("south"));
        assertEquals(Map.EAST, Map.stringToInt("EAST"));
        assertEquals(Map.EAST, Map.stringToInt("east"));
        assertEquals(Map.WEST, Map.stringToInt("WEST"));
        assertEquals(Map.WEST, Map.stringToInt("west"));
    }

    @Test
    void testIntToString() {
        assertEquals("north", Map.intToString(Map.NORTH));
        assertEquals("south", Map .intToString(Map.SOUTH));
        assertEquals("east", Map.intToString(Map.EAST));
        assertEquals("west", Map .intToString(Map.WEST));
    }
}
