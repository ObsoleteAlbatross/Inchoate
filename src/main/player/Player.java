package player;

import map.Map;

import java.util.ArrayList;

public class Player {
    private Map map;

    public Player() {
        this.map = new Map(new ArrayList<>());
    }

    public void move(int direction) {
        map.setCurrentIndex(map.getCurrentRoom().getDestinationFromDirection(direction));
    }

    public boolean checkMove(int direction) {
        return map.getCurrentRoom().getDestinationFromDirection(direction) != -1;
    }


    // Getters and setters
    public Map getMap() {
        return map;
    }
}

