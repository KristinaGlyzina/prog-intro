import java.io.*;
import java.nio.charset.StandardCharsets;

public class CustomScanner {
    private Reader reader;
    private final char[] buffer = new char[1024];
    private int bufferIndex;
    private int bufferSize;
    private char c;

    public CustomScanner(File file) throws IOException {
        this.reader = new FileReader(file, StandardCharsets.UTF_8);
        this.fillBuffer();
    }

    public CustomScanner(String s) {
        this.reader = new StringReader(s);
        this.fillBuffer();
    }

    public CustomScanner(InputStream in) {
        this.reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        this.fillBuffer();
    }

    private void fillBuffer() {
        try {
            if (bufferSize == -1 || bufferIndex == bufferSize) {
                bufferSize = reader.read(buffer);
                bufferIndex = 0;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean hasNextLine() {
        fillBuffer();
        if (bufferSize == -1) {
            return false;
        }
        if (bufferIndex < bufferSize) {
            return checkNextLine();
        } else {
            fillBuffer();
            return hasNextLine();
        }
    }

    private boolean checkNextLine() {
        for (int i = bufferIndex; i < bufferSize; i++) {
            if (buffer[i] != '\0') {
                return true;
            } else {
                break;
            }
        }
        return false;
    }

    private boolean isAlphabeth(char c) {
        return Character.getType(c) == Character.DASH_PUNCTUATION || Character.isLetter(c) || c == '\'';
    }

    public String nextLine() {
        StringBuilder s = new StringBuilder();
        fillBuffer();
        StringBuilder line = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        while (true) {
            char c = buffer[bufferIndex++];
            s.append(c);
            if (s.length() > lineSeparator.length()) {
                line.append(s.charAt(0));
                s.deleteCharAt(0);
            }
            if (bufferSize == -1 || lineSeparator.contentEquals(s)) {
                break;
            }
            fillBuffer();
        }
        return line.toString();
    }

    public void close() throws IOException {
        reader.close();
    }

    private String getNextInt() {
        StringBuilder sb = new StringBuilder();
        char ch = buffer[bufferIndex++];
        while (Character.isWhitespace(ch)) {
            fillBuffer();
            ch = buffer[bufferIndex++];
        }
        while (Character.isDigit(ch) || ch == '-' || Character.isDigit(returnInt(ch))) {
            if (bufferSize == -1) {
                break;
            }
            fillBuffer();
            if (Character.isDigit(returnInt(ch))) {
                sb.append(returnInt(ch));
            } else {
                sb.append(ch);
            }
            ch = buffer[bufferIndex++];
        }
        return sb.toString();
    }

    private char returnInt(char c) {
        switch (c) {
            case 'a':
                return '0';
            case 'b':
                return '1';
            case 'c':
                return '2';
            case 'd':
                return '3';
            case 'e':
                return '4';
            case 'f':
                return '5';
            case 'g':
                return '6';
            case 'h':
                return '7';
            case 'i':
                return '8';
            case 'j':
                return '9';
        }
        return '\0';
    }
}