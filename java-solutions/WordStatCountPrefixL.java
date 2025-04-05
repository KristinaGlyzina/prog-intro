import java.io.*;
import java.util.*;

public class WordStatCountPrefixL {
    public static boolean isAlphabeth(char c) {
        return Character.getType(c) == Character.DASH_PUNCTUATION || Character.isLetter(c) || c == '\'';
    }

    public static void main(String[] args) {
        String inputFile = args[0];
        String outputFile = args[1];

        BufferedReader reader = null;
        BufferedWriter writer = null;

        Map<String, Integer> map = new LinkedHashMap<>();

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                String words = line.toLowerCase() + " ";
                char[] charArray = words.toCharArray();
                for (char c : charArray) {
                    if (isAlphabeth(c)) {
                        sb.append(c);
                    } else {
                        String word = sb.toString();
                        if (!word.isEmpty()) {
                            if (word.length() >= 3) {
                                word = word.substring(0, 3);

                                    map.put(word, map.getOrDefault(word, 0) + 1);
                            }
                            sb.setLength(0);
                        }
                    }
                }
            }

            List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
            Collections.sort(list, Map.Entry.comparingByValue());
            Map<String, Integer> sortedLinkedHashMap = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : list) {
                sortedLinkedHashMap.put(entry.getKey(), entry.getValue());
            }
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
                for (Map.Entry<String, Integer> item : sortedLinkedHashMap.entrySet()) {
                    if (item.getKey().length() >= 3) {
                        writer.write(item.getKey().substring(0, 3) + " " + item.getValue() + "\n");
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } finally {
                    writer.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}