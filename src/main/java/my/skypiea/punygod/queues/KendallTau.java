package my.skypiea.punygod.queues;

import my.skypiea.punygod.Inversions;

/**
 * 两个排列之间的Kendall tau距离就是在
 * 两组数列中顺序不同的数对的数目。
 * <p>
 * 某个排列和标准排列的Kendall tau距离就是其中逆序数对的数量。
 */
public class KendallTau {
    public static long distance(int[] a, int[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array dimensions disagree");
        }
        int n = a.length;
        int[] ainv = new int[n];
        for (int i = 0; i < n; i++) {
            ainv[a[i]] = i;
        }

        int[] bnew = new int[n];
        for (int i = 0; i < n; i++)
            bnew[i] = ainv[b[i]];

        return Inversions.count(bnew);

    }

}
