public class Instruction {
    private short instruction;
    private byte opcode;
    private byte destination;
    private byte sourceImmediate;

    public Instruction(){

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
