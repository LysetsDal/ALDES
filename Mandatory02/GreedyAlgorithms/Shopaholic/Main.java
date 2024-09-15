package GreedyAlgorithms.Shopaholic;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

// https://itu.kattis.com/courses/KSALDES1KU/KSALDES1KU-2024/assignments/nmchzj/problems/shopaholic

public class Main {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int size = io.getInt();

        Long[] items = new Long[size];
        long totalDiscount = 0;

        for (int i = 0; i < size; i++) {
            long item = io.getLong();
            items[i] = item;
        }

        Arrays.sort(items, Comparator.reverseOrder());

        int count = 1;
        for (long item : items) {
            if (count % 3 == 0) {
                totalDiscount = totalDiscount + item;
            }
            count++;
        }

        System.out.println(totalDiscount);
        io.close();
    }
    static class Kattio extends PrintWriter {
        public Kattio(InputStream i) {
            super(new BufferedOutputStream(System.out));
            r = new BufferedReader(new InputStreamReader(i));
        }
        public Kattio(InputStream i, OutputStream o) {
            super(new BufferedOutputStream(o));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public boolean hasMoreTokens() {
            return peekToken() != null;
        }

        public int getInt() {
            return Integer.parseInt(nextToken());
        }

        public double getDouble() {
            return Double.parseDouble(nextToken());
        }

        public long getLong() {
            return Long.parseLong(nextToken());
        }

        public String getWord() {
            return nextToken();
        }



        private BufferedReader r;
        private String line;
        private StringTokenizer st;
        private String token;

        private String peekToken() {
            if (token == null)
                try {
                    while (st == null || !st.hasMoreTokens()) {
                        line = r.readLine();
                        if (line == null) return null;
                        st = new StringTokenizer(line);
                    }
                    token = st.nextToken();
                } catch (IOException e) { }
            return token;
        }

        private String nextToken() {
            String ans = peekToken();
            token = null;
            return ans;
        }
    }
}