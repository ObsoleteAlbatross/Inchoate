package model.player;

import model.item.Inventory;
import model.item.Item;
import model.map.Direction;
import model.map.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    void runBefore() {
        player = new Player();
        player.getMap().addRoom(new Room("Bilgewater", "Welcome to Bilgewater", 1, 4, -1, -1, new Inventory()));
        player.getMap().addRoom(new Room("Ionia", "Welcome to Ionia", -1, 2, 0, -1, new Inventory()));
        player.getMap().addRoom(new Room("Demacia", "Welcome to Demacia", -1, 3, -1, 1, new Inventory()));
        player.getMap().addRoom(new Room("Noxus", "Welcome to Noxus", -1, -1, -1, 2, new Inventory()));
        Item item = new Item("name", "desc");
        Room lockedRoom = new Room("name", "desc", -1, -1, -1, 0, new Inventory(),
                item);
        player.getMap().addRoom(lockedRoom);
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
    void testMoveGoodHiddenInventory() {
        player.getQuest().addItem(new Item("name", "desc"));
        player.move(Direction.WEST);
        assertTrue(player.hasItem(new Item("name", "desc")));
        assertEquals(4, player.getMap().getCurrentIndex());
    }

    @Test
    void testMoveGoodVisibleInventory() {
        player.getInventory().addItem(new Item("name", "desc"));
        player.move(Direction.WEST);
        assertTrue(player.hasItem(new Item("name", "desc")));
        assertEquals(4, player.getMap().getCurrentIndex());
    }

    @Test
    void testMoveException() {
        try {
            player.move(Direction.WEST);
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
        for (Direction direction : Direction.values()) {
            try {
                player.move(direction);
            } catch (Exception e) {
                assertEquals(IllegalArgumentException.class, e.getClass());
            }
        }
    }
}
