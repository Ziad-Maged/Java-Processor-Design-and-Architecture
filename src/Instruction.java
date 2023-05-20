public class Instruction {
    private final short instruction;
    private byte opcode;
    private byte r1;
    private byte r2Immediate;
    private boolean running;
    private int currentClockCycle;
    private int instructionNumber;
    private String toBePrinted;

    public Instruction(short instruction){
        this.instruction = instruction;
        this.running = true;
        currentClockCycle = 1;
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
        StringBuilder sregBinary = new StringBuilder(Integer.toBinaryString(Program.sreg));
        while(sregBinary.length() < 8)
            sregBinary.insert(0, "0");
        switch(opcode){
            case 0 ->{
                // The Carry BIT
                int temp1 = Program.registers[r1] & 0x000000FF;
                int temp2 = Program.registers[r2Immediate] & 0x000000FF;
                if(((temp1 + temp2) & 0b100000000) == 0b100000000){
                    sregBinary.setCharAt(7, '1');
                }else{
                    sregBinary.setCharAt(7, '0');
                }
                //The Overflow BIT
                if(Program.registers[r1] < 0 && Program.registers[r2Immediate] < 0){
                    if((byte)(Program.registers[r1] + Program.registers[r2Immediate]) >= 0)
                        sregBinary.setCharAt(6, '1');
                    else
                        sregBinary.setCharAt(6, '0');
                }else if(Program.registers[r1] > 0 && Program.registers[r2Immediate] > 0){
                    if((byte)(Program.registers[r1] + Program.registers[r2Immediate]) <= 0)
                        sregBinary.setCharAt(6, '1');
                    else
                        sregBinary.setCharAt(6, '0');
                }
                //The Negative BIT
                if((byte)(Program.registers[r1] + Program.registers[r2Immediate]) < 0)
                    sregBinary.setCharAt(5, '1');
                else
                    sregBinary.setCharAt(5, '0');
                //The Sign BIT
                if((sregBinary.charAt(5) == '1' && sregBinary.charAt(6) == '0')
                        || (sregBinary.charAt(5) == '0' && sregBinary.charAt(6) == '1'))
                    sregBinary.setCharAt(4, '1');
                else
                    sregBinary.setCharAt(4, '0');
                //The Zero BIT
                if((byte)(Program.registers[r1] + Program.registers[r2Immediate]) == 0)
                    sregBinary.setCharAt(3, '1');
                else
                    sregBinary.setCharAt(3, '0');
                Program.registers[r1] = (byte)(Program.registers[r1] + Program.registers[r2Immediate]);
                toBePrinted = "R" + r1 + "=" + Program.registers[r1];
            }
            case 1 ->{
                //The Overflow BIT
                if(Program.registers[r1] < 0 && Program.registers[r2Immediate] > 0){
                    if((byte)(Program.registers[r1] - Program.registers[r2Immediate]) > 0)
                        sregBinary.setCharAt(6, '1');
                    else
                        sregBinary.setCharAt(6, '0');
                }else if(Program.registers[r1] > 0 && Program.registers[r2Immediate] < 0){
                    if((byte)(Program.registers[r1] - Program.registers[r2Immediate]) < 0)
                        sregBinary.setCharAt(6, '1');
                    else
                        sregBinary.setCharAt(6, '0');
                }else{
                    sregBinary.setCharAt(6, '0');
                }
                //The Negative BIT
                if((byte)(Program.registers[r1] - Program.registers[r2Immediate]) < 0)
                    sregBinary.setCharAt(5, '1');
                else
                    sregBinary.setCharAt(5, '0');
                //The Sign BIT
                if((sregBinary.charAt(5) == '1' && sregBinary.charAt(6) == '0')
                        || (sregBinary.charAt(5) == '0' && sregBinary.charAt(6) == '1'))
                    sregBinary.setCharAt(4, '1');
                else
                    sregBinary.setCharAt(4, '0');
                //The Zero BIT
                if((byte)(Program.registers[r1] - Program.registers[r2Immediate]) == 0)
                    sregBinary.setCharAt(3, '1');
                else
                    sregBinary.setCharAt(3, '0');
                Program.registers[r1] = (byte)(Program.registers[r1] - Program.registers[r2Immediate]);
                toBePrinted = "R" + r1 + "=" + Program.registers[r1];
            }
            case 2 ->{
                //The Negative BIT
                if((byte)(Program.registers[r1] * Program.registers[r2Immediate]) < 0)
                    sregBinary.setCharAt(5, '1');
                else
                    sregBinary.setCharAt(5, '0');
                //The Zero BIT
                if((byte)(Program.registers[r1] * Program.registers[r2Immediate]) == 0)
                    sregBinary.setCharAt(3, '1');
                else
                    sregBinary.setCharAt(3, '0');
                Program.registers[r1] = (byte)(Program.registers[r1] * Program.registers[r2Immediate]);
                toBePrinted = "R" + r1 + "=" + Program.registers[r1];
            }
            case 3 ->{
                Program.registers[r1] = r2Immediate;
                toBePrinted = "R" + r1 + "=" + r2Immediate;
            }
            case 4 ->{
                if(Program.registers[r1] == 0){
                    Program.pc = (short)(Program.pc + 1 + r2Immediate);
                    toBePrinted = "PC=" + Program.pc;
                }
            }
            case 5 ->{
                //The Negative BIT
                if((byte)(Program.registers[r1] & Program.registers[r2Immediate]) < 0)
                    sregBinary.setCharAt(5, '1');
                else
                    sregBinary.setCharAt(5, '0');
                //The Zero BIT
                if((byte)(Program.registers[r1] & Program.registers[r2Immediate]) == 0)
                    sregBinary.setCharAt(3, '1');
                else
                    sregBinary.setCharAt(3, '0');
                Program.registers[r1] = (byte) (Program.registers[r1] & r2Immediate);
                toBePrinted = "R" + r1 + "=" + Program.registers[r1];
            }
            case 6 ->{
                //The Negative BIT
                if((byte)(Program.registers[r1] ^ Program.registers[r2Immediate]) < 0)
                    sregBinary.setCharAt(5, '1');
                else
                    sregBinary.setCharAt(5, '0');
                //The Zero BIT
                if((byte)(Program.registers[r1] ^ Program.registers[r2Immediate]) == 0)
                    sregBinary.setCharAt(3, '1');
                else
                    sregBinary.setCharAt(3, '0');
                Program.registers[r1] = (byte)(Program.registers[r1] ^ Program.registers[r2Immediate]);
                toBePrinted = "R" + r1 + "=" + Program.registers[r1];
            }
            case 7 ->{
                StringBuilder destBinary = new StringBuilder(Integer.toBinaryString(r1));
                StringBuilder srcBinary = new StringBuilder(Integer.toBinaryString(r2Immediate));
                while (destBinary.length() < 6)
                    destBinary.insert(0, "0");
                while (srcBinary.length() < 6)
                    srcBinary.insert(0, "0");
                Program.pc = Short.parseShort(destBinary + srcBinary.toString(), 2);
                toBePrinted = "PC=" + Program.pc;
            }
            case 8 ->{
                //The Negative BIT
                if((byte)(Program.registers[r1] << Program.registers[r2Immediate]) < 0)
                    sregBinary.setCharAt(5, '1');
                else
                    sregBinary.setCharAt(5, '0');
                //The Zero BIT
                if((byte)(Program.registers[r1] << Program.registers[r2Immediate]) == 0)
                    sregBinary.setCharAt(3, '1');
                else
                    sregBinary.setCharAt(3, '0');
                Program.registers[r1] = (byte)(Program.registers[r1] << r2Immediate);
                toBePrinted = "R" + r1 + "=" + Program.registers[r1];
            }
            case 9 ->{
                //The Negative BIT
                if((byte)(Program.registers[r1] >> Program.registers[r2Immediate]) < 0)
                    sregBinary.setCharAt(5, '1');
                else
                    sregBinary.setCharAt(5, '0');
                //The Zero BIT
                if((byte)(Program.registers[r1] >> Program.registers[r2Immediate]) == 0)
                    sregBinary.setCharAt(3, '1');
                else
                    sregBinary.setCharAt(3, '0');
                Program.registers[r1] = (byte)(Program.registers[r1] >> r2Immediate);
                toBePrinted = "R" + r1 + "=" + Program.registers[r1];
            }
            case 10 ->{
                Program.registers[r1] = Program.dataMemory[r2Immediate];
                toBePrinted = "R" + r1 + "=" + Program.registers[r1];
            }
            case 11 ->{
                Program.dataMemory[r2Immediate] = Program.registers[r1];
                toBePrinted = "DataMemory[" + r2Immediate + "]=" + Program.dataMemory[r2Immediate];
            }
        }
        Program.sreg = (byte) Integer.parseInt(sregBinary.toString(), 2);
    }

    public short getInstruction() {
        return instruction;
    }

    public byte getOpcode() {
        return opcode;
    }

    public byte getR1() {
        return r1;
    }

    public byte getR2Immediate() {
        return r2Immediate;
    }

    public boolean isRunning() {
        return running;
    }

    public int getInstructionNumber() {
        return instructionNumber;
    }

    public int getCurrentClockCycle() {
        return currentClockCycle;
    }

    public void setCurrentClockCycle(int currentClockCycle) {
        this.currentClockCycle = currentClockCycle;
    }

    public void setInstructionNumber(int instructionNumber) {
        this.instructionNumber = instructionNumber;
    }

    public String getToBePrinted() {
        return toBePrinted;
    }

    public void start(){
        if(currentClockCycle == 1){
            decode();
        }else if(currentClockCycle == 2){
            execute();
        }else if(currentClockCycle == 3){
            running = false;
        }
        currentClockCycle++;
    }

}
