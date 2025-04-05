import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell, int m, int n) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            int first = 0;
            int second = 0;
            while (true) {
                try {
                    first = in.nextInt();
                    second = in.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please, enter the correct data.");
                    in.next();
                    in.next();
                }
            }
            final Move move = new Move(first, second, cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }
}