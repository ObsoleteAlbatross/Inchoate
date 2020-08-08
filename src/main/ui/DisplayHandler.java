// Sources
// Autocompletion based on :
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextAreaDemoProject/src/components/TextAreaDemo.java


package ui;

import model.item.Item;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DisplayHandler extends JFrame implements DocumentListener, ActionListener {

    private static final String COMMIT_ACTION = "commit";
    private static final String NEWLINE = "\n";
    private static final String HITSOUND = "./data/hit_sound.wav";
    private final Inchoate inchoate;
    public JTextField textField;
    protected JButton buttonSave;
    protected JButton buttonLoad;
    protected JButton buttonTake;
    protected JButton buttonDrop;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private List<String> commandWords;
    private Mode mode = Mode.INSERT;

    // EFFECTS: Initalize GUI fields
    public DisplayHandler() {
        super("Inchoate");
        initGUI();
        commandWords = new ArrayList<>();

        textField.getDocument().addDocumentListener(this);
        InputMap im = textField.getInputMap();
        ActionMap am = textField.getActionMap();
        im.put(KeyStroke.getKeyStroke(""), COMMIT_ACTION);
        am.put(COMMIT_ACTION, new CommitAction());

        inchoate = new Inchoate(this);
    }

    // EFFECTS: Wrapper method for init entire game
    public void initGame() {
        initCommands();
        initActionListener();
    }

    // MODIFIES: this, inchoate
    // EFFECTS: Init action listener
    private void initActionListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = textField.getText();
                if (command.equals("1") || command.equals("2") || command.equals("3")) {
                    return;
                }
                print(command, Color.BLACK);
                try {
                    inchoate.processCommand(command.toLowerCase().split(" ", 2));
                } catch (Exception exception) {
                    print(exception.toString().split(": ")[1], Color.RED);
                }
            }
        };
        textField.addActionListener(actionListener);
    }

    // MODIFIES: this
    // EFFECTS: Add words to commandsWords for autocompletion. Sort it after since autocomplete requires it
    private void initCommands() {
        this.commandWords = new ArrayList<>();
        List<String> commandWords = new ArrayList<>();
        commandWords.add("north");
        commandWords.add("south");
        commandWords.add("east");
        commandWords.add("west");
        commandWords.add("inventory");
        commandWords.add("riddle");
        commandWords.add("here");
        commandWords.add("search");
        commandWords.add("take");
        commandWords.add("drop");
        commandWords.add("save");
        commandWords.add("load");
        addCommand(commandWords);
    }

    // EFFECTS: Add a list of commands to command words
    public void addCommand(List<String> commandWords) {
        for (String command : commandWords) {
            addCommand(command);
        }
        // Sorting list : https://howtodoinjava.com/java/sort/sort-arraylist-strings-integers/
        this.commandWords.sort(Comparator.comparing(String::toString));
    }

    // EFFECTS: Add a singular command to command words
    public void addCommand(String command) {
        if (!this.commandWords.contains(command)) {
            this.commandWords.add(command);
            this.commandWords.sort(Comparator.comparing(String::toString));
        }
    }

    // EFFECTS: Wrapper method for initializing the GUI components onto a layout
    private void initGUI() {
        initComponents();
        initLayout();
    }

    // MODIFIES: this
    // EFFECTS: init components
    private void initComponents() {
        textPane = new JTextPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 400));
        textPane.setEditable(false);
        textPane.setBackground(Color.WHITE);
        textPane.setForeground(Color.BLACK);

        scrollPane = new JScrollPane(textPane);

        textField = new JTextField(20);
        textField.addActionListener(this);
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);

        initButtons();
    }

    // MODIFIES: this
    // EFFECTS: Inits all the buttons
    private void initButtons() {
        buttonSave = initButton("Quick Save");
        buttonLoad = initButton("Quick Load");
        buttonDrop = initButton("Drop All");
        buttonTake = initButton("Take All");
    }

    // MODIFIES: this
    // EFFECTS: Init the given button with given name
    private JButton initButton(String name) {
        JButton button = new JButton(name);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        button.setMnemonic(KeyEvent.VK_D);
        button.setActionCommand(name.toLowerCase());
        button.addActionListener(this);
        return button;
    }

    // REQUIRES: Called after init components [Using initGUI() wrapper]
    // MODIFIES: this
    // EFFECTS: inits the layout for components
    private void initLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        horzGroup(layout);
        vertGroup(layout);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: init horizontal group
    private void horzGroup(GroupLayout layout) {
        GroupLayout.ParallelGroup horzGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup h1 = layout.createSequentialGroup();
        GroupLayout.ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        h2.addComponent(buttonSave, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        h2.addComponent(buttonLoad, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        h2.addComponent(buttonTake, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        h2.addComponent(buttonDrop, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        h2.addComponent(textField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        h2.addComponent(scrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);

        h1.addContainerGap();
        h1.addGroup(h2);
        h1.addContainerGap();
        horzGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);
        layout.setHorizontalGroup(horzGroup);
    }

    // MODIFIES: this
    // EFFECTS: init vertical group
    private void vertGroup(GroupLayout layout) {
        GroupLayout.ParallelGroup vertGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup v1 = layout.createSequentialGroup();
        v1.addContainerGap();
        v1.addComponent(textField, GroupLayout.DEFAULT_SIZE, 25, 25);
        v1.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
        v1.addComponent(buttonTake);
        v1.addComponent(buttonDrop);
        v1.addComponent(buttonSave);
        v1.addComponent(buttonLoad);
        v1.addContainerGap();
        vertGroup.addGroup(v1);
        layout.setVerticalGroup(vertGroup);
    }

    // MODIFIES: this
    // EFFECTS: Main action listener
    public void actionPerformed(ActionEvent actionEvent) {
        handleTextField();
        handleButtons(actionEvent);
    }

    // MODIFIES: this
    // EFFECTS: append text in text field to pane
    private void handleTextField() {
        textField.selectAll();
        textPane.setCaretPosition(textPane.getDocument().getLength());
    }

    // EFFECTS: handle buttons presses and play a sound
    private void handleButtons(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        try {
            if (command.equals("quick save")) {
                playSound(HITSOUND);
                inchoate.saveFile("./data/saveFile0.save");
            } else if (command.equals("quick load")) {
                playSound(HITSOUND);
                inchoate.loadFile("./data/saveFile0.save");
            } else if (command.equals("take all")) {
                playSound(HITSOUND);
                takeAll();
            } else if (command.equals("drop all")) {
                playSound(HITSOUND);
                dropAll();
            }
        } catch (Exception exception) {
            print(exception.toString().split(": ")[1], Color.RED);
        }
    }

    // MODIFIES: inchoate, player
    // EFFECTS: Take all items from room
    private void takeAll() {
        List<Item> items = inchoate.player.getMap().getCurrentRoom().getInventory().getItems();
        if (items.size() == 0) {
            print("There are no items in this room", Color.BLUE);
        } else {
            for (int i = items.size() - 1; i >= 0; i--) {
                inchoate.take(items.get(i).getName());
            }
        }
    }

    // MODIFIES: inchoate, player
    // EFFECTS: Drop all items from inventory
    private void dropAll() {
        List<Item> items = inchoate.player.getInventory().getItems();
        if (items.size() == 0) {
            print("There are no items in your inventory", Color.BLUE);
        } else {
            for (int i = items.size() - 1; i >= 0; i--) {
                inchoate.drop(items.get(i).getName());
            }
        }
    }

    // Source : https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
    // EFFECTS: play the given sound file in a new thread
    private void playSound(final String url) {
        try {
            File file = new File(url);
            // Clip clip = AudioSystem.getClip();
            // AudioInputStream inputStream = AudioSystem.getAudioInputStream(
            //         Main.class.getResourceAsStream(file.getAbsolutePath()));
            // clip.open(inputStream);
            // clip.start();
            FileInputStream is = new FileInputStream(file);
            AudioStream audioStream = new AudioStream(is);
            AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
            System.out.println(e);
            System.err.println(e.getMessage());
        }
    }

    // EFFECTS: Do something on change
    public void changedUpdate(DocumentEvent ev) {
        // implements DocumentListener
        // nothing is here intentionally
    }

    // EFFECTS: Do something on remove
    public void removeUpdate(DocumentEvent ev) {
        // implements DocumentListener
        // nothing is here intentionally
    }

    // EFFECTS: Auto complete on insert
    public void insertUpdate(DocumentEvent ev) {
        autoComplete(ev);
    }

    // EFFECTS: Auto complete and change mode accordingly
    private void autoComplete(DocumentEvent ev) {
        if (ev.getLength() != 1) {
            return;
        }
        int pos = ev.getOffset();
        String content = null;
        try {
            content = textField.getText(0, pos + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        int w;
        for (w = pos; w >= 0; w--) {
            if (!Character.isLetter(content.charAt(w))) {
                break;
            }
        }
        if (pos - w < 1) {
            return;
        }
        updateCompletion(content, w, pos);
    }

    // MODIFIES: this
    // EFFECTS: Update mode
    private void updateCompletion(String content, int w, int pos) {
        String prefix = content.substring(w + 1).toLowerCase();
        int n = Collections.binarySearch(commandWords, prefix);
        if (n < 0 && -n - 1 < commandWords.size()) {
            String match = commandWords.get(-n - 1);
            if (match.startsWith(prefix)) {
                String completion = match.substring(pos - w);
                SwingUtilities.invokeLater(
                        new CompletionTask(completion, pos + 1));
            }
        } else {
            mode = Mode.INSERT;
        }
    }

    // EFFECTS: Print the given text to the textPane in the given color
    //          Also print it to System.out
    public void print(String str, Color c) {
        System.out.println(str);
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setItalic(attributeSet, true);
        StyleConstants.setForeground(attributeSet, c);
        StyleConstants.setBackground(attributeSet, textPane.getBackground());
        Document doc = textPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), str + NEWLINE, attributeSet);
        } catch (Exception e) {
            // do nothing
        }
    }

    // EFFECTS: Return this.textField
    public JTextField getTextField() {
        return textField;
    }

    private enum Mode { INSERT, COMPLETION }

    // EFFECTS: This inner class is for handling the completion task
    private class CompletionTask implements Runnable {
        String completion;
        int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        public void run() {
            textField.setText(textField.getText() + completion);
            // textField.insert(completion, position);
            textField.setCaretPosition(position + completion.length());
            textField.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }

    // EFFECTS: This inner class is for the actual completion action
    private class CommitAction extends AbstractAction {
        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = textField.getSelectionEnd();
                // textField.insert(" ", pos);
                textField.setText(textField.getText() + " ");
                textField.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            } else {
                textField.replaceSelection("\n");
            }
        }
    }


}
