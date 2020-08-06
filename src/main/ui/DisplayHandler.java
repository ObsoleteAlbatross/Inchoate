// Sources:
// JPanel base - https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// JTextArea text stuff - https://stackoverflow.com/questions/9650992/how-to-change-text-color-in-the-jtextarea
// JTextPane - https://www.javatpoint.com/java-jtextpane


package ui;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class DisplayHandler extends JPanel implements ActionListener {

    private static final String newline = "\n";
    protected static JTextPane textArea;
    // protected JTextArea textArea;
    protected static JTextField textField;
    private static Inchoate inchoate;
    public JFrame frame;
    protected JButton b1, b2, b3;
    private boolean isStartingPhase;
    private boolean isQuittingPhase;

    public DisplayHandler(String nothing) {
    }

    public DisplayHandler() {
        super(new GridBagLayout());
        isStartingPhase = true;

        // Text input field
        textField = new JTextField(20);
        textField.addActionListener(this);
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLUE);

        // Text display area
        // textArea = new JTextArea(5, 20);
        textArea = new JTextPane();
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);

        inchoate = new Inchoate(this);

        b1 = new JButton("Disable middle button");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("disable");

        b2 = new JButton("Middle button");
        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.setMnemonic(KeyEvent.VK_M);

        b3 = new JButton("Enable middle button");
        //Use the default text position of CENTER, TRAILING (RIGHT).
        b3.setMnemonic(KeyEvent.VK_E);
        b3.setActionCommand("enable");
        b3.setEnabled(false);

        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(this);
        b3.addActionListener(this);

        b1.setToolTipText("Click this button to disable the middle button.");
        b2.setToolTipText("This middle button does nothing when you click it.");
        b3.setToolTipText("Click this button to enable the middle button.");

        //Add Components to this container, using the default FlowLayout.
        add(b1);
        add(b2);
        add(b3);
    }

    // EFFECTS: Sets up the display window for the game
    public void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Inchoate");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(800, 800));

        //Create and set up the content pane.
        DisplayHandler newContentPane = new DisplayHandler();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        starting();

        //Display the window.
        frame.pack();
        frame.setVisible(true);


    }

    private void starting() {
        print("Welcome...");
        print("Would you line to start a `new` game or `load` from existing?");
        isStartingPhase = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = textField.getText();
        inchoate.print(text, textField.getForeground());
        textField.selectAll();
        textArea.setCaretPosition(textArea.getDocument().getLength());
        try {
            if ("disable".equals(e.getActionCommand())) {
                b2.setEnabled(false);
                b1.setEnabled(false);
                b3.setEnabled(true);
            } else if ("enable".equals(e.getActionCommand())){
                b2.setEnabled(true);
                b1.setEnabled(true);
                b3.setEnabled(false);
            } else if (isStartingPhase && text.toLowerCase().split(" ", 2)[0].equals("new")) {
                print("You find yourself in Bilgewater. "
                        + "You must go save the princess from the evil clutches of the Noxian empire!");
                isStartingPhase = false;
            } else if (isQuittingPhase && text.toLowerCase().split(" ", 2)[0].equals("yes")) {
                quit();
            } else if (isQuittingPhase) {
                isQuittingPhase = false;
            } else {
                inchoate.processCommand(text.toLowerCase().split(" ", 2));
            }
        } catch (Exception exception) {
            if (exception.toString().split(": ").length > 1) {
                print(exception.toString().split(": ")[1], Color.RED);
            } else {
                print(exception.toString());
            }
        }
    }

    public void quittingPhase() {
        print("Are you sure you want to quit?");
        isQuittingPhase = true;
    }

    public void quit() {
        System.exit(0);
    }

    // public void print(String str) {
    //     textArea.append(str + newline);
    // }

    public void print(String str) {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setItalic(attributeSet, true);
        StyleConstants.setForeground(attributeSet, textArea.getForeground());
        StyleConstants.setBackground(attributeSet, textArea.getBackground());
        Document doc = textArea.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), str + newline, attributeSet);
        } catch (Exception e) {
            // do nothing
        }
    }

    public void print(String str, Color c) {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setItalic(attributeSet, true);
        StyleConstants.setForeground(attributeSet, c);
        StyleConstants.setBackground(attributeSet, textArea.getBackground());
        Document doc = textArea.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), str + newline, attributeSet);
        } catch (Exception e) {
            // do nothing
        }
    }
}
