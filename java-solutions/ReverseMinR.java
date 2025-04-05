import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ReverseMinR {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<int[]> list = new ArrayList<>();
        int[] arr = new int[2];
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            Scanner scanner2 = new Scanner(str);
            int trueLenght = 0;
            while (scanner2.hasNext()) {
                int num = scanner2.nextInt();
                if (trueLenght == arr.length) {
                    arr = Arrays.copyOf(arr, arr.length * 2);
                }
                arr[trueLenght] = num;
                trueLenght++;
            }
            arr = Arrays.copyOf(arr, trueLenght);
            list.add(arr);
            arr = new int[2];
        }
        for (int[] ints : list) {
            int min = Integer.MAX_VALUE;
            for (int anInt : ints) {
                if (anInt < min) {
                    min = anInt;
                }
                System.out.print(min);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}