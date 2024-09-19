package ExactChange;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    public static final int MAX_AMOUNT = 10_000;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int testCases = io.getInt();

        for (int i = 0; i < testCases; i++) {
            findSolution(io);
        }

        io.close();
    }

    public static void findSolution(Kattio io) {
        int targetAmount = io.getInt();
        int numBills = io.getInt();

        int[] bills = new int[numBills];

        for (int i = 0; i < numBills; i++) {
            bills[i] = io.getInt();
        }

        // Sorting an int array is stupid in java...
        Integer[] arrInteger = Arrays.stream(bills).boxed().toArray(Integer[]::new);
        Arrays.sort(arrInteger, Collections.reverseOrder());
        bills = Arrays.stream(arrInteger).mapToInt(Integer::intValue).toArray();

        int[] result = solve(bills, targetAmount);
        System.out.println(result[0] + " " + result[1]);
    }

    public static int[] solve(int[] bills, int targetAmount) {
        int[] memo = new int[MAX_AMOUNT + 1];
        Arrays.fill(memo, -1);
        memo[0] = 0;

        for (int bill : bills) {
            for (int amount = MAX_AMOUNT; amount >= bill; amount--) {
                int newAmount = amount - bill;

                if (memo[newAmount] == -1) {
                    continue;
                }

                if (memo[amount] == -1) {
                    memo[amount] = memo[newAmount] + 1;

                } else {
                    // Update memo: (drop bill), (take bill)
                    memo[amount] = Math.min(memo[amount], memo[newAmount] + 1);
                }
            }
        }

        int[] result = new int[] { -1, -1 };
        for (int amount = targetAmount; amount <= MAX_AMOUNT; amount++) {
            if (memo[amount] != -1) {
                result = new int[] { amount, memo[amount] };
                break;
            }
        }

        return result;
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
