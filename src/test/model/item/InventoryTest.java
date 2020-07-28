package model.item;

import model.item.Inventory;
import model.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    Inventory inventory;

    @BeforeEach
    void runBefore() {
        inventory = new Inventory();
    }

    @Test
    void testGetItem() {
        Item item1 = new Item("item1", "interesting");
        Item item2 = new Item("item2", "interesting");
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        inventory.addItem(items);

        try {
            inventory.getItem("hihihihiThisWillFAIL!!");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }

        assertEquals(item1, inventory.getItem(item1.getName()));
        assertEquals(item2, inventory.getItem(item2.getName()));
    }

    @Test
    void testAddItemSingular() {
        Item item1 = new Item("item1", "interesting");
        assertEquals(0, inventory.getItems().size());
        inventory.addItem(item1);
        assertEquals(1, inventory.getItems().size());
        assertTrue(inventory.getItems().contains(item1));
    }

    @Test
    void testAddItemMultiple() {
        Item item1 = new Item("item1", "interesting");
        Item item2 = new Item("item2", "interesting");
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        inventory.addItem(items);
        assertEquals(2, inventory.getItems().size());
        assertTrue(inventory.getItems().contains(items.get(0)));
        assertTrue(inventory.getItems().contains(items.get(1)));
    }

    @Test
    void testRemoveItem() {
        Item item1 = new Item("item1", "interesting");
        Item item2 = new Item("item2", "interesting");
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        inventory.addItem(items);
        try {
            inventory.removeItem("hihihihiThisWillFAIL!!");
            fail();
        } catch (Exception e) {
            // assertEquals(IllegalArgumentException.class, e.getClass());
        }

        inventory.removeItem(item1.getName());
        assertEquals(1, inventory.getItems().size());
        assertFalse(inventory.getItems().contains(item1));

        inventory.removeItem(item2.getName());
        assertEquals(0, inventory.getItems().size());
        assertFalse(inventory.getItems().contains(item2));
    }
}
