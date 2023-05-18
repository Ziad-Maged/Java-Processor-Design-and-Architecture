public class Instruction {
    private short instruction;
    private byte opcode;
    private byte destination;
    private byte sourceImmediate;

    public Instruction(short instruction){
        this.instruction = instruction;
        String binary = Integer.toBinaryString(instruction);
        if(binary.length() > 16)
            binary = binary.substring(16);
        else if(binary.length() < 16){
            while(binary.length() < 16)
                binary = "0" + binary;
        }
        opcode = Byte.parseByte(binary.substring(0, 4), 2);
        destination = Byte.parseByte(binary.substring(4, 10), 2);
        sourceImmediate = Byte.parseByte(binary.substring(10), 2);
    }

    public byte getDestination() {
        return destination;
    }

    public byte getOpcode() {
        return opcode;
    }

    public short getInstruction() {
        return instruction;
    }

    public byte getSourceImmediate() {
        return sourceImmediate;
    }

    public void setInstruction(short instruction) {
        this.instruction = instruction;
    }

    public void setDestination(byte destination) {
        this.destination = destination;
    }

    public void setOpcode(byte opcode) {
        this.opcode = opcode;
    }

    public void setSourceImmediate(byte sourceImmediate) {
        this.sourceImmediate = sourceImmediate;
    }
}
