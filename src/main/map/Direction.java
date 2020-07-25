package map;

public class Direction {
    public static final int NORTH = 0;
    public static final int WEST = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;

    public static int stringToInt(String str) {
        String s = str.toLowerCase();
        return s.equals("north") ? NORTH : s.equals("west") ? WEST :
                s.equals("south") ? SOUTH : EAST;
    }

    public static String intToString(int dir) {
        return dir == NORTH ? "north" : dir == WEST ? "west" :
                dir == SOUTH ? "south" : "east";
    }
}
