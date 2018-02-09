package my.skypiea.punygod.sort.quick;

import my.skypiea.punygod.sort.Example;

/**
 * 维护一个指针lt使得a[lo...lt-1]中的元素都小于v;
 * 维护一个指针gt使得a[gt+1...hi]中的元素都大于v;
 * 维护一个指针i使得a[lt...i-1]中的元素都等于v;
 * a[i,gt]未知大小;
 */
public class Quick3way extends Example {

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        Comparable v = a[lo];
        int lt = lo, i = lo + 1, gt = hi;
        while (i <= gt) {
            int cmp = v.compareTo(a[i]);
            if (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else i++;
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }
}
