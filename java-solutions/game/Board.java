public interface Board {
    Position getPosition();

    Cell getCell();

    Result makeMove(Move move);

    int getM();

    int getN();

    void reset();
}