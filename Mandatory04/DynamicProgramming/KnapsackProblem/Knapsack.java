
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Knapsack {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);

        while (io.hasMoreTokens()) {
            solve(io);
        }

        io.close();
    }

    public static void solve(Kattio io) {
        int weightLimit = io.getInt();
        int objectSize = io.getInt();

        Item[] items = new Item[objectSize];

        for (int i = 0; i < objectSize; i++) {
            int value = io.getInt();
            int weight = io.getInt();
            items[i] = new Item(value, weight, i + 1);
        }

        Result result = OPT(items, weightLimit);
        System.out.println(result);
    }

    // Main logic
    public static Result OPT(Item[] items, int weightLimit) {

        int n = items.length;
        int[][] memory = new int[n + 1][weightLimit + 1]; // Memoization table
        boolean[][] taken = new boolean[n + 1][weightLimit + 1]; // Mark when taking instead of dropping

        for (int i = 1; i <= n; i++) {
            int currentValue = items[i - 1].value;
            int currentWeight = items[i - 1].weight;

            for (int w = 0; w <= weightLimit; w++) {

                if (w >= currentWeight) {
                    if (memory[i - 1][w - currentWeight] + currentValue > memory[i][w]) {
                        memory[i][w] = memory[i - 1][w - currentWeight] + currentValue;
                        taken[i][w] = true; // Mark the item as taken
                    }
                }

                memory[i][w] = memory[i - 1][w];
            }
        }

        // Construct the list of indices
        List<Integer> chosenItems = new ArrayList<>();

        for (int i = n; i > 0; i--) {
            if (taken[i][weightLimit]) { // Item i was taken
                chosenItems.add(items[i - 1].index); // Add the item's index
                weightLimit -= items[i - 1].weight; // Reduce the remaining weight
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
        public List<Integer> chosenItems;

        public Result(int itemCount, List<Integer> chosenItems) {
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
