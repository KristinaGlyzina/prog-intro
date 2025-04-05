public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String s : args) {
            int ptr = -1;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (!Character.isWhitespace(c)) {
                    if (ptr == -1) {
                        ptr = i;
                    }
                } else {
                    if (ptr != -1) {
                        sum += Integer.parseInt(s.substring(ptr, i));
                        ptr = -1;
                    }
                }
            }
            if (ptr != -1) {
                sum += Integer.parseInt(s.substring(ptr));
            }
        }
        System.out.println(sum);
    }
}