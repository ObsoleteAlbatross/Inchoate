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

public class SaveFileHandlerTest {
    Player originalPlayer;
    Item playerItem;
    Item roomItem;
    Item riddleItem;
    Inventory roomInv;
    Riddle roomRiddle;
    Room room1;
    Room room2;
    String saveFile;
    String loadFile;

    @BeforeEach
    void runBefore() {
        saveFile = "./data/save" +
                "Test.save";
        loadFile = "./data/loadTest.save";

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

    /*
     * Since SaveFileHandler only contains static methods, the default constructor is not needed
     * and therefore never tested which makes autobot unhappy.
     * As per piazza post followup https://piazza.com/class/kc0wnmopmqo4vi?cid=448 or @448_f1,
     * I have created this dummy test despite it technically being an "attempt to `fake` code coverage"
     * According to Bhavesh / Vegetables:
     *    It does. However, this is more of a special case where the code coverage runner would
     *    penalize you for a method you do not need to call anywhere else in your code.
     *    Therefore, it should be fine if you create a dummy test for only this.
     *    Also, make sure to clearly leave a comment above the text explaining why it does
     *    not have any assert statements. You can link to this post if you want as well.
     * I pray for no penalization as this is done in good faith, but these days good faith
     * is often not enough. Why am I rambling on inside a comment. Anyways, have a nice day
     * whomever stumbles upon this :)
     */
    @Test
    void testDummyDefaultConstructor() {
        SaveFileHandler saveFileHandler = new SaveFileHandler();
        assertEquals(saveFileHandler.getClass(), SaveFileHandler.class);
    }

    // Testing based on TellerApp
    @Test
    void testWriter() {
        try {
            SaveFileHandler.saveFile(saveFile, originalPlayer);
        } catch (IOException e) {
            fail();
        }

        // Read test save file and see if nothing has changed
        Player savePlayer = new Player();
        try {
            savePlayer = SaveFileHandler.loadFile(saveFile);
        } catch (Exception e) {
            fail();
        }
        assertEquals(originalPlayer.getInventory().getItems().size(),
                savePlayer.getInventory().getItems().size());
        assertTrue(savePlayer.getInventory().getItems().containsKey(playerItem.getName()));
        assertEquals(originalPlayer.getMap().getCurrentIndex(),
                savePlayer.getMap().getCurrentIndex());
        assertEquals(originalPlayer.getMap().getRoomByIndex(0).isVisited(),
                savePlayer.getMap().getRoomByIndex(0).isVisited());
        assertEquals(originalPlayer.getMap().getRoomByIndex(1).isVisited(),
                savePlayer.getMap().getRoomByIndex(1).isVisited());
        assertEquals(originalPlayer.getMap().getCurrentRoom().getRiddle().getQuestion(),
                savePlayer.getMap().getCurrentRoom().getRiddle().getQuestion());
        assertEquals(originalPlayer.getMap().getCurrentRoom().getRiddle().getItem().getName(),
                savePlayer.getMap().getCurrentRoom().getRiddle().getItem().getName());
    }

    @Test
    void testLoad() {
        Player savePlayer = new Player();
        try {
            savePlayer = SaveFileHandler.loadFile(loadFile);
        } catch (Exception e) {
            fail();
        }
        assertEquals(originalPlayer.getInventory().getItems().size(),
                savePlayer.getInventory().getItems().size());
        assertEquals(originalPlayer.getMap().getCurrentIndex(),
                savePlayer.getMap().getCurrentIndex());
        assertEquals(originalPlayer.getMap().getRoomByIndex(0).isVisited(),
                savePlayer.getMap().getRoomByIndex(0).isVisited());
        assertEquals(originalPlayer.getMap().getRoomByIndex(1).isVisited(),
                savePlayer.getMap().getRoomByIndex(1).isVisited());
        assertEquals(originalPlayer.getMap().getCurrentRoom().getRiddle().getQuestion(),
                savePlayer.getMap().getCurrentRoom().getRiddle().getQuestion());
        assertEquals(originalPlayer.getMap().getCurrentRoom().getRiddle().getItem().getName(),
                savePlayer.getMap().getCurrentRoom().getRiddle().getItem().getName());
    }

    @Test
    void testFileNotFoundException() {
        try {
            SaveFileHandler.loadFile(loadFile + "JALSJLDKAJSD");
            fail();
        } catch (Exception e) {
            // nothing to do here, excepted result
        }
    }
}
