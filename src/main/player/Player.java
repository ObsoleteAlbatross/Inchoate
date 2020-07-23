package player;

import map.Map;

public class Player {
    private int hp;
    private int mp;
    private Inventory inventory;
    private Map map;

    public Player() {
        this.inventory = new Inventory(5);
        this.map = new Map();
    }

    public void move(int direction) {
        int destination = map.getCurrentRoom().getDestinationFromDirection(direction);
        map.setCurrentIndex(destination);
    }

    public boolean checkMove(int direction) {
        int destination = map.getCurrentRoom().getDestinationFromDirection(direction);
        return map.getCurrentRoom().getDestinationFromDirection(direction) != -1;
    }


    // Getters and setters
    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}

