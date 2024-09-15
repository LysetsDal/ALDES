package Mandatory03.DivideAndConquer.ClosestPoints;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.StringTokenizer;

public class ClosestPoints {

    private static final PointComparer comparer = new PointComparer();

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int size = io.getInt();

        List<Point> pointsX = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            double x = io.getDouble();
            double y = io.getDouble();
            Point point = new Point(x, y);
            pointsX.add(point);
        }

        // Start logic
        pointsX.sort(comparer.compareByX());
        
        List<Point> res = closestUtil(pointsX, 0, size);

        for (Point point : res) {
            System.out.println(point);
        }

        io.close();
    }

    public static List<Point> closestUtil(List<Point> pointsX,  int startIdx, int endIdx) {
        if ((endIdx - startIdx) <= 3) {
            return bruteForce(pointsX, endIdx);
        }

        int mid = startIdx + (endIdx - startIdx) / 2;
        Point midPoint = pointsX.get(mid);

        // Left and right partition of lists
        List<Point> left = closestUtil(pointsX, startIdx, mid);
        List<Point> right = closestUtil(pointsX, mid, endIdx);

        // left and right delta
        double dl = dist(left.get(0), left.get(1));
        double dr = dist(right.get(0), right.get(1));

        // find the min of the two
        double delta = Math.min(dl, dr);

        // Set deltaPoints equal to the min two points
        List<Point> deltaPoints;
        if (delta == dl) {
            deltaPoints = left;
        } else {
            deltaPoints = right;
        }

        // Only add points in strip array that are closer than current delta
        List<Point> strip = new ArrayList<>();
        for (int i = 0; i < endIdx; i++) {
            // calculates the delta distance on each side.
            if (Math.abs(pointsX.get(i).x - midPoint.x) < delta) {
                strip.add(pointsX.get(i));
                System.out.println("Added:" + pointsX.get(i).toString() + " to strip");
            }
        }

        List<Point> stripClosest = stripClosest(strip, strip.size(), delta);

        if (stripClosest.get(0) == null | stripClosest.get(1) == null) {
            return deltaPoints;
        }

        if (delta < dist(stripClosest.get(0), stripClosest.get(0))) {
            return deltaPoints;

        }

        return stripClosest;
    }

    public static List<Point> stripClosest(List<Point> strip, int size, double d) {
        double min = d;
        List<Point> res = new ArrayList<>();

        strip.sort(comparer.compareByY());

        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size && (strip.get(j).y - strip.get(i).y) < min; ++j) {
                double dist = dist(strip.get(i), strip.get(j));
                if (dist < min) {
                    min = dist;
                    res.add(strip.get(i));
                    res.add(strip.get(j));
                }
            }
        }

        return res;
    }

    public static List<Point> bruteForce(List<Point> points, int n) {
        double min = Double.MAX_VALUE;
        List<Point> res = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                double currMin = dist(points.get(i), points.get(j));
                if (currMin < min) {
                    min = currMin;
                    res.add(points.get(i));
                    res.add(points.get(j));
                }
            }
        }
        return res;
    }

    private static double dist(Point p1, Point p2) {
        double distX = (p2.x - p1.x) * (p2.x - p1.x);
        double distY = (p2.y - p1.y) * (p2.y - p1.y);
        double d = Math.sqrt(distX + distY);
        return d;
    }

    private static class PointComparer {
        public Comparator<Point> compareByX() {
            return Comparator.comparingDouble(o -> o.x);
        }

        public Comparator<Point> compareByY() {
            return Comparator.comparingDouble(o -> o.y);
        }
    }

    public static class Point implements Comparable<Point> {
        public double x = 0;
        public double y = 0;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point other) {
            return compareToX(other);
        }

        public int compareToX(Point other) {
            return Double.compare(this.x, other.x);
        }

        @Override
        public String toString() {
            return this.x + " " + this.y;
        }
    }

    private static class Kattio extends PrintWriter {
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

        public float getFloat() {
            return Float.parseFloat(nextToken());
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
