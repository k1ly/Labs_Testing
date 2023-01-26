package fundamentals;

import java.util.Scanner;

public class Task5 {

    public static void main(String[] args) {
        System.out.println("Введите номер месяца: ");
        int month = new Scanner(System.in).nextInt();
        String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        if (month < 1 || month > 12)
            System.out.println("Недопустимый номер");
        else System.out.println("Ваш месяц: " + months[month - 1]);
    }
}
