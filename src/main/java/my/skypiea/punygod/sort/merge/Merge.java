package my.skypiea.punygod.sort.merge;

import my.skypiea.punygod.sort.Example;

public class Merge extends Example {
    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) return;
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

    public static void indexSort(Comparable[] a) {
        int[] index = new int[a.length];
        int[] aux = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            index[i] = i;
        }
        sort(a, index, aux, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int[] index, int[] aux, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, index, aux, lo, mid);
        sort(a, index, aux, mid + 1, hi);
        merge(a, index, aux, lo, mid, hi);
    }

    private static void merge(Comparable[] a, int[] index, int[] aux, int lo, int mid, int hi) {
        if (less(a[mid], a[mid + 1])) return;

        int i = lo, j = mid + 1;
        for (int k = lo; k < hi; k++) {
            aux[k] = index[k];
        }
        for (int k = lo; k < hi; k++) {
            if (i > mid) index[k] = aux[j++];
            if (j > hi) index[k] = aux[i++];
            if (less(a[index[i]], a[index[j]])) index[k] = aux[i++];
            else index[k] = aux[j++];
        }
    }


}
