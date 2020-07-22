package player;

public class Player {
    private int hp;
    private int mp;
    private final Inventory inventory;

    public Player() {
        this.inventory = new Inventory(5);
    }

    // Getters
    public int getHp() {
        return hp;
    }

    // Setters
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
}

