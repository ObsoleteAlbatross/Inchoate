package item;

// Items
public abstract class Item {

    private String name;
    private String description;
    private int durability;
    private boolean isStackable;

    public Item() {
    }

    public void use() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDurability() {
        return durability;
    }

    public boolean isStackable() {
        return isStackable;
    }
}
