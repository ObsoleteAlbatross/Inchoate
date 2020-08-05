package ui;

// Main class to run the game
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DisplayHandler.createAndShowGUI();
            }
        });
        // new Inchoate();
    }
}
