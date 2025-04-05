import java.io.*;
import java.util.*;

public class WordStatInput {
    public static boolean isAlphabeth(char c) {
        if (Character.getType(c) == Character.DASH_PUNCTUATION || Character.isLetter(c) || c == '\'') {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String inputFile = args[0];
        String outputFile = args[1];

        LinkedHashMap<String, Integer> quantityWords = new LinkedHashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                String words = line.toLowerCase();
                char[] charArray = words.toCharArray();
                for (char c : charArray) {
                    if (isAlphabeth(c)) {
                        sb.append(c);
                    } else {
                        String word = sb.toString();
                        if (!word.equals("")) {
                            if (quantityWords.containsKey(word)) {
                                quantityWords.put(word, quantityWords.get(word) + 1);
                            } else {
                                quantityWords.put(word, 1);
                            }
                            sb.setLength(0);
                        }
                    }
                }
                String word = sb.toString();
                if (!word.equals("")) {
                    if (quantityWords.containsKey(word)) {
                        quantityWords.put(word, quantityWords.get(word) + 1);
                    } else {
                        quantityWords.put(word, 1);
                    }
                    sb.setLength(0);
                }
            }
            for (Map.Entry<String, Integer> item : quantityWords.entrySet()) {
                writer.write(item.getKey() + " " + item.getValue() + "\n");
            }
            writer.close();
            reader.close();
        } catch (
                IOException e) {
            e.getMessage();
        }
    }
}