package fundamentals;

import java.util.Random;
import java.util.Scanner;

public class Task3 {

    public static void main(String[] args) {
        System.out.println("Введите кол-во чисел: ");
        int count = new Scanner(System.in).nextInt();
        var rand = new Random();
        for (int i = 0; i < count; i++) {
            System.out.println(rand.nextInt());
        }
        for (int i = 0; i < count; i++) {
            System.out.print(rand.nextInt());
        }
    }
}
