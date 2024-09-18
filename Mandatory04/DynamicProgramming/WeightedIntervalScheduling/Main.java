import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int size = io.getInt();

        Job[] jobs = new Job[size];

        for (int i = 0; i < size; i++) {
            int start = io.getInt();
            int finish = io.getInt();
            int weight = io.getInt();
            jobs[i] = new Job(start, finish, weight);
        }

        // sort by earliest finish time
        Arrays.sort(jobs, ((a, b) -> Integer.compare(a.finish, b.finish)));

        System.out.println(computeOptimal(jobs));
        io.close();
    }

    public static int computeOptimal(Job[] jobs) {
        int n = jobs.length;

        int[] memo = new int[n];
        int[] jobP = new int[n];

        for (int i = 0; i < n; i++) {
            jobP[i] = findLastNonOverlapping(jobs, i);
        }

        memo[0] = jobs[0].weight;

        for (int i = 1; i < n; i++) {

            int take = jobs[i].weight;
            int lastJob = jobP[i];

            if (lastJob != -1) {
                take += memo[lastJob];
            }

            int drop = memo[i - 1];

            memo[i] = Math.max(take, drop);
        }

        return memo[n - 1];
    }

    private static int findLastNonOverlapping(Job[] jobs, int jobIndex) {
        int low = 0;
        int high = jobIndex - 1;

        // We can use binary search because jobs is sorted
        while (low <= high) {
            int mid = (low + high) / 2;
            if (jobs[mid].finish <= jobs[jobIndex].start) {
                if (jobs[mid + 1].finish <= jobs[jobIndex].start) {
                    low = mid + 1;
                } else {
                    return mid;
                }
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    public static class Job {
        int start;
        int finish;
        int weight;

        public Job(int start, int finish, int weight) {
            this.start = start;
            this.finish = finish;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return this.start + " " + this.finish + " " + this.weight;
        }
    }

    static class Kattio extends PrintWriter {
        public Kattio(InputStream i) {
            super(new BufferedOutputStream(System.out));
            r = new BufferedReader(new InputStreamReader(i));
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
