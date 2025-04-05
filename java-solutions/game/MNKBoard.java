import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final Cell[][] cells;

    private Cell turn;
    private final int m, n, k;
    private int empty;

    public MNKBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.cells = new Cell[m][n];
        reset();
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }

        cells[move.getRow()][move.getColumn()] = move.getValue();
        if (checkWin(move.getRow(), move.getColumn())) {
            return Result.WIN;
        }
        if (empty == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    private boolean checkWin(int r, int c) {
        return deep(r, c, 0, 1) || deep(r, c, 1, 0) || deep(r, c, 1, 1) || deep(r, c, 1, -1);
    }

    private boolean deep(int r, int c, int dr, int dc) {
        int inLine = 0;
        Cell begin = cells[r][c];
        while (onTheBoard(r, c) && cells[r][c] == begin) {
            r += dr;
            c += dc;
            ++inLine;
        }
        r -= dr * inLine;
        c -= dc * inLine;
        while (onTheBoard(r, c) && cells[r][c] == begin) {
            r -= dr;
            c -= dc;
            ++inLine;
        }
        return inLine - 1 >= k;
    }

    private boolean onTheBoard(int r, int c) {
        return 0 <= r && r < m && 0 <= c && c < n;
    }

    @Override
    public boolean isValid(final Move move) {
        return onTheBoard(move.getRow(), move.getColumn())
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public String toString() {
        String lineSeparator = System.lineSeparator();
        final StringBuilder sb = new StringBuilder("    ");
        for (int i = 0; i < m; i++) {
            sb.append(String.format("%4s", i));
        }
        sb.append(lineSeparator);
        for (int r = 0; r < n; r++) {
            sb.append(String.format("%4s", (r)));
            for (Cell cell : cells[r]) {
                sb.append(String.format("%4s", SYMBOLS.get(cell)));
            }
            sb.append(lineSeparator);
        }
        return sb.toString();
    }

    public void reset() {
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        empty = m * n;
    }
}