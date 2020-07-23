package map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    private Map map;
    Room room1;
    Room room2;

    @BeforeEach
    void runBefore() {
        List<Room> rooms = new ArrayList<>();
        room1 = new Room("1", "", 0, 0, 0, 0);
        rooms.add(room1);
        room2 = new Room("2", "", 0, 0, 0, 0);
        rooms.add(room2);
        map = new Map(rooms);
    }

    @Test
    void testAddRoom() {
        assertEquals(2, map.getRooms().size());
        map.addRoom(new Room("", "", 0, 0, 0, 0));
        assertEquals(3, map.getRooms().size());
    }

    void testGetCurrentRoom() {
        assertEquals(room1, map.getCurrentRoom());
        map.setCurrentIndex(1);
        assertEquals(1, map.getCurrentIndex());
        assertEquals(room2, map.getCurrentRoom());
    }
}
