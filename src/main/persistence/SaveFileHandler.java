package persistence;

import model.player.Player;

import java.io.*;

public class SaveFileHandler {
    private final String file;

    // EFFECTS: Set the default save file as the given file
    public SaveFileHandler(String file) {
        this.file = file;
    }

    // EFFECTS: Return a player created from reading a file
    public Player loadFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Player player = (Player) objectInputStream.readObject();
        objectInputStream.close();
        return player;
    }

    // EFFECTS: Save player data (and all it encompasses) to a file
    public void saveFile(Player player) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(player);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
}
