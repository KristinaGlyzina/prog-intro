import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        int m, n, k;

        System.out.println("If you want to play 'MNK' enter: '1', if you want to play 'Tournament' enter: '2'.");
        int num = getNum(1, 2);

        System.out.println("Please, enter board parameters: count of rows, columns and count of 'X' or 'O' per line to win.");
        m = getNum(1, Integer.MAX_VALUE);
        n = getNum(1, Integer.MAX_VALUE);
        k = getNum(1, Integer.MAX_VALUE);

        if (num == 1) {
            System.out.println("MNK game started.");
            final Game game = new Game(false, new HumanPlayer(), new HumanPlayer());
            int result;

            do {
                result = game.play(new MNKBoard(m, n, k));
                System.out.println("Game result: " + result);
            } while (result != 0);

        } else {
            System.out.println("Please, enter count of players. Minimum number of players 2.");
            int playersCount;

            playersCount = getNum(2, Integer.MAX_VALUE);
            final Tournament tournament = new Tournament(false, playersCount);
            tournament.play(new MNKBoard(m, n, k));
        }
        sc.close();
    }

    public static int getInt() {
        try {
            int n = sc.nextInt();
            return n;
        } catch (InputMismatchException ex) {
            System.out.println("Input should contain a number.");
            sc.next();
            return getInt();
        } catch (NoSuchElementException ex) {
            System.out.println("You determinate the program.");
            System.exit(0);
        }
        return 0;
    }

    public static int getNum(int num1, int num2) {
        int num;
        do {
            num = getInt();
        } while (!(num1 <= num && num <= num2));
        return num;
    }
}