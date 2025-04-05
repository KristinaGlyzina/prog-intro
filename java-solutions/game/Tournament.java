import java.util.*;

public class Tournament {
    private Map<Integer, Player> playerMap = new HashMap<>();
    private List<Integer> stagePlayers = new ArrayList<>();
    private Board board;
    private final Random random = new Random();
    private final boolean log;
    private int playersCount;

    public Tournament(final boolean log, int playersCount) {
        this.log = log;
        this.playersCount = playersCount;
    }

    public void validCount() {
        int power = 1;
        while (power * 2 <= playersCount) {
            power *= 2;
        }
        while (power != playersCount) {
            System.out.println("A preliminary selection is carried out, since the number of players must be a power of two.");
            System.out.println("Currently the number of players is: " + playerMap.size());
            selectPlayers();
        }
        int winner = playTour();
        System.out.println("Winner" + winner);
        System.out.println("Currently the number of players is: " + playerMap.size());
    }

    private void addPlayers(int playersCount) {
        for (int i = 1; i <= playersCount; i++) {
            playerMap.put(i, new HumanPlayer());
        }
    }

    private int playTour() {
        stagePlayers.addAll(playerMap.keySet());
        while (stagePlayers.size() != 1) {
            playStage();
        }
        System.out.println("Tournament is over.");
        return stagePlayers.get(0);
    }

    private void playStage() {
        for (int indexPlayer1 = 0; indexPlayer1 < stagePlayers.size(); indexPlayer1 += 2) {
            int indexPlayer2 = indexPlayer1 + 1;
            System.out.println("Competitors for round: " + "player-" + stagePlayers.get(indexPlayer1) + " and " + "player-" + stagePlayers.get(indexPlayer2) + ".");
            playRound(stagePlayers.get(indexPlayer1), stagePlayers.get(indexPlayer2));
        }
        stagePlayers.clear();
        stagePlayers.addAll(playerMap.keySet());
    }

    private void playRound(int firstKey, int secondKey) {
        int result = 0;
        while (result == 0) {
            final Game game = new Game(log, playerMap.get(firstKey), playerMap.get(secondKey));
            result = game.play(board);
            if (result == 1) {
                System.out.println("Player-" + firstKey + " has won.");
                playerMap.remove(secondKey);
                System.out.println("Player-" + secondKey + " is eliminated from the game.");
            } else if (result == 2) {
                System.out.println("Player-" + secondKey + " has won.");
                playerMap.remove(firstKey);
                System.out.println("Player-" + firstKey + " is eliminated from the game.");
            }
            board.reset();
        }

    }

    public void play(Board board) {
        this.board = board;
        addPlayers(playersCount);
        validCount();
        while (playerMap.size() > 1) {
            System.out.println("The tournament started.");
            int winnerIndex = playTour();
            System.out.println("Winner is player-" + winnerIndex);
        }
    }

    private void selectPlayers() {
        int firstPlayer = getRandomPlayer();
        System.out.println("first player-" + firstPlayer);
        int secondPlayer = getRandomPlayer();

        while (firstPlayer == secondPlayer) {
            secondPlayer = getRandomPlayer();
        }
        System.out.println("second player-" + secondPlayer);
        playPreliminaryRound(firstPlayer, secondPlayer);
    }

    private void playPreliminaryRound(int player1, int player2) {
        int result = 0;
        final Game game = new Game(log, playerMap.get(player1), playerMap.get(player2));
        while (result == 0) {
            result = game.play(board);
            if (result == 1) {
                playerMap.remove(player2);
                System.out.println("Defeated player-" + player2 + " is eliminated from the game.");
            } else if (result == 2) {
                playerMap.remove(player1);
                System.out.println("Defeated player-" + player1 + " is eliminated from the game.");
            }
            playersCount = playerMap.size();
            board.reset();
        }
    }

    private int getRandomPlayer() {
        int index = -1;
        while (playerMap.get(index) == null) {
            index = random.nextInt(1, playerMap.size() + 1);
        }

        return index;
    }
}