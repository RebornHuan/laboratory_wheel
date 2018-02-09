package my.skypiea.punygod.sort.quick;

import my.skypiea.punygod.sort.Example;

/**
 * 快递三向切分(Bently、Mcilroy)
 * 将重复元素放置在子数组两端的方式实现一个信息量的排序算法;
 * 使用索引p、q使得a[lo...p-1]和a[q+1...hi]的元素都和a[lo]
 * 相等。
 * 使用两个索引i、j，使得a[p...i-1]小于a[lo]和a[j+1...q]大于a[lo]
 * 可以减少重复数据的排序问题
 */

public class QuickX extends Example {

    private static final int CUTOFF = 8;

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        int N = hi - lo + 1;
        if (hi <= lo + CUTOFF) {
            insertSort(a, lo, hi);
            return;
        }

        /**
         * 三取样切分
         */
        else if (N < 40) {
            int m = median3(a, lo, lo + N / 2, hi);
            exch(a, lo, m);
        } else {
            int eps = N / 8;
            int mid = lo + N / 2;
            int m1 = median3(a, lo, lo + eps, lo + eps + eps);
            int m2 = median3(a, mid - eps, mid, mid + eps);
            int m3 = median3(a, hi - eps - eps, hi - eps, hi);

            int m = median3(a, m1, m2, m3);
            exch(a, lo, m);
        }

        int i = lo, j = hi + 1;
        int p = lo, q = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v))
                if (i >= hi) break;
            while (less(v, a[--j]))
                if (j <= lo) break;
            if (i == j && eq(v, a[i])) {
                exch(a, ++p, i);
            }
            if (i >= j) break;

            exch(a, i, j);
            if (eq(v, a[i])) exch(a, ++q, i);
            if (eq(v, a[j])) exch(a, --q, j);
        }
        i = j + 1;
        for (int k = lo; k < q; k++) {
            exch(a, k, j--);
        }
        for (int k = hi; k > q; k--) {
            exch(a, k, i++);
        }

        sort(a, lo, j);
        sort(a, i, hi);
    }

    private static boolean eq(Comparable v, Comparable w) {
        return v.compareTo(w) == 0;
    }

    private static void insertSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    /**
     * 三取样切分 -- 即如何找到合适的快排中比较的那个值
     *
     * @param a
     * @param i
     * @param j
     * @param k
     * @return
     */
    private static int median3(Comparable[] a, int i, int j, int k) {
        return (less(a[i], a[j])) ? (less(a[j], a[k])) ? k : (less(a[i], a[k])) ? k : i :
                (less(a[i], a[k])) ? i : (less(a[j], a[k])) ? k : j;
    }


}
