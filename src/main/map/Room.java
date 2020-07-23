package map;

import item.Item;

import java.util.HashMap;
import java.util.List;

public class Room {
    private HashMap<String, Integer> directions;
    private List<Item> items;
    private String name;
    private String description;

    public Room(String name, String description, HashMap<String, Integer> directions, List<Item> items) {
        this.name = name;
        this.description = description;
        this.directions = directions;
        this.items = items;
    }


}
