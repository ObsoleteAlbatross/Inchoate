package ui;

import items.Inventory;
import items.Item;
import map.Map;
import map.Riddle;
import map.Room;
import player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Inchoate game
public class Inchoate {

    private final Player player;
    List<String> moveCommands;
    private String primaryText;
    private String secondaryText;

    // EFFECTS: Performs some setup and runs the Inchoate game
    public Inchoate() {
        player = new Player();
        makeMap();
        setupCommands();
        welcomeText();
        runInchoate();
    }

    // MODIFIES: this
    // EFFECTS: the game loop
    private void runInchoate() {
        boolean isGameRunning = true;
        while (isGameRunning) {
            boolean validCommand = false;
            while (!validCommand) {
                String[] command;
                Scanner input = new Scanner(System.in);
                command = input.nextLine().toLowerCase().split(" ", 2);
                try {
                    isGameRunning = processCommand(command);
                    validCommand = true;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.toString().split(": ")[1]);
                    validCommand = false;
                }
            }
            if (isGameRunning) {
                isGameRunning = winCondition();
            }
        }
    }

    private void welcomeText() {
        System.out.println("Welcome to Inchoate...");
        System.out.println("You find yourself in Bilgewater. "
                + "You must go save the princess from the evil clutches of the Noxian empire!");
    }

    private void makeMap() {
        player.getMap().addRoom(new Room("Bilgewater", "Bilgewater is a port city", 1, 4, 6, 5, new Inventory()));
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
        player.getMap().addRoom(new Room("Freljord", "As cold as 'her' heart :C", -1, 0, -1, -1, new Inventory(), i2));
        player.getMap().addRoom(new Room("Vestaya Tribe", "Xayah and Rakan uwu", 0, -1, -1, -1, new Inventory(),
                new Riddle("What's red and looks like penguins?", "red penguin", new Item("Vestaya", "Vestaya"))));
        player.getMap().getCurrentRoom().setVisited(true);
    }

    private void setupCommands() {
        moveCommands = new ArrayList<>();
        moveCommands.add("north");
        moveCommands.add("south");
        moveCommands.add("east");
        moveCommands.add("west");
    }

    private boolean processCommand(String[] command) throws IllegalArgumentException {
        if (command[0].equals("quit")) {
            System.out.println("Quitting game...");
            return false;
        } else if (moveCommands.contains(command[0])) {
            movePlayer(command[0]);
        } else if (command[0].equals("here")) {
            getInfoCurrentRoom();
        } else if (command[0].equals("search")) {
            search();
        } else if (command[0].equals("take")) {
            take(command[1]);
        } else if (command[0].equals("drop")) {
            drop(command[1]);
        } else if (command[0].equals("inventory")) {
            viewInventory();
        } else if (command[0].equals("riddle")) {
            riddle();
        } else {
            throw new IllegalArgumentException("Invalid command!");
        }
        return true;
    }

    // EFFECTS: Shows the riddle question if it exists
    private void riddle() throws IllegalArgumentException {
        if (player.getMap().getCurrentRoom().getRiddle() == null) {
            throw new IllegalArgumentException("There is no riddle in this room");
        }
        System.out.println(player.getMap().getCurrentRoom().getRiddle().getQuestion());
        Scanner input = new Scanner(System.in);
        String answer = input.nextLine().toLowerCase();
        answerRiddle(answer);
    }

    // MODIFIES: Player, Riddle
    // EFFECTS: Answer riddle, if correct, then add the item to player hidden inventory
    private void answerRiddle(String answer) throws IllegalArgumentException {
        player.getMap().getCurrentRoom().getRiddle().answerQuestion(answer);
        player.getQuest().addItem(player.getMap().getCurrentRoom().getRiddle().getItem());
    }

    // EFFECTS: print items in player inventory
    private void viewInventory() {
        System.out.println(player.getInventory().getItems());
    }

    // MODIFIES: Player
    // EFFECTS: Take a given item from inventory and put in room
    private void drop(String itemName) throws IllegalArgumentException {
        Item item = player.getInventory().getItem(itemName);
        player.getMap().getCurrentRoom().getInventory().addItem(item);
        player.getInventory().removeItem(itemName);
        System.out.println("Dropped `" + itemName + "` from your inventory");
    }

    // MODIFIES: Player
    // EFFECTS: Take a given item from room and put in player inventory
    void take(String itemName) throws IllegalArgumentException {
        Item item = player.getMap().getCurrentRoom().getInventory().getItem(itemName);
        player.getInventory().addItem(item);
        player.getMap().getCurrentRoom().getInventory().removeItem(item.getName());
        System.out.println("Added `" + item.getName() + "` to your inventory");
    }

    // EFFECTS: Print out info of current room, as if you had just visited it
    private void getInfoCurrentRoom() {
        System.out.println(player.getMap().getCurrentRoom().getName());
        System.out.println(player.getMap().getCurrentRoom().getDescription());
    }

    // MODIFIES: this, Player
    // EFFECTS: move the player to a room in given direction if possible
    private void movePlayer(String dir) throws IllegalArgumentException {
        player.move(Map.stringToInt(dir));
        System.out.println(player.getMap().getCurrentRoom().getName());
        if (!player.getMap().getCurrentRoom().isVisited()) {
            System.out.println(player.getMap().getCurrentRoom().getDescription());
            player.getMap().getCurrentRoom().setVisited(true);
        }
    }

    // EFFECTS: list any items in current room
    private void search() {
        System.out.println(player.getMap().getCurrentRoom().getInventory().getItems());
    }

    // EFFECTS: Check if player has win game, return false if yes, true otherwise
    //          and print some good stuff to let player know its over
    private boolean winCondition() {
        if (player.getMap().getCurrentRoom().getName().equals("Noxus")) {
            System.out.println("Congratulations you have saved the princess or something");
            System.out.println("The game is now over lol");
            return false;
        }
        return true;
    }
}
