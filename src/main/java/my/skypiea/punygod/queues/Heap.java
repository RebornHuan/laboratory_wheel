package my.skypiea.punygod.queues;


public class Heap {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int k = N / 2; k > 1; k--) {
            sink(a, k, N);
        }

        while (N > 1) {
            exch(a, 1, N--);
            sink(a, 1, N);
        }
    }

    private static void sink(Comparable[] pq, int k, int N) {
        while (2 * k < N) {
            int j = 2 * k;
            if (j < N && less(pq, j, j++)) j++;
            if (!less(k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }


    private static void exch(Object[] pq, int i, int j) {
        Object temp = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = temp;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

}
