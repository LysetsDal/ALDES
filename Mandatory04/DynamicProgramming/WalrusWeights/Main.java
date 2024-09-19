package WalrusWeights;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int MAX_WEIGHT = 1500;
        int size = io.getInt();

        int[] plates = new int[size];

        for (int i = 0; i < size; i++) {
            int plate = io.getInt();
            plates[i] = plate;
        }

        boolean[] memo = new boolean[MAX_WEIGHT + 1];
        memo[0] = true;

        for (int plate : plates) {

            for (int w = MAX_WEIGHT; w >= plate; w--) {
                if (memo[w - plate]) {
                    memo[w] = true;
                }
            }
        }

        int bestWeight = 0;
        for (int w = 0; w < MAX_WEIGHT; w++) {
            if (memo[w]) {
                if (Math.abs(1000 - w) < Math.abs(1000 - bestWeight)) {
                    bestWeight = w;
                } else if (Math.abs(1000 - w) == Math.abs(1000 - bestWeight) && w > bestWeight) {
                    bestWeight = w;
                }
            }
        }

        System.out.println(bestWeight);
        io.close();
    }

    static class Kattio extends PrintWriter {
        public Kattio(InputStream i) {
            super(new BufferedOutputStream(System.out));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public int getInt() {
            return Integer.parseInt(nextToken());
        }

        public double getDouble() {
            return Double.parseDouble(nextToken());
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
