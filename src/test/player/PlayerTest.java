package player;

import items.Inventory;
import map.Direction;
import map.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void runBefore() {
        player = new Player();
        player.getMap().addRoom(new Room("Bilgewater", "Welcome to Bilgewater", 1, -1, -1, -1, new Inventory()));
        player.getMap().addRoom(new Room("Ionia", "Welcome to Ionia", -1, 2, 0, -1, new Inventory()));
        player.getMap().addRoom(new Room("Demacia", "Welcome to Demacia", -1, 3, -1, 1, new Inventory()));
        player.getMap().addRoom(new Room("Noxus", "Welcome to Noxus", -1, -1, -1, 2, new Inventory()));
    }

    @Test
    void testMoveGood() {
        Room originalRoom = player.getMap().getCurrentRoom();
        player.getMap().getCurrentRoom().setVisited(true);
        player.move(Direction.NORTH);
        player.getMap().getCurrentRoom().setVisited(true);
        assertEquals(1, player.getMap().getCurrentIndex());
        assertNotEquals(originalRoom, player.getMap().getCurrentRoom());
        player.move(Direction.SOUTH);
        assertTrue(player.getMap().getCurrentRoom().isVisited());
        assertEquals(0, player.getMap().getCurrentIndex());
        assertEquals(originalRoom, player.getMap().getCurrentRoom());
    }

    @Test
    void testMoveException() {
        // Directions are 0-3
        for (int i = 0; i < 4; i++) {
            try {
                player.move(i);
            } catch (Exception e) {
                assertEquals(IllegalArgumentException.class, e.getClass());
            }
        }
    }

}
