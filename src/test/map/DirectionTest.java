package map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {
    @Test
    void testStringToInt() {
        assertEquals(Direction.NORTH, Direction.stringToInt("NORTH"));
        assertEquals(Direction.NORTH, Direction.stringToInt("north"));
        assertEquals(Direction.SOUTH, Direction.stringToInt("SOUTH"));
        assertEquals(Direction.SOUTH, Direction.stringToInt("south"));
        assertEquals(Direction.EAST, Direction.stringToInt("EAST"));
        assertEquals(Direction.EAST, Direction.stringToInt("east"));
        assertEquals(Direction.WEST, Direction.stringToInt("WEST"));
        assertEquals(Direction.WEST, Direction.stringToInt("west"));
    }
}
