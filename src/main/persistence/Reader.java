package persistence;

import model.player.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Reader {
    public static Player loadFile(String file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Player player = (Player) objectInputStream.readObject();
        objectInputStream.close();
        return player;
    }
}
