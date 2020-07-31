package persistence;

import model.player.Player;

import java.io.*;

// This class handles file saving
public class SaveFileHandler {
    // EFFECTS: Return a player created from reading a file
    public static Player loadFile(String file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Player player = (Player) objectInputStream.readObject();
        objectInputStream.close();
        return player;
    }

    // EFFECTS: Save player data (and all it encompasses) to a file
    public static void saveFile(String file, Player player) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(player);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
}
