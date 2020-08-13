package ui;

import model.item.Inventory;
import model.item.Item;
import model.map.Direction;
import model.map.Riddle;
import model.map.Room;
import model.player.Player;
import persistence.SaveFileHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Inchoate game
public class Inchoate {

    public Player player;
    List<String> moveCommands;
    boolean isGameRunning;
    private DisplayHandler displayHandler;

    // EFFECTS: Performs some setup and runs the Inchoate game
    public Inchoate() {
        player = new Player();
        makeMap();
        setupCommands();
        starting();
        runInchoate();
    }

    // EFFECTS: Game with a GUI (Display Handler)
    public Inchoate(DisplayHandler displayHandler) {
        this.displayHandler = displayHandler;
        player = new Player();
        makeMap();
        setupCommands();
        starting();
    }

    // MODIFIES: this
    // EFFECTS: the game loop
    private void runInchoate() {
        isGameRunning = true;
        while (isGameRunning) {
            boolean validCommand = false;
            while (!validCommand) {
                String[] command;
                Scanner input = new Scanner(System.in);
                command = input.nextLine().toLowerCase().split(" ", 2);
                try {
                    processCommand(command);
                    validCommand = true;
                } catch (Exception e) {
                    System.out.println(e.toString().split(": ")[1]);
                    validCommand = false;
                }
            }
            if (isGameRunning) {
                isGameRunning = winCondition();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: run through starting sequence
    private void starting() {
        displayHandler.print("Would you line to start a `new` game or `load` from existing?", Color.BLUE);
        displayHandler.addCommand("new");
        displayHandler.addCommand("load");
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                displayHandler.getTextField().removeActionListener(this);
                String command = displayHandler.getTextField().getText();
                displayHandler.print(command, Color.BLACK);
                if (command.equals("new")) {
                    displayHandler.initGame();
                    welcomeText();
                    return;
                }
                try {
                    String[] commands = {command};
                    processCommand(commands);
                } catch (Exception exception) {
                    displayHandler.print(exception.toString().split(": ")[1], Color.RED);
                    displayHandler.getTextField().addActionListener(this);
                }
            }
        };
        displayHandler.getTextField().addActionListener(actionListener);
    }

    // EFFECTS: Print some welcome text
    private void welcomeText() {
        displayHandler.print("You find yourself in Bilgewater. "
                + "You must go save the princess from the evil clutches of the Noxian empire!", Color.BLUE);
    }

    // MODIFIES: Player
    // EFFECTS: Make the map
    private void makeMap() {
        player.getMap().addRoom(new Room("Bilgewater", "Bilgewater is a port city", 1, 4, -1, 5, new Inventory()));
        player.getMap().addRoom(new Room("Ionia", "Ionia.... uuhhhh Master Yi", -1, 2, 0, -1, new Inventory()));
        player.getMap().addRoom(new Room("Demacia", "DEMACIAAAAAAAAA!!!!!!!!!!", -1, 3, -1, 1, new Inventory()));
        player.getMap().addRoom(new Room("Noxus", "Kitty Kat Katarina :3", -1, -1, -1, 2, new Inventory(),
                new Item("Vestaya", "Vestaya")));
        Item i1 = new Item("Key to Yennefer's Room", "A common item");
        Item i2 = new Item("Mushroom", "A mushroom found in Mario games");
        Inventory inv4 = new Inventory();
        inv4.addItem(i1);
        inv4.addItem(i2);
        player.getMap().addRoom(new Room("Shurima", "The sands are endless here", -1, -1, -1, 0, inv4));
        player.getMap().addRoom(new Room("Freljord", "As cold as 'her' heart :C", -1, 0, -1, 6, new Inventory(), i2));
        player.getMap().addRoom(new Room("Vestaya Tribe", "Xayah and Rakan uwu", 0, 5, -1, -1, new Inventory(),
                new Riddle("What's red and looks like penguins?", "red penguin", new Item("Vestaya", "Vestaya"))));
        player.getMap().getCurrentRoom().setVisited(true);
    }

    // EFFECTS: Setup some commands which can take up extra space
    private void setupCommands() {
        moveCommands = new ArrayList<>();
        moveCommands.add("north");
        moveCommands.add("south");
        moveCommands.add("east");
        moveCommands.add("west");
    }

    // EFFECTS: Parse the command
    public void processCommand(String[] command) throws Exception {
        if (command[0].equals("quit")) {
            quit();
        } else if (moveCommands.contains(command[0])) {
            movePlayer(command[0]);
        } else if (command[0].equals("here")) {
            here();
        } else if (command[0].equals("search")) {
            search();
        } else if (command[0].equals("take") || command[0].equals("drop")) {
            inventoryCommands(command);
        } else if (command[0].equals("inventory")) {
            viewInventory();
        } else if (command[0].equals("riddle")) {
            riddle();
        } else if (command[0].equals("save")) {
            save();
        } else if (command[0].equals("load")) {
            load();
        } else if (command[0].equals("help")) {
            help();
        } else {
            throw new IllegalArgumentException("Invalid command!");
        }
    }

    // EFFECTS: Handle inventory related commands
    private void inventoryCommands(String[] command) throws IllegalArgumentException {
        if (command[0].equals("take")) {
            take(command[1]);
        } else if (command[0].equals("drop")) {
            drop(command[1]);
        }
    }

    // EFFECTS: Print some help message
    private void help() {
        displayHandler.print("There is a basic user manual with commands in the README", Color.BLUE);
    }

    // MODIFIES: this
    // EFFECTS: quit the game
    public void quit() throws IllegalArgumentException {
        displayHandler.print("Are you sure you want to quit?", Color.BLUE);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayHandler.getTextField().removeActionListener(this);
                String command = displayHandler.getTextField().getText();
                displayHandler.print(command, Color.BLACK);
                if (command.equals("yes")) {
                    displayHandler.print("Quitting game...", Color.BLUE);
                    try {
                        Thread.sleep(4000);
                    } catch (Exception exception) {
                        // do nothing
                    }
                    System.exit(0);
                }
            }
        };
        displayHandler.getTextField().addActionListener(actionListener);
    }

    // EFFECTS: Save file to regex: ./data/saveFile[123].save
    private void save() {
        displayHandler.print("Choose a save file to save to [1, 2, 3]", Color.BLUE);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                displayHandler.getTextField().removeActionListener(this);
                String command = displayHandler.getTextField().getText();
                displayHandler.print(command, Color.BLACK);

                if (!command.equals("1") && !command.equals("2") && !command.equals("3")) {
                    displayHandler.getTextField().addActionListener(this);
                    throw new IllegalArgumentException("Please select a valid save file");
                } else {
                    try {
                        saveFile("./data/saveFile" + command + ".save");
                    } catch (Exception exception) {
                        displayHandler.print(exception.toString().split(": ")[1], Color.RED);
                        displayHandler.getTextField().addActionListener(this);
                    }
                }
            }
        };
        displayHandler.getTextField().addActionListener(actionListener);
    }

    // EFFECTS: save file
    public void saveFile(String file) throws IOException {
        try {
            SaveFileHandler.saveFile(file, player);
        } catch (IOException e) {
            throw new IOException("Failed to save");
        }
        displayHandler.print("Saved to slot " + file.charAt(15), Color.BLUE);
    }

    // MODIFIES: this, player
    // EFFECTS: Load file to regex: ./data/saveFile[123].save
    private void load() {
        displayHandler.print("Choose a save file to load from [1, 2, 3]", Color.BLUE);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                displayHandler.getTextField().removeActionListener(this);
                String command = displayHandler.getTextField().getText();
                displayHandler.print(command, Color.BLACK);

                if (!command.equals("1") && !command.equals("2") && !command.equals("3")) {
                    displayHandler.getTextField().addActionListener(this);
                    throw new IllegalArgumentException("Please select a valid save file");
                } else {
                    try {
                        loadFile("./data/saveFile" + command + ".save");
                    } catch (Exception e) {
                        displayHandler.print(e.toString().split(": ")[1], Color.RED);
                        displayHandler.getTextField().addActionListener(this);
                    }
                }
            }
        };
        displayHandler.getTextField().addActionListener(actionListener);

    }

    // MODIFIES: this, player
    // EFFECTS: Load file
    public void loadFile(String file) throws IOException, ClassNotFoundException {
        try {
            player = SaveFileHandler.loadFile(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Can't load a save file that doesn't exist");
        } catch (IOException e) {
            throw new IOException("Failed to load save file");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Class not found exception");
        }
        displayHandler.initGame();
        displayHandler.print("Loaded from slot " + file.charAt(15), Color.BLUE);
        here();
    }

    // EFFECTS: Shows the riddle question if it exists
    private void riddle() throws IllegalArgumentException {
        if (player.getMap().getCurrentRoom().getRiddle() == null) {
            throw new IllegalArgumentException("There is no riddle in this room");
        }
        displayHandler.answering = true;
        displayHandler.print(player.getMap().getCurrentRoom().getRiddle().getQuestion(), Color.BLUE);
    }

    // MODIFIES: Player, Riddle
    // EFFECTS: Answer riddle, if correct, then add the item to player hidden inventory
    public void answerRiddle(String answer) throws IllegalArgumentException {
        player.getMap().getCurrentRoom().getRiddle().answerQuestion(answer);
        player.getQuest().addItem(player.getMap().getCurrentRoom().getRiddle().getItem());
        displayHandler.print("That is the correct answer!", Color.BLUE);
        displayHandler.print("You here a door unlock in a distant area...", Color.BLUE);
    }

    // EFFECTS: print items in player inventory
    private void viewInventory() {
        displayHandler.print(player.getInventory().toString(), Color.BLUE);
    }

    // MODIFIES: Player
    // EFFECTS: Take a given item from inventory and put in room
    public void drop(String itemName) throws IllegalArgumentException {
        Item item = player.getInventory().getItem(itemName);
        player.getMap().getCurrentRoom().getInventory().addItem(item);
        player.getInventory().removeItem(itemName);
        displayHandler.print("Dropped `" + itemName + "` from your inventory", Color.BLUE);
    }

    // MODIFIES: Player
    // EFFECTS: Take a given item from room and put in player inventory
    public void take(String itemName) throws IllegalArgumentException {
        Item item = player.getMap().getCurrentRoom().getInventory().getItem(itemName);
        player.getInventory().addItem(item);
        player.getMap().getCurrentRoom().getInventory().removeItem(item.getName());
        displayHandler.print("Added `" + item.getName() + "` to your inventory", Color.BLUE);
    }

    // EFFECTS: Print out info of current room, as if you had just visited it
    private void here() {
        displayHandler.print("Location name: " + player.getMap().getCurrentRoom().getName(), Color.BLUE);
        displayHandler.print("Location description: " + player.getMap().getCurrentRoom().getDescription(), Color.BLUE);
    }

    // MODIFIES: this, Player
    // EFFECTS: move the player to a room in given direction if possible
    private void movePlayer(String dir) throws IllegalArgumentException {
        switch (dir) {
            case "north":
                player.move(Direction.NORTH);
                break;
            case "south":
                player.move(Direction.SOUTH);
                break;
            case "west":
                player.move(Direction.WEST);
                break;
            case "east":
                player.move(Direction.EAST);
                break;
        }
        displayHandler.print("Location name: " + player.getMap().getCurrentRoom().getName(), Color.BLUE);
        if (!player.getMap().getCurrentRoom().isVisited()) {
            displayHandler.print("Location descripton: "
                    + player.getMap().getCurrentRoom().getDescription(), Color.BLUE);
            player.getMap().getCurrentRoom().setVisited(true);
        }
    }

    // EFFECTS: list any items in current room
    private void search() {
        displayHandler.print(player.getMap().getCurrentRoom().getInventory().toString(), Color.BLUE);
    }

    // EFFECTS: Check if player has win game, return false if yes, true otherwise
    //          and print some good stuff to let player know its over
    public boolean winCondition() {
        if (player.getMap().getCurrentRoom().getName().equals("Noxus")) {
            displayHandler.print("Congratulations you have saved the princess or something", Color.BLUE);
            displayHandler.print("The game is now over lol", Color.BLUE);
            displayHandler.print("The game is now over lol", Color.BLUE);
            displayHandler.print("The game is now over lol", Color.BLUE);
            displayHandler.print("Quitting game...", Color.BLUE);
            Thread quitThread = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(8000);
                    } catch (Exception exception) {
                        // do nothing
                    }
                    System.exit(0);
                }
            };
            quitThread.start();
            return false;
        }
        return true;
    }
}
