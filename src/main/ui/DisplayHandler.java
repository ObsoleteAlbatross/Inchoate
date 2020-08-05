// Sources:
// JPanel base - https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// JTextArea text stuff - https://stackoverflow.com/questions/9650992/how-to-change-text-color-in-the-jtextarea
// JTextPane - https://www.javatpoint.com/java-jtextpane


package ui;

import sun.java2d.pipe.TextPipe;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayHandler extends JPanel implements ActionListener {

    private static final String newline = "\n";
    protected static JTextPane textArea;
    // protected JTextArea textArea;
    protected static JTextField textField;
    private static Inchoate inchoate;

    public DisplayHandler() {
        super(new GridBagLayout());

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
    }

    // EFFECTS: Sets up the display window for the game
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Inchoate");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(800,800));

        //Create and set up the content pane.
        DisplayHandler newContentPane = new DisplayHandler();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = textField.getText();
        inchoate.print(text, textField.getForeground());
        // textArea.append(text + newline);
        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());

        try {
            inchoate.processCommand(text.toLowerCase().split(" ", 2));
        } catch (IllegalArgumentException iae) {
            print(iae.toString().split(": ")[1], Color.RED);
        }
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
