package Knapsack;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        while (io.hasMoreTokens()) {
            findMaxWeight(io);
        }

        io.close();
    }

    public static void findMaxWeight(Kattio io) {
        int weightLimit = io.getInt();
        int objectSize = io.getInt();

        Item[] items = new Item[objectSize];

        for (int i = 0; i < objectSize; i++) {
            int value = io.getInt();
            int weight = io.getInt();
            items[i] = new Item(value, weight, i);
        }

        Result result = getResult(items, weightLimit);
        System.out.println(result);
    }

    // Main logic
    static Result getResult(Item[] items, int cap) {
        int n = items.length;

        int[][] memo = new int[n + 1][cap + 1]; // Memoization table

        for (int i = 1; i <= n; i++) {

            int value = items[i - 1].value;
            int weight = items[i - 1].weight;

            for (int w = 0; w <= cap; w++) {

                if (weight > w) {
                    memo[i][w] = memo[i - 1][w]; // Item to big
                } else {
                    // Either drop or take
                    memo[i][w] = Math.max(memo[i - 1][w], memo[i - 1][w - weight] + value);
                }
            }
        }

        ArrayList<Integer> chosenItems = new ArrayList<>();

        for (int i = n; i > 0; i--) {
            // If the value has changed, it means the item was taken
            if (memo[i][cap] != memo[i - 1][cap]) {
                chosenItems.add(items[i - 1].index); // Add item's index
                cap -= items[i - 1].weight; // Reduce the remaining capacity
            }
        }

        Collections.sort(chosenItems); // Sort the chosen item indices
        return new Result(chosenItems.size(), chosenItems);
    }

    static class Item {
        public int value;
        public int weight;
        public int index;

        public Item(int value, int weight, int index) {
            this.value = value;
            this.weight = weight;
            this.index = index;
        }
    }

    static class Result {
        public int itemCount;
        public ArrayList<Integer> chosenItems;

        public Result(int itemCount, ArrayList<Integer> chosenItems) {
            this.itemCount = itemCount;
            this.chosenItems = chosenItems;
        }

        public String printList() {
            StringBuilder strb = new StringBuilder();
            for (Integer integer : chosenItems) {
                strb.append(integer + " ");
            }
            return strb.toString().trim();
        }

        @Override
        public String toString() {
            return this.itemCount + "\n" + printList();
        }
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
                        if (line == null)
                            return null;
                        st = new StringTokenizer(line);
                    }
                    token = st.nextToken();
                } catch (IOException e) {
                }
            return token;
        }

        private String nextToken() {
            String ans = peekToken();
            token = null;
            return ans;
        }
    }
}
