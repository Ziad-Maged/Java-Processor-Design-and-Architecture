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
        String[] sregBinary = Integer.toBinaryString(sreg).split("");
        switch(opcode){
            case 0 ->{
                registers[destination] = (byte)(registers[destination] + registers[sourceImmediate]);
            }
            case 1 ->{
                registers[destination] = (byte)(registers[destination] - registers[sourceImmediate]);
            }
            case 2 ->{
                registers[destination] = (byte)(registers[destination] * registers[sourceImmediate]);
            }
            case 3 ->{
                registers[destination] = sourceImmediate;
            }
            case 4 ->{
                if(registers[destination] == 0)
                    pc = (short)(pc + 1 + sourceImmediate);
            }
            case 5 ->{
                registers[destination] = (byte) (registers[destination] & sourceImmediate);
            }
            case 6 ->{
                registers[destination] = (byte)(registers[destination] ^ registers[sourceImmediate]);
            }
            case 7 ->{
                String destBinary = Integer.toBinaryString(destination);
                String srcBinary = Integer.toBinaryString(sourceImmediate);
                while (destBinary.length() < 6)
                    destBinary = "0" + destBinary;
                while (srcBinary.length() < 6)
                    srcBinary = "0" + srcBinary;
                pc = Short.parseShort(destBinary + srcBinary, 2);
            }
            case 8 ->{
                registers[destination] = (byte)(registers[destination] << sourceImmediate);
            }
            case 9 ->{
                registers[destination] = (byte)(registers[destination] >> sourceImmediate);
            }
            case 10 ->{
                registers[destination] = dataMemory[sourceImmediate];
            }
            case 11 ->{
                dataMemory[sourceImmediate] = registers[destination];
            }
        }
    }

    public static void load(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int i = 0;
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
            String r1Binary = Integer.toBinaryString(Integer.parseInt(s[1].substring(1)));
            while(r1Binary.length() < 6)
                r1Binary = "0" + r1Binary;
            String r2Binary;
            if(s[2].contains("R"))
                r2Binary = Integer.toBinaryString(Integer.parseInt(s[2].substring(1)));
            else
                r2Binary = Integer.toBinaryString(Integer.parseInt(s[2]));
            if(r2Binary.length() > 6)
                r2Binary = r2Binary.substring(26);
            while(r2Binary.length() < 6)
                r2Binary = "0" + r2Binary;
            binary += r1Binary + r2Binary;
            instructionMemory[i] = (short) Integer.parseInt(binary, 2);
            i++;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        byte b1 = sc.nextByte();
        byte b2 = sc.nextByte();

        if(b1 < 0 && b2 < 0){
            if((byte)(b1 + b2) >= 0)
                System.out.println("Overflow");
            else
                System.out.println("No Overflow");
        }else if(b1 > 0 && b2 > 0){
            if((byte)(b1 + b2) <= 0)
                System.out.println("Overflow");
            else
                System.out.println("No Overflow");
        }

        System.out.println((byte)(b1 + b2));

    }
}