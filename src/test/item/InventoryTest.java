package item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Inventory;

public class InventoryTest {
    private Inventory inventory;
    private int maxSize = 5;

    @BeforeEach
    void runBefore() {
        inventory = new Inventory(maxSize);
    }

    @Test
    void testAddItem() {

    }

}
