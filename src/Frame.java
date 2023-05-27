import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements KeyListener {

    JTextArea textField = new JTextArea(500, 500);
    JScrollPane scrollPane = new JScrollPane(textField, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    public Frame(){
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Assembly Constructor");
        this.setLocationRelativeTo(null);
        textField.setBackground(Color.BLACK);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textField.setForeground(Color.GREEN);
        textField.setCaretColor(Color.WHITE);
        textField.setLineWrap(true);
        this.add(scrollPane);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Frame();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            //TODO
        }
    }
}
