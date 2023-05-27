import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Frame extends JFrame implements KeyListener {

    JTextArea textField = new JTextArea(500, 500);
    JScrollPane scrollPane = new JScrollPane(textField, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    public Frame(){
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setTitle("Assembly Constructor");
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        textField.setBackground(Color.BLACK);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        textField.setForeground(Color.GREEN);
        textField.setCaretColor(Color.WHITE);
        textField.setLineWrap(true);
        textField.addKeyListener(this);
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
            String[] instructions = textField.getText().split("\n");
            System.out.println(Arrays.deepToString(instructions));
            try {
                PrintWriter writer = new PrintWriter(new FileWriter("Program.txt"));
                for(int i = 0; i < instructions.length - 1; i++){
                    writer.println(instructions[i]);
                }
                writer.print(instructions[instructions.length - 1]);
                writer.close();
                Program.startPipelinedProgram();
                JTextArea text = new JTextArea();
                text.setEditable(false);
                text.setText(Program.guiOutput.toString());
                text.setBackground(Color.BLACK);
                text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
                text.setForeground(Color.GREEN);
                JFrame frame = new JFrame();
                frame.setSize(500, 500);
                frame.setTitle("Output");
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.add(text);
                frame.setVisible(true);
            } catch (IOException ignored) {}
        }
        Program.guiOutput = new StringBuilder();
        Program.numberOfInstructions = 0;
        Program.instructions = new Instruction[3];
        Program.pc = 0;
        Program.sreg = 0;
        Program.instructionsCounter = 0;
    }
}
