package model.item;

import model.item.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {
    @Test
    void testToString() {
        Item item = new Item("item", "desc");
        assertEquals("item (desc)", item.toString());
    }
}
