public class SequentialPlayer implements Player {
    @Override
    public Move move(final Position position, final Cell cell, int m, int n) {
//        Board board = (Board) position;
//        board.makeMove()


        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                final Move move = new Move(r, c, cell);
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}