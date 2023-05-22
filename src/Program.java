import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

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

    public static void fetch(){
        if(instructionsCounter < numberOfInstructions){
            if(instructions[0] == null){
                instructions[0] = new Instruction(instructionMemory[pc++]);
                instructions[0].setInstructionNumber(++instructionsCounter);
            }else if(instructions[1] == null){
                instructions[1] = new Instruction(instructionMemory[pc++]);
                instructions[1].setInstructionNumber(++instructionsCounter);
            }else if(instructions[2] == null){
                instructions[2] = new Instruction(instructionMemory[pc++]);
                instructions[2].setInstructionNumber(++instructionsCounter);
            }else if (!instructions[0].isRunning()){
                instructions[0] = instructions[1];
                instructions[1] = instructions[2];
                instructions[2] = new Instruction(Program.instructionMemory[pc++]);
                instructions[2].setInstructionNumber(++instructionsCounter);
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
            switch (s[0]){
                case "ADD", "SUB", "MUL", "EOR", "BR" ->{
                    if(!s[2].contains("R")){
                        System.out.println("Error in Instruction " + numberOfInstructions + 1 + " Expected a register between R0 and R63 but received Immediate");
                        numberOfInstructions = -1;
                        return;
                    }
                }
                default -> {
                    if(s[2].contains("R")){
                        System.out.println("Error in Instruction " + numberOfInstructions + 1 + " Expected an Immediate but received a Register");
                        numberOfInstructions = -1;
                        return;
                    }
                }
            }
            if(s[2].contains("R"))
                r2Binary = new StringBuilder(Integer.toBinaryString(Integer.parseInt(s[2].substring(1))));
            else
                r2Binary = new StringBuilder(Integer.toBinaryString(Integer.parseInt(s[2])));
            if(r2Binary.length() > 6)
                r2Binary = new StringBuilder(r2Binary.substring(r2Binary.length() - 6));
            while(r2Binary.length() < 6)
                r2Binary.insert(0, "0");
            binary += r1Binary + r2Binary.toString();
            instructionMemory[numberOfInstructions++] = (short) Integer.parseInt(binary, 2);
        }
    }

    public static void startPipelinedProgram(){
        try {
            Program.load("test.txt");
            if(numberOfInstructions == -1)
                return;
            Program.clockCycle = 3 + (numberOfInstructions - 1);
            for(int i = 1; i <= Program.clockCycle; i++){
                for(Instruction e : instructions){
                    if(e != null && e.isRunning())
                        e.start();
                }
                Program.fetch();
                System.out.println("Clock Cycle " + i + ": ");
                for(Instruction e : instructions){
                    if(e != null && e.isRunning()){
                        if(e.getCurrentClockCycle() == 1){
                            System.out.println("Instruction " + e.getInstructionNumber() + "(Fetch) (No Parameters)");
                        }else if(e.getCurrentClockCycle() == 2){
                            System.out.println("Instruction " + e.getInstructionNumber() + "(Decode) (instruction=" + e.getInstruction() + ")");
                        }else if(e.getCurrentClockCycle() == 3){
                            System.out.println("Instruction " + e.getInstructionNumber() + "(Execute) (opcode=" + e.getOpcode() + ", R1=" + e.getR1() + ", R2/Immediate=" + e.getR2Immediate() + ")");
                            System.out.println(e.getToBePrinted());
                        }
                    }
                }
            }
            System.out.println("Instruction Memory: " + Arrays.toString(instructionMemory));
            System.out.println("Data Memory: " + Arrays.toString(dataMemory));
            System.out.println("Register File: " + Arrays.toString(registers));
            System.out.println("Status Register: " + Program.sreg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Program.startPipelinedProgram();
    }
}