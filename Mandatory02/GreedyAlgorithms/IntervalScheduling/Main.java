package GreedyAlgorithms.IntervalScheduling;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;

// https://itu.kattis.com/courses/KSALDES1KU/KSALDES1KU-2024/assignments/nmchzj/problems/intervalscheduling

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Tuple> list = new ArrayList<>();
        int totalIntervals = sc.nextInt();

        int largestCountIntervals = 0;
        sc.nextLine();

        for (int i = 0; i < totalIntervals; i++) {
            int s = sc.nextInt();
            int f = sc.nextInt();
            sc.nextLine();

            Tuple temp = new Tuple(s, f);
            list.add(temp);
        }

        // Sort list of intervals by lowest f (end integer)
        TupleComparator comparer = new TupleComparator();
        list.sort(comparer);

        // Find the first interval with lowest end.
        Tuple lastInterval = list.get(0);
        largestCountIntervals++;

        for (Tuple tuple : list) {
            if (tuple.s >= lastInterval.f) {
                largestCountIntervals++;
                lastInterval = tuple;
            }
        }

        System.out.println(largestCountIntervals);

        sc.close();
    }

    // Custom Comparer Class
    static class TupleComparator implements Comparator<Tuple> {
        @Override
        public int compare(Tuple t1, Tuple t2) {
            return t1.f - t2.f;
        }
    }

    private static class Tuple {
        int s;
        int f;

        Tuple(int _s, int _f) {
            this.s = _s;
            this.f = _f;
        }
    }
}
