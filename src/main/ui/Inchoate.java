package ui;

import model.item.Inventory;
import model.item.Item;
import model.map.Direction;
import model.map.Riddle;
import model.map.Room;
import model.player.Player;
import persistence.SaveFileHandler;


import javax.security.sasl.SaslException;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.*;
import javax.security.sasl.SaslException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// GUI base from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// Inchoate game
public class Inchoate {

    private Player player;
    boolean isGameRunning;
    List<String> moveCommands;
    DisplayHandler displayHandler;

    // EFFECTS: Performs some setup and runs the Inchoate game
    public Inchoate() {
        player = new Player();
        makeMap();
        setupCommands();
        starting();
        runInchoate();
    }

    // EFFECTS: As above but intented for use with GUI
    public Inchoate(DisplayHandler displayHandler) {
        player = new Player();
        makeMap();
        setupCommands();
        welcomeText();
        this.displayHandler = displayHandler;
        // runInchoate();
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
                    print(e.toString().split(": ")[1], Color.RED);
                    validCommand = false;
                }
            }
            if (isGameRunning) {
                isGameRunning = winCondition();
            }
        }
    }


    private void starting() {
        print("Welcome to Inchoate...");
        print("Would you line to start a `new` game or `load` from existing?");
        Scanner input = new Scanner(System.in);
        String[] command = input.nextLine().toLowerCase().split(" ", 2);
        if (command[0].equals("new")) {
            welcomeText();
            return;
        }
        boolean validCommand = false;
        while (!validCommand) {
            try {
                processCommand(command);
                validCommand = true;
            } catch (Exception e) {
                System.out.println(e.toString().split(": ")[1]);
                validCommand = false;
            }
        }
    }

    // EFFECTS: Print some welcome text
    private void welcomeText() {
        print("You find yourself in Bilgewater. "
                + "You must go save the princess from the evil clutches of the Noxian empire!");
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
    private void processCommand(String[] command) throws IllegalArgumentException {
        if (command[0].equals("quit")) {
            quit();
        } else if (moveCommands.contains(command[0])) {
            movePlayer(command[0]);
        } else if (command[0].equals("here")) {
            here();
        } else if (command[0].equals("search")) {
            search();
        } else if (command[0].equals("take") && command.length > 1) {
            take(command[1]);
        } else if (command[0].equals("drop") && command.length > 1) {
            drop(command[1]);
        } else if (command[0].equals("inventory")) {
            viewInventory();
        } else if (command[0].equals("riddle")) {
            riddle();
        } else if (command[0].equals("save")) {
            save();
        } else if (command[0].equals("load")) {
            load();
        } else {
            throw new IllegalArgumentException("Invalid command!");
        }
    }

    // MODIFIES: this
    // EFFECTS: quit the game
    private void quit() throws IOException, IllegalArgumentException {
        print("Would you like to save the game?");
        Scanner input = new Scanner(System.in);
        String command = input.nextLine().toLowerCase();
        if (command.equals("yes")) {
            save();
        } else if (!command.equals("no")) {
            throw new IllegalArgumentException("Please enter either `yes` or `no`");
        }
        System.out.println("Quitting game...");
        isGameRunning = false;
    }

    // EFFECTS: Save file to regex: ./data/saveFile[123].save
    private void save() throws IOException {
        print("Choose a save file to save to [1, 2, 3]");
        String file = getSaveFile();
        try {
            SaveFileHandler.saveFile(file, player);
        } catch (IOException e) {
            throw new IOException("Failed to save");
        }
        print("Saved to slot " + file.charAt(15));
    }

    // EFFECTS: Load file to regex: ./data/saveFile[123].save
    private void load() throws IOException, ClassNotFoundException {
        print("Choose a save file to load from [1, 2, 3]");
        String file = getSaveFile();
        try {
            SaveFileHandler.loadFile(file);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Can't load a save file that doesn't exist");
        } catch (IOException e) {
            throw new IOException("Failed to load save file");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Class not found exception");
        }
        print("Loaded from slot " + file.charAt(15));
        here();
    }

    // EFFECTS: Get the save file based on user input
    private String getSaveFile() throws IllegalArgumentException {
        Scanner input = new Scanner(System.in);
        String command = input.nextLine().toLowerCase();
        if (!command.equals("1") && !command.equals("2") && !command.equals("3")) {
            throw new IllegalArgumentException("Please select a valid save file");
        }
        return "./data/saveFile" + command + ".save";
    }

    // EFFECTS: Shows the riddle question if it exists
    private void riddle() throws IllegalArgumentException {
        if (player.getMap().getCurrentRoom().getRiddle() == null) {
            throw new IllegalArgumentException("There is no riddle in this room");
        }
        print(player.getMap().getCurrentRoom().getRiddle().getQuestion());
        Scanner input = new Scanner(System.in);
        String answer = input.nextLine().toLowerCase();
        answerRiddle(answer);
    }

    // MODIFIES: Player, Riddle
    // EFFECTS: Answer riddle, if correct, then add the item to player hidden inventory
    private void answerRiddle(String answer) throws IllegalArgumentException {
        player.getMap().getCurrentRoom().getRiddle().answerQuestion(answer);
        player.getQuest().addItem(player.getMap().getCurrentRoom().getRiddle().getItem());
        print("That is the correct answer!");
        print("You here a door unlock in a distant area...");
    }

    // EFFECTS: print items in player inventory
    private void viewInventory() {
        print(player.getInventory().getItems().toString());
    }

    // MODIFIES: Player
    // EFFECTS: Take a given item from inventory and put in room
    private void drop(String itemName) throws IllegalArgumentException {
        Item item = player.getInventory().getItem(itemName);
        player.getMap().getCurrentRoom().getInventory().addItem(item);
        player.getInventory().removeItem(itemName);
        print("Dropped `" + itemName + "` from your inventory");
    }

    // MODIFIES: Player
    // EFFECTS: Take a given item from room and put in player inventory
    void take(String itemName) throws IllegalArgumentException {
        Item item = player.getMap().getCurrentRoom().getInventory().getItem(itemName);
        player.getInventory().addItem(item);
        player.getMap().getCurrentRoom().getInventory().removeItem(item.getName());
        print("Added `" + item.getName() + "` to your inventory");
    }

    // EFFECTS: Print out info of current room, as if you had just visited it
    private void here() {
        print("Location Name: " + player.getMap().getCurrentRoom().getName());
        print("Location Description : " + player.getMap().getCurrentRoom().getDescription());
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
        System.out.println(player.getMap().getCurrentRoom().getName());
        if (!player.getMap().getCurrentRoom().isVisited()) {
            print("Location Description: " + player.getMap().getCurrentRoom().getDescription());
            player.getMap().getCurrentRoom().setVisited(true);
        }
    }

    // EFFECTS: list any items in current room
    private void search() {
        print(player.getMap().getCurrentRoom().getInventory().getItems().toString());
    }

    // EFFECTS: Check if player has win game, return false if yes, true otherwise
    //          and print some good stuff to let player know its over
    private boolean winCondition() {
        if (player.getMap().getCurrentRoom().getName().equals("Noxus")) {
            print("Congratulations you have saved the princess or something");
            print("The game is now over lol");
            return false;
        }
        return true;
    }

    public void print(String str) {
        System.out.println(str);
        if (displayHandler != null) {
            displayHandler.print(str);
        }
    }

    public void print(String str, Color c) {
        System.out.println(str);
        if (displayHandler != null) {
            displayHandler.print(str, c);
        }
    }
}
