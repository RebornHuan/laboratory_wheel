package my.skypiea.punygod.sort.merge;

import my.skypiea.punygod.sort.Example;

public class MergeX extends Example {
    private static final int CUT_OFF = 7;

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo + CUT_OFF) {
            insertSort(a, lo, hi);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        if (less(a[mid], a[mid + 1])) return;
        int i = lo, j = mid + 1;
        for (int k = lo; k < hi; k++) {
            aux[k] = a[k];
        }
        for (int k = lo; k < hi; k++) {
            if (i > mid) a[k] = aux[j++];
            if (j > hi) a[k] = aux[i++];
            if (less(a[i], a[j])) a[k] = aux[i++];
            else a[k] = aux[j++];
        }
    }

    private static void insertSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(j, j - 1); j--) {
                exch(a, j, j - 1);
            }
        }
    }
}
