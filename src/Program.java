
public class Program {
    Instruction[] instructionMemory = new Instruction[1024];
    Data[] dataMemory = new Data[2048];
    byte[] registers = new byte[64];
    static short pc = 0;
    static byte sreg = 0;

    public static void main(String[] args) {
        String[] test = Integer.toBinaryString(15).split("");
        for (String s : test) {
            System.out.println(s);
        }
    }
}
