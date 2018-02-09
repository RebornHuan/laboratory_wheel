package my.skypiea.punygod.sort.merge;

import my.skypiea.punygod.sort.Example;

public class MergeBU extends Example {
    private static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        int N = a.length;
        for (int sz = 1; sz < N - sz; sz += sz) {
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
            }
        }
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
}
