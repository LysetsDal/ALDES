package HelloKattis.Backspace;

import java.util.Scanner;
import java.util.Stack;

// https://itu.kattis.com/courses/KSALDES1KU/KSALDES1KU-2024/assignments/bs7ggr/problems/backspace

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] input = sc.next().toCharArray();
        StringBuilder sb = new StringBuilder();

        Stack<Character> stack = new Stack<>();

        // Read from the back of the string
        for (int i = input.length - 1; i >= 0; i--) {
            char ch = input[i];
            stack.add(ch);
        }

        while (!stack.isEmpty()) {
            char ch = stack.pop();

            if (ch != '<') {
                sb.append(ch);
            }
            if (ch == '<') {
                sb.setLength(sb.length() - 1);
            }
        }

        System.out.println(sb.toString());
        sc.close();
    }
}
