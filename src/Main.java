import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        byte t1 = sc.nextByte();
        byte t2 = sc.nextByte();
        if(t1 < 0 && t2 < 0){
            if((byte)(t1 + t2) >= 0)
                System.out.println("Carry");
            else
                System.out.println("No Carry");
        }else if(t1 > 0 && t2 > 0){
            if((byte)(t1 + t2) <= 0)
                System.out.println("Carry");
            else
                System.out.println("No Carry");
        }else {
            System.out.println("No Carry");
        }
        System.out.println((byte)(t1 + t2));
    }
}
