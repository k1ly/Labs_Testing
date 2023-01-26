package fundamentals;

public class Task4 {

    public static void main(String[] args) {
        int sum = 0;
        for (int i = args.length; i > 0; i--) {
            sum += Integer.parseInt(args[i - 1]);
        }
        System.out.println(sum);
    }
}
