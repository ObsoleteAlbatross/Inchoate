package persistence;

import model.item.Inventory;
import model.item.Item;
import model.map.Riddle;
import model.map.Room;
import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    Player originalPlayer;
    Item playerItem;
    Item roomItem;
    Item riddleItem;
    Inventory roomInv;
    Riddle roomRiddle;
    Room room1;
    Room room2;
    String file = "./data/writeTest.save";


    @BeforeEach
    void runBefore() {
        originalPlayer = new Player();

        playerItem = new Item("player", "player");
        originalPlayer.getInventory().addItem(playerItem);

        roomInv = new Inventory();
        roomItem = new Item("roomItem", "roomItem");
        roomInv.addItem(roomItem);
        room1 = new Room("room1", "room1", -1, -1, -1, -1, roomInv);
        originalPlayer.getMap().addRoom(room1);
        room1.setVisited(true);

        riddleItem = new Item("riddle", "riddle");
        roomRiddle = new Riddle("riddle", "riddle", riddleItem);
        room2 = new Room("room2", "room2", -1, -1, -1, -1, new Inventory(), roomRiddle);
        room2.setVisited(true);
        originalPlayer.getMap().addRoom(room2);

        originalPlayer.getMap().setCurrentIndex(1);
    }

    @Test
    void testWriter() {
        // Test taken from TellerApp
        // Save player to a file
        try {
            Writer.saveFile(file, originalPlayer);
        } catch (IOException e) {
            fail();
        }

        // Read test save file and see if nothing has changed
        Player savePlayer = new Player();
        try {
            savePlayer = Reader.loadFile(file);
        } catch (Exception e) {
            fail();
        }
        assertEquals(originalPlayer.getInventory().getItems().get(0).getName(),
                savePlayer.getInventory().getItems().get(0).getName());
        assertEquals(originalPlayer.getMap().getCurrentIndex(),
                savePlayer.getMap().getCurrentIndex());
        assertEquals(originalPlayer.getMap().getRoomByIndex(0).getInventory().getItems().get(0).getName(),
                savePlayer.getMap().getRoomByIndex(0).getInventory().getItems().get(0).getName());
        assertEquals(originalPlayer.getMap().getRoomByIndex(0).isVisited(),
                savePlayer.getMap().getRoomByIndex(0).isVisited());
        assertEquals(originalPlayer.getMap().getRoomByIndex(1).isVisited(),
                savePlayer.getMap().getRoomByIndex(1).isVisited());
        assertEquals(originalPlayer.getMap().getCurrentRoom().getRiddle().getQuestion(),
                savePlayer.getMap().getCurrentRoom().getRiddle().getQuestion());
        assertEquals(originalPlayer.getMap().getCurrentRoom().getRiddle().getItem().getName(),
                savePlayer.getMap().getCurrentRoom().getRiddle().getItem().getName());
    }
}
