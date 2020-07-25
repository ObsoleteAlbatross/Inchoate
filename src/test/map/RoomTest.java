package map;

import items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import items.Inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTest {
    private Room room;

    @BeforeEach
    void runBefore() {
        room = new Room("name", "desc", -1, 0, 1, 2, new Inventory());
    }
    @Test
    void testGetDestinationFromDirection() {
        assertEquals(room.getNorth(), room.getDestinationFromDirection(Map.NORTH));
        assertEquals(room.getSouth(), room.getDestinationFromDirection(Map.SOUTH));
        assertEquals(room.getWest(), room.getDestinationFromDirection(Map.WEST));
        assertEquals(room.getEast(), room.getDestinationFromDirection(Map.EAST));
    }

    @Test
    void testGetNameDesc() {
        assertEquals("name", room.getName());
        assertEquals("desc", room.getDescription());
    }

    @Test
    void testGetInventory() {
        assertEquals(0, room.getInventory().getItems().size());
    }

    @Test
    void testLockedRoom() {
       Item item = new Item("name", "desc");
       Room lockedRoom = new Room("name", "desc", -1, -1, -1, -1, new Inventory(),
       item);

       assertEquals(item, lockedRoom.getRequired());
    }


    @Test
    void testRiddle() {
        Riddle riddle = new Riddle("question", "answer", new Item("name", "desc"));
        Room riddleRoom = new Room("name", "desc", -1, -1, -1, -1, new Inventory(), riddle);

        assertEquals(riddle, riddleRoom.getRiddle());
    }
}
