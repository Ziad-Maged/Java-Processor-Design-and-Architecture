
public class Program {
    short[] instructionMemory = new short[1024];
    byte[] dataMemory = new byte[2048];
    byte[] registers = new byte[64];
    static short pc = 0;
    static byte sreg = 0;
    static int clockCycle = 0;

    public void fetch(){
        decode(instructionMemory[pc]);
        clockCycle++;
        pc++;
    }

    public void decode(short instruction){
        String binary = Integer.toBinaryString(instruction);
        if(binary.length() > 16)
            binary = binary.substring(16);
        else if(binary.length() < 16){
            while(binary.length() < 16)
                binary = "0" + binary;
        }
        byte opcode = Byte.parseByte(binary.substring(0, 4), 2);
        byte destination = Byte.parseByte(binary.substring(4, 10), 2);
        byte sourceImmediate = Byte.parseByte(binary.substring(10), 2);
        execute(opcode, destination, sourceImmediate);
    }

    public void execute(byte opcode, byte destination, byte sourceImmediate){
        //TODO LATER
    }

    public static void main(String[] args) {
        //TODO Later
    }
}