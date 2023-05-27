import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    JTextArea textField = new JTextArea();

    public Frame(){
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Assembly Constructor");
        this.setLocationRelativeTo(null);
        textField.setBackground(Color.BLACK);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textField.setForeground(Color.GREEN);
        textField.setCaretColor(Color.WHITE);
        this.add(textField);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Frame();
    }

}
