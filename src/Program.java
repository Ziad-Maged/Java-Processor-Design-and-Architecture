import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Program {
    static short[] instructionMemory = new short[1024];
    static byte[] dataMemory = new byte[2048];
    static byte[] registers = new byte[64];
    static short pc = 0;
    static byte sreg = 0;
    static int numberOfInstructions = 0;
    static int clockCycle = 0;
    static int instructionsCounter = 0;
    static Instruction[] instructions = new Instruction[3];

    public void fetch(){
        if(instructionsCounter < numberOfInstructions){
            if(instructions[0] == null){
                instructions[0] = new Instruction(instructionMemory[pc++]);
                instructions[0].instructionNumber = ++instructionsCounter;
            }else if(instructions[1] == null){
                instructions[1] = new Instruction(instructionMemory[pc++]);
                instructions[1].instructionNumber = ++instructionsCounter;
            }else if(instructions[2] == null){
                instructions[2] = new Instruction(instructionMemory[pc++]);
                instructions[2].instructionNumber = ++instructionsCounter;
            }else if (!instructions[0].running){
                instructions[0] = instructions[1];
                instructions[1] = instructions[2];
                instructions[2] = new Instruction(Program.instructionMemory[pc++]);
                instructions[2].instructionNumber = ++instructionsCounter;
            }
        }
    }

    public static void load(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        while(br.ready()){
            String[] s = br.readLine().split(" ");
            String binary = "";
            switch (s[0]){
                case "ADD" -> binary += "0000";
                case "SUB" -> binary += "0001";
                case "MUL" -> binary += "0010";
                case "MOVI" -> binary += "0011";
                case "BEQZ" -> binary += "0100";
                case "ANDI" -> binary += "0101";
                case "EOR" -> binary += "0110";
                case "BR" -> binary += "0111";
                case "SAL" -> binary += "1000";
                case "SAR" -> binary += "1001";
                case "LDR" -> binary += "1010";
                case "STR" -> binary += "1011";
            }
            StringBuilder r1Binary = new StringBuilder(Integer.toBinaryString(Integer.parseInt(s[1].substring(1))));
            while(r1Binary.length() < 6)
                r1Binary.insert(0, "0");
            StringBuilder r2Binary;
            if(s[2].contains("R"))
                r2Binary = new StringBuilder(Integer.toBinaryString(Integer.parseInt(s[2].substring(1))));
            else
                r2Binary = new StringBuilder(Integer.toBinaryString(Integer.parseInt(s[2])));
            if(r2Binary.length() > 6)
                r2Binary = new StringBuilder(r2Binary.substring(26));
            while(r2Binary.length() < 6)
                r2Binary.insert(0, "0");
            binary += r1Binary + r2Binary.toString();
            instructionMemory[numberOfInstructions++] = (short) Integer.parseInt(binary, 2);
        }
    }

    public static void main(String[] args) {
        try {
            Program.load("test.txt");
            Program.clockCycle = 3 + (numberOfInstructions - 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}