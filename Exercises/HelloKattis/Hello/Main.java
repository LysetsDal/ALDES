package HelloKattis.Hello;

import java.util.Collections;
import java.util.Scanner;

// https://itu.kattis.com/courses/KSALDES1KU/KSALDES1KU-2024/assignments/bs7ggr/problems/echoechoecho


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String word = sc.next();

        System.out.println(String.join(" ", Collections.nCopies(3, word)));
    }
}
