package items;

// Item class, has a name and description
public class Item {
    String name;
    String description;

    // EFFECTS: Set name and description to given
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // EFFECTS: Return item name
    public String getName() {
        return name;
    }

    // EFFECTS: Return item description
    public String getDescription() {
        return description;
    }

    // EFFECTS: Override Object.toString to be in a nicer format
    @Override
    public String toString() {
        return this.getName() + " (" + this.getDescription() + ")";
    }
}
