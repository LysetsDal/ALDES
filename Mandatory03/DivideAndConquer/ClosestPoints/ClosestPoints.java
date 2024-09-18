package DivideAndConquer.ClosestPoints;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class ClosestPoints {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        int size = io.getInt();

        Point[] points = new Point[size];

        for (int i = 0; i < size; i++) {
            double x = io.getDouble();
            double y = io.getDouble();
            points[i] = new Point(x, y);
        }

        Point[] res = findClosestPair(points);

        for (Point point : res) {
            System.out.println(point);
        }

        io.close();
    }

    private static Point[] closestPairRec(Point[] pointByX, Point[] pointByY) {
        int n = pointByX.length;

        // Base case: Brute-force on 3 or fewer points
        if (n <= 3) {
            return bruteForce(pointByX);
        }

        int mid = n / 2;
        Point midPoint = pointByX[mid];

        // Divide points into two "panes"
        Point[] leftPane = Arrays.copyOfRange(pointByX, 0, mid);
        Point[] rightPane = Arrays.copyOfRange(pointByX, mid, n);

        // Sort by the Y coordinates to not sort at every recursion
        List<Point> leftPaneByY = new ArrayList<>();
        List<Point> rightPaneByY = new ArrayList<>();
        for (Point p : pointByY) {
            if (p.x <= midPoint.x) {
                leftPaneByY.add(p);
            } else {
                rightPaneByY.add(p);
            }
        }

        Point[] closestPairLeft = closestPairRec(leftPane, leftPaneByY.toArray(new Point[0]));
        Point[] closestPairRight = closestPairRec(rightPane, rightPaneByY.toArray(new Point[0]));

        // Find the smaller distance of the left and right pane
        double delta = Math.min(dist(closestPairLeft[0], closestPairLeft[1]),
                dist(closestPairRight[0], closestPairRight[1]));

        // Get the maximum x-coordinate in the left pane
        double maxX = leftPane[leftPane.length - 1].x;

        // Find the points in the strip within delta dist
        List<Point> strip = new ArrayList<>();
        for (Point p : pointByY) {
            if (Math.abs(p.x - maxX) < delta) {
                strip.add(p);
            }
        }

        Point[] closestPairStrip = closestPairInStrip(strip.toArray(new Point[0]), delta);

        if (closestPairStrip != null && dist(closestPairStrip[0], closestPairStrip[1]) < delta) {
            return closestPairStrip;
        } else {
            return (dist(closestPairLeft[0], closestPairLeft[1]) < dist(closestPairRight[0], closestPairRight[1]))
                    ? closestPairLeft
                    : closestPairRight;
        }
    }

    public static Point[] findClosestPair(Point[] points) {
        Point[] Px = points.clone();
        Point[] Py = points.clone();

        Arrays.sort(Px, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(Py, Comparator.comparingDouble(p -> p.y));

        return closestPairRec(Px, Py);
    }

    private static Point[] closestPairInStrip(Point[] strip, double delta) {
        double minDist = delta;
        Point[] closestPair = new Point[2];

        // If strip has fewer than 2 points
        if (strip.length < 2) {
            return null;
        }

        // Initialize with the first two points in the strip
        closestPair[0] = strip[0];
        closestPair[1] = strip[1];

        int n = strip.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n && (strip[j].y - strip[i].y) < delta; j++) {
                double dist = dist(strip[i], strip[j]);
                if (dist < minDist) {
                    minDist = dist;
                    closestPair[0] = strip[i];
                    closestPair[1] = strip[j];
                }
            }
        }
        return closestPair;
    }

    public static Point[] bruteForce(Point[] points) {
        int n = points.length;
        double minDist = Double.MAX_VALUE;
        Point[] closestPair = new Point[2];

        // base case (early finish)
        if (n == 2) {
            closestPair[0] = points[0];
            closestPair[1] = points[1];
        }

        closestPair[0] = points[0];
        closestPair[1] = points[1];

        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                double currMin = dist(points[i], points[j]);
                if (currMin < minDist) {
                    minDist = currMin;
                    closestPair[0] = points[i];
                    closestPair[1] = points[j];
                }
            }
        }
        return closestPair;
    }

    private static double dist(Point p1, Point p2) {
        if (p1 == null || p2 == null) {
            return Double.MAX_VALUE;
        }

        double distX = Math.pow(p1.x - p2.x, 2);
        double distY = Math.pow(p1.y - p2.y, 2);
        double d = Math.sqrt(distX + distY);
        return d;
    }

    public static class Point {
        public double x = 0;
        public double y = 0;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
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
