package player;

import map.Map;

public class Player {
    private Map map;

    public Player() {
        this.map = new Map();
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

