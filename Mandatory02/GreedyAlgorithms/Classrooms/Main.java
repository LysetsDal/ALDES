package GreedyAlgorithms.Classrooms;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// https://itu.kattis.com/courses/KSALDES1KU/KSALDES1KU-2024/assignments/nmchzj/problems/classrooms

// WRONG CURRENTLY...

/*
 * Even if the ending time of one activity equals the starting time another activity), 
 * they cannot be assigned to the same classroom.
 */

public class Main {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int n = io.getInt(); // number of activities
        int k = io.getInt(); // number of classrooms

        Interval[] intervals = new Interval[n];

        // Parse input
        for (int i = 0; i < n; i++) {
            int start = io.getInt();
            int end = io.getInt();
            intervals[i] = new Interval(start, end);
        }

        // Sort intervals by lowest end time
        Arrays.sort(intervals);

        for (Interval interval : intervals) {
            System.out.println(interval.toString());
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        ArrayList<Integer> frontier = new ArrayList<>();
        int maxActivities = 0;

        for (Interval interval : intervals) {
            System.out.println("Interval " + "(" + interval.start + " - " + interval.end + ")");

            if (pq.size() < k) { // if there is room we add by default
                pq.offer(interval.end);
                System.out.println("Added " + interval.end + " to pq");
                maxActivities++;
            } else if (!pq.isEmpty() && pq.peek() < interval.start) {
                while (!pq.isEmpty() && pq.peek() < interval.start) {
                    int head = pq.poll();
                    frontier.add(head);
                    System.out.println("Added " + head + " from pq to frontier");
                }

                int currentDiff = Integer.MAX_VALUE;

                for (int i = 0; i < frontier.size(); i++) {
                    int element = frontier.get(i);
                    System.out.println("Element: " + element);
                    int diff = interval.start - element;

                    System.out.println("Diff: " + diff + " (" + interval.start + "-" + element + ")");

                    if (diff < currentDiff) {
                        currentDiff = interval.start;
                        frontier.remove(i);
                        pq.offer(currentDiff);
                        maxActivities++;
                        
                        System.out.println("New Lowest: " + currentDiff);
                    } else {
                        System.out.println("Added from frontier to pq: " + element);
                        frontier.remove(i);

                    }

                }

                frontier.clear();
                System.out.println("[End] Added " + currentDiff + " to pq");
                
            }
        }

        System.out.println(maxActivities);
        io.close();
    }

    static class Interval implements Comparable<Interval> {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "(" + this.start + ", " + this.end + ")";
        }

        @Override
        public int compareTo(Interval other) {
            return Integer.compare(this.end, other.end);
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
