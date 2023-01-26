package fundamentals;

import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) {
        System.out.println("Введите имя: ");
        String name = new Scanner(System.in).nextLine();
        System.out.println("Привет, " + name);
    }
}
