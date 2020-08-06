// Sources
// Autocompletion based on :
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextAreaDemoProject/src/components/TextAreaDemo.java


package ui;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DisplayHandler extends JFrame implements DocumentListener, ActionListener {

    private static final String COMMIT_ACTION = "commit";
    private static final String newline = "\n";
    public JTextField textField;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private List<String> commandWords;
    private Mode mode = Mode.INSERT;
    private final Inchoate inchoate;

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
    }

    // REQUIRES: Called after init components [Using initGUI() wrapper]
    // MODIFIES: this
    // EFFECTS: inits the layout for components
    private void initLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        //Create a parallel group for the horizontal axis
        GroupLayout.ParallelGroup horzGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        //Create a sequential and a parallel groups
        GroupLayout.SequentialGroup h1 = layout.createSequentialGroup();
        GroupLayout.ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        //Add a scroll panel and a label to the parallel group h2
        h2.addComponent(textField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        h2.addComponent(scrollPane, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);

        //Add a container gap to the sequential group h1
        h1.addContainerGap();
        // Add the group h2 to the group h1
        h1.addGroup(h2);
        h1.addContainerGap();
        //Add the group h1 to horzGroup
        horzGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);
        //Create the horizontal group
        layout.setHorizontalGroup(horzGroup);

        //Create a parallel group for the vertical axis
        GroupLayout.ParallelGroup vertGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        //Create a sequential group
        GroupLayout.SequentialGroup v1 = layout.createSequentialGroup();
        //Add a container gap to the sequential group v1
        v1.addContainerGap();
        // Add text field
        v1.addComponent(textField, GroupLayout.DEFAULT_SIZE, 25, 25);
        v1.addContainerGap();
        //Add scroll panel to the sequential group v1
        v1.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
        v1.addContainerGap();
        //Add the group v1 to vertGroup
        vertGroup.addGroup(v1);
        //Create the vertical group
        layout.setVerticalGroup(vertGroup);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: Append the text in field to textPane
    public void actionPerformed(ActionEvent actionEvent) {
        String command = textField.getText();
        // print(command, Color.RED);
        // textArea.append(text + newline);
        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textPane.setCaretPosition(textPane.getDocument().getLength());
    }


    // Listeners

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
            doc.insertString(doc.getLength(), str + newline, attributeSet);
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
