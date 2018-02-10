package my.skypiea.punygod.sort.quick;

import my.skypiea.punygod.sort.Example;

public class Quick extends Example {
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v))
                if (i >= hi) break;
            while (less(v, a[j--]))
                if (j <= lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        return j;
    }

    public static Comparable select(Comparable[] a, int k) {
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int i = partition(a, lo, hi);
            if (i > k) hi = i - 1;
            else if (i < k) lo = i + 1;
            else return a[i];
        }
        return a[lo];
    }
}
