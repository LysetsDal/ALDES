package DivideAndConquer.MergeSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MergeSortAlgo<T extends Comparable<T>> {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Integer> input = new ArrayList<>();
        MergeSortAlgo<Integer> algo = new MergeSortAlgo<>();

        int count = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < count; i++) {
            int tmp = sc.nextInt();
            input.add(tmp);
        }
        sc.nextLine();

        List<Integer> output = algo.MergeSort(input);

        for (Integer integer : output) {
            System.out.print(integer + " ");
        }

        sc.close();
    }

    public List<T> MergeSort(List<T> list) {
        if (list.size() == 1) {
            return list;
        }

        List<T> a = list.subList(0, list.size() / 2);
        List<T> b = list.subList(list.size() / 2, list.size());

        return Merge(MergeSort(a), MergeSort(b));
    }

    public List<T> Merge(List<T> listA, List<T> listB) {
        List<T> res = new ArrayList<T>();
        int i = 0;
        int j = 0;

        while (i < listA.size() && j < listB.size()) {
            if (listA.get(i).compareTo(listB.get(j)) <= 0) {
                res.add(listA.get(i));
                i++;
            } else {
                res.add(listB.get(j));
                j++;
            }
        }

        // Add remaining elements from listA
        while (i < listA.size()) {
            res.add(listA.get(i));
            i++;
        }

        // Add remaining elements from listB
        while (j < listB.size()) {
            res.add(listB.get(j));
            j++;
        }

        return res;
    }

}
