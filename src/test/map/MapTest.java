package map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Inventory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    private Map map;

    @BeforeEach
    void runBefore() {
        map = new Map();
    }

    @Test
    void testAddRoom() {
        assertEquals(0, map.getRooms().size());
        map.addRoom(new Room("", "", 0, 0, 0, 0, new ArrayList<>()));
        assertEquals(1, map.getRooms().size());
    }

    void testGetCurrentRoom() {
        Room room1 = new Room("1", "", 0, 0, 0, 0, new ArrayList<>());
        map.addRoom(room1);
        Room room2 = new Room("2", "", 0, 0, 0, 0, new ArrayList<>());
        map.addRoom(room2);
        assertEquals(room1, map.getCurrentRoom());
        map.setCurrentIndex(1);
        assertEquals(1, map.getCurrentIndex());
        assertEquals(room2, map.getCurrentRoom());
    }
}
