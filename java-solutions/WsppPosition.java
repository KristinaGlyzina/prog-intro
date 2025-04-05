import java.io.*;
import java.util.*;

public class WsppPosition {

    static class Position {
        int line;
        int index;

        public Position(int line, int index) {
            this.line = line;
            this.index = index;
        }

        @Override
        public String toString() {
            return line + ":" + index;
        }
    }

    static boolean isValid(char c) {
        return Character.getType(c) == Character.DASH_PUNCTUATION || Character.isAlphabetic(c) || c == '\'';
    }

    public static void main(String[] args) {

        try {
            CustomScanner scanner = new CustomScanner(new File(args[0]));
            Map<String, List<Position>> words = new LinkedHashMap<>();
            List<Integer> lineCnt = new ArrayList<>();
            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                int wordNumber = 1;
                String line = scanner.nextLine() + System.lineSeparator();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (isValid(c)) {
                        sb.append(c);
                    } else {
                        if (!sb.isEmpty()) {
                            String word = sb.toString().toLowerCase();
                            List<Position> wordIndexes = words.get(word);
                            if (wordIndexes == null) {
                                wordIndexes = new ArrayList<>();
                            }
                            wordIndexes.add(new Position(lineNumber, wordNumber));
                            words.put(word, wordIndexes);
                            sb.setLength(0);
                            wordNumber++;
                        }
                    }
                }
                lineCnt.add(wordNumber);
                lineNumber++;
            }
            scanner.close();
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1])));
                for (Map.Entry<String, List<Position>> wordList : words.entrySet()) {
                    String word = wordList.getKey();
                    List<Position> positions = wordList.getValue();
                    for (Position position : positions) {
                        position.index = lineCnt.get(position.line - 1) - position.index;
                    }
                    writer.append(word).append(" ").append(String.valueOf(positions.size()));
                    for (Position position : positions) {
                        writer.append(" ").append(String.valueOf(position));
                    }
                    writer.append(System.lineSeparator());
                }
            } catch (IOException e){
                System.err.println("Error with writer: " + e.getMessage());
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error with scanner:  " + e.getMessage());
        }
    }
}