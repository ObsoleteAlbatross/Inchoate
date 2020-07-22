package ui;

import player.Inventory;
import player.Player;

import java.util.Scanner;

// Inchoate game
public class Inchoate {

    private Player player;

    // EFFECTS: Runs the Inchoate game
    public Inchoate() {
        runInchoate();
    }

    // MODIFIES: this
    // EFFECTS: ???
    private void runInchoate() {
        boolean isGameRunning = true;
        String command;
        Scanner input = new Scanner(System.in);
        player = new Player();

        // Game loop
        while (isGameRunning) {
            System.out.println("Please type something...ANYTHING!!!");
            command = input.next().toLowerCase();
            isGameRunning = processCommand(command);
        }
    }

    private boolean processCommand(String command) {
        if (command.equals("quit")) {
            System.out.println("Quitting game");
            return false;
        } else if (command.equals("inventory")) {
            interactInventory();
        }
        return true;
    }

    private void interactInventory() {
        System.out.println(player.getInventory().getItems());
    }
}
