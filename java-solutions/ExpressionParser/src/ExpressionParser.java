import expression.parser.TripleParser;

import java.util.*;

public class ExpressionParser implements TripleParser {
    private final Map<Character, Integer> priority = new HashMap<Character, Integer>() {{
        put('(', 0);
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
        put('~', 3);
    }};;
    private final Stack<String> stack = new Stack<>();
    private final List<String> numbers = new ArrayList<>();
    private final Stack<AnyExpression> eval = new Stack<>();
    private String expression;

    public AnyExpression toReturnExpression() {
        for (String symbol : numbers) {
            if (symbol.equals("x") || symbol.equals("y") || symbol.equals("z")) {
                eval.push(new Variable(symbol));
            } else if (symbol.equals("-") || symbol.equals("+") || symbol.equals("/") || symbol.equals("*") || symbol.equals("~")) {
                AnyExpression temp = eval.pop();
                switch (symbol) {
                    case "+" -> eval.push(new Add(eval.pop(), temp));
                    case "-" -> eval.push(new Subtract(eval.pop(), temp));
                    case "/" -> eval.push(new Divide(eval.pop(), temp));
                    case "*" -> eval.push(new Multiply(eval.pop(), temp));
                    case "~" -> eval.push(new UnaryMinus(eval.pop()));
                }
            } else {
                eval.push(new Const(Integer.parseInt(symbol)));
            }
        }
        return eval.get(0);
        //System.err.println(eval);
    }

    public void toParse() {
        char[] charNumbers = this.expression.toCharArray();
        int pointer = 0;
        while (pointer < charNumbers.length) {
            char symbol = charNumbers[pointer];
            if (symbol >= '0' && symbol <= '9') {
                StringBuilder number = new StringBuilder();
                number.append(symbol);
                numbers.add(number.toString());
                pointer++;
            } else if (symbol == '(') {
                stack.add(String.valueOf(symbol));
                pointer++;
            } else if (symbol == ')') {
                while (!stack.peek().equals('(')) {
                    stack.pop();
                }
                stack.pop();
            } else if (Character.isWhitespace(symbol)) {
                pointer++;
            } else if (symbol == '+' || symbol == '-') {
                if (pointer == 0 && symbol == '-') {
                    symbol = '~';
                    stack.add(String.valueOf(symbol));
                } else if (symbol == '-') {
                    char prev = charNumbers[pointer - 1];
                    if (prev == '+' || prev == '-' || prev == '*' || prev == '/' || prev == '~') {
                        symbol = '~';
                        stack.add(String.valueOf(symbol));
                    }
                }
                if (stack.contains('*') || stack.contains('/') || stack.contains('~')) {
                    while (priority.get(symbol) != 1) {
                        String c = stack.pop();
                        numbers.add(c);
                    }
                    stack.add(String.valueOf(symbol));
                }
                stack.add(String.valueOf(symbol));
                pointer++;
            } else if (symbol == '*' || symbol == '/') {
                if (stack.contains('~')) {
                    while (priority.get(symbol) != 3) {
                        String c = stack.pop();
                        numbers.add(c);
                    }
                    pointer++;
                } else {
                    stack.add(String.valueOf(symbol));
                    pointer++;
                }
            } else if (symbol == 'x') {
                numbers.add(String.valueOf(symbol));
                pointer++;
            }
        }
        while (!stack.isEmpty()) {
            numbers.add(stack.pop());
        }
    }
    @Override
    public AnyExpression parse(String str){
        this.expression = str;
        toParse();
        return toReturnExpression();
    }
}