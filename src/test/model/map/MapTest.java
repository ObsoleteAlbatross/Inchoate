package model.map;

import model.item.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    Room room1;
    Room room2;
    private Map map;

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

    @Test
    void testGetCurrentRoom() {
        assertEquals(room1, map.getCurrentRoom());
        map.setCurrentIndex(1);
        assertEquals(1, map.getCurrentIndex());
        assertEquals(room2, map.getCurrentRoom());
    }
}
