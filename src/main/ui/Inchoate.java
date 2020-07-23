package ui;

import map.Direction;
import map.Room;
import player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Inchoate game
public class Inchoate {

    List<String> moveCommands;
    private Player player;
    private String primaryText;
    private String secondaryText;


    // EFFECTS: Runs the Inchoate game
    public Inchoate() {
        primaryText = "Welcome to Inchoate!";
        secondaryText = "You find yourself in a plains opening...";
        makeMap();
        setupCommands();
        runInchoate();
    }

    // MODIFIES: this
    // EFFECTS: the game loop
    private void runInchoate() {
        boolean isGameRunning = true;
        String command;
        Scanner input = new Scanner(System.in);
        player = new Player();
        while (isGameRunning) {
            System.out.println(primaryText);
            if (!player.getMap().getCurrentRoom().isVisited()) {
                System.out.println(secondaryText);
            }
            player.getMap().getCurrentRoom().setVisited(true);
            boolean validCommand = false;
            while (!validCommand) {
                command = input.next().toLowerCase();
                try {
                    isGameRunning = processCommand(command);
                    validCommand = true;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.toString().split(": ")[1]);
                    validCommand = false;
                }
            }
        }
    }

    private void makeMap() {
        player.getMap().addRoom(new Room("Bilgewater", "Welcome to Bilgewater", 1, -1, -1, -1));
        player.getMap().addRoom(new Room("Ionia", "Welcome to Ionia", -1, 2, 0, -1));
        player.getMap().addRoom(new Room("Demacia", "Welcome to Demacia", -1, 3, -1, 1));
        player.getMap().addRoom(new Room("Noxus", "Welcome to Noxus", -1, -1, -1, 2));
    }

    private void setupCommands() {
        moveCommands = new ArrayList<>();
        moveCommands.add("north");
        moveCommands.add("south");
        moveCommands.add("east");
        moveCommands.add("west");
    }

    private boolean processCommand(String command) throws IllegalArgumentException {
        if (command.equals("quit")) {
            System.out.println("Quitting game...");
            return false;
        } else if (moveCommands.contains(command)) {
            if (!movePlayer(command)) {
                throw new IllegalArgumentException("You can't go that way!");
            }
        } else {
            throw new IllegalArgumentException("Invalid command!");
        }
        return true;
    }

    // Modifies: this, Player
    // Effects: move the player to a room in given direction if possible
    private boolean movePlayer(String dir) {
        int direction = dir.equals("north") ? Direction.NORTH : dir.equals("south") ? Direction.SOUTH :
                dir.equals("west") ? Direction.WEST : Direction.EAST;
        if (!player.checkMove(direction)) {
            return false;
        }
        player.move(direction);
        primaryText = player.getMap().getCurrentRoom().getName();
        secondaryText = player.getMap().getCurrentRoom().getDescription();
        return true;
    }
}
