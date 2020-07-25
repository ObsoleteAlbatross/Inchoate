package map;

import items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RiddleTest {
    private Riddle riddle;
    private Item item;

    @BeforeEach
    void runBefore() {
        item = new Item("item", "item");
        riddle = new Riddle("question", "answer", item);
    }

    @Test
    void testAnswerQuestion() {
        try {
            riddle.answerQuestion("adklasdjjlkas");
        } catch (Exception e) {
            assertEquals("That is not the right answer", e.getLocalizedMessage());
        }
        assertFalse(riddle.isAnswered());
        riddle.answerQuestion("answer");
        assertTrue(riddle.isAnswered());
        try {
            riddle.answerQuestion("adklasdjjlkas");
        } catch (Exception e) {
            assertEquals("You have already answered this question", e.getLocalizedMessage());
        }
    }

    @Test
    void testGetters() {
        assertEquals(item, riddle.getItem());
        assertEquals("question", riddle.getQuestion());
    }
}
