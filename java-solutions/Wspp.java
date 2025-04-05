import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Wspp {

    public static void main(String[] args) throws IOException {
        File input = new File(args[0]);
        CustomScanner scanner = new CustomScanner(input);
        LinkedHashMap<String, List<Integer>> words = new LinkedHashMap<>();
        int wordIndex = 1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine() + System.lineSeparator();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (isValid(c)) {
                    sb.append(c);
                } else {
                    if (!sb.toString().isEmpty()) {
                        String word = sb.toString().toLowerCase();
                        List<Integer> wordIndexes = words.get(word);
                        if (wordIndexes == null) {
                            wordIndexes = new ArrayList<>();
                        }
                        wordIndexes.add(wordIndex);
                        wordIndex++;
                        words.put(word, wordIndexes);
                        sb = new StringBuilder();
                    }
                }
            }
        }
        scanner.close();
        File output = new File(args[1]);
        PrintWriter writer = new PrintWriter(output);
        for (Map.Entry<String, List<Integer>> wordList : words.entrySet()) {
            String word = wordList.getKey();
            List<Integer> indexes = wordList.getValue();
            writer.print(word + " " + indexes.size());
            for (int i = 0; i < indexes.size(); i++) {
                writer.print(" " + indexes.get(i));
            }
            writer.println();
        }
        writer.close();
    }

    static boolean isValid(char c) {
        return Character.getType(c) == Character.DASH_PUNCTUATION || Character.isAlphabetic(c) || c == '\'';
    }
}