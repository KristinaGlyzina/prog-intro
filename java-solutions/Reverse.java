import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Reverse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<int[]> list = new ArrayList<>();
        int[] arr = new int[2];
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            Scanner scanner2 = new Scanner(str);
            int trueLenght = 0;
            while (scanner2.hasNextLine()) {
                int num = scanner2.nextInt();
                if (trueLenght == arr.length) {
                    arr = Arrays.copyOf(arr, arr.length * 2);
                    arr[trueLenght] = num;
                    trueLenght++;
                } else {
                    arr[trueLenght] = num;
                    trueLenght++;
                }
            }
            arr = Arrays.copyOf(arr, trueLenght);
            list.add(arr);
            arr = new int[2];
            scanner2.close();
        }
        scanner.close();
        for (int i = list.size() - 1; i >= 0; i--) {
            for (int j = list.get(i).length - 1; j >= 0; j--) {
                System.out.print(list.get(i)[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}