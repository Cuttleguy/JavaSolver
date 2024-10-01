package util.math;

import org.scilab.forge.jlatexmath.Char;

import java.util.*;

public class InfixToPostfix {
    // Function to return precedence of operators
    static int prec(char c) {
        if (c == '^')
            return 3;
        else if (c == '/' || c == '*')
            return 2;
        else if (c == '+' || c == '-')
            return 1;
        else
            return -1;
    }

    // Function to return associativity of operators
    static char associativity(char c) {
        if (c == '^')
            return 'R';
        return 'L'; // Default to left-associative
    }

    // The main function to convert infix expression to postfix expression
    // The main function to convert infix expression to postfix expression
    public static String infixToPostfix(String s) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // If the scanned character is an operand, add it to the output string.
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                result.append(c);
            }
            // If the scanned character is an ‘(‘, push it to the stack.
            else if (c == '(') {
                stack.push(c);
            }
            // If the scanned character is an ‘)’, pop and add to the output string from the stack
            // until an ‘(‘ is encountered.
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop(); // Pop '('
            }
            // If an operator is scanned
            else {
                result.append(' ');
                while (!stack.isEmpty() && (prec(s.charAt(i)) < prec(stack.peek()) ||
                        prec(s.charAt(i)) == prec(stack.peek()) &&
                                associativity(s.charAt(i)) == 'L')) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }

        // Pop all the remaining elements from the stack
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }
    public static String[] tokenize(String string)
    {
        Character[] seperators = new Character[]{
                '+',
                '-',
                '*',
                '/',
                '(',
                ')',
                '^',
                ' '
        };
        ArrayList<String> tokens=new ArrayList<>();
        StringBuilder tokenBuilder=new StringBuilder();
        for (int i = 0; i < string.length(); i++)
        {
            char c = string[i];

            if(seperators.toList().contains(c))
            {
                if(tokenBuilder.isEmpty())
                {
                    tokens.add(String.valueOf(c));
                }
                else{
                    tokens.add(tokenBuilder.toString());
                    tokens.add(String.valueOf(c));

                }
                tokenBuilder=new StringBuilder();
            }
            else
            {
                tokenBuilder.append(c);
            }
        }
        String[] toReturn = new String[tokens.size()];
        toReturn=tokens.toArray(toReturn);

        return toReturn;
    }
}
