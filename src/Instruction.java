public class Instruction {
    short instruction;
    byte opcode;
    byte r1;
    byte r2Immediate;
    boolean running;
    int currentClockCycle;

    public Instruction(short instruction){
        this.instruction = instruction;
        this.running = true;
        currentClockCycle = 2;
    }

    public void decode(){
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(instruction));
        if(binary.length() > 16)
            binary = new StringBuilder(binary.substring(16));
        else if(binary.length() < 16){
            while(binary.length() < 16)
                binary.insert(0, "0");
        }
        opcode = Byte.parseByte(binary.substring(0, 4), 2);
        r1 = Byte.parseByte(binary.substring(4, 10), 2);
        r2Immediate = Byte.parseByte(binary.substring(10), 2);
    }

    public void execute(){
        String[] sregBinary = Integer.toBinaryString(Program.sreg).split("");
        switch(opcode){
            case 0 ->{
                Program.registers[r1] = (byte)(Program.registers[r1] + Program.registers[r2Immediate]);
            }
            case 1 ->{
                Program.registers[r1] = (byte)(Program.registers[r1] - Program.registers[r2Immediate]);
            }
            case 2 ->{
                Program.registers[r1] = (byte)(Program.registers[r1] * Program.registers[r2Immediate]);
            }
            case 3 ->{
                Program.registers[r1] = r2Immediate;
            }
            case 4 ->{
                if(Program.registers[r1] == 0)
                    Program.pc = (short)(Program.pc + 1 + r2Immediate);
            }
            case 5 ->{
                Program.registers[r1] = (byte) (Program.registers[r1] & r2Immediate);
            }
            case 6 ->{
                Program.registers[r1] = (byte)(Program.registers[r1] ^ Program.registers[r2Immediate]);
            }
            case 7 ->{
                String destBinary = Integer.toBinaryString(r1);
                String srcBinary = Integer.toBinaryString(r2Immediate);
                while (destBinary.length() < 6)
                    destBinary = "0" + destBinary;
                while (srcBinary.length() < 6)
                    srcBinary = "0" + srcBinary;
                Program.pc = Short.parseShort(destBinary + srcBinary, 2);
            }
            case 8 ->{
                Program.registers[r1] = (byte)(Program.registers[r1] << r2Immediate);
            }
            case 9 ->{
                Program.registers[r1] = (byte)(Program.registers[r1] >> r2Immediate);
            }
            case 10 ->{
                Program.registers[r1] = Program.dataMemory[r2Immediate];
            }
            case 11 ->{
                Program.dataMemory[r2Immediate] = Program.registers[r1];
            }
        }
    }

    public void start(){
        //TODO
    }

}
