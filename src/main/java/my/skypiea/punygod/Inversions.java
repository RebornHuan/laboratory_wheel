package my.skypiea.punygod;

public class Inversions {

    public static long count(int[] a) {
        int[] aux = new int[a.length];
        return count(a, aux, 0, a.length - 1);
    }

    private static long count(int[] a, int[] aux, int lo, int hi) {
        long inversions = 0;
        if (hi <= lo) return 0;
        int mid = lo + (hi - lo) / 2;
        inversions += count(a, aux, lo, mid);
        inversions += count(a, aux, mid + 1, hi);
        inversions += merge(a, aux, lo, mid, hi);
        return inversions;
    }

    private static long merge(int[] a, int[] aux, int lo, int mid, int hi) {
        int inversions = 0;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j <= mid) a[k] = aux[i++];
            else if (aux[i] < aux[j]) {
                a[k] = aux[j++];
                /**
                 * 因为 aux[i...mid] 是单调的;
                 * aux[j,hi]是单调的;
                 * 所以如果aux[i]<aux[j]意味着 从 i...mid 的数都小于 aux[j];
                 * 在进行数值对的时候使用;
                 * **/
                inversions = mid - i + 1;
            } else
                a[k] = aux[i++];

        }
        return inversions;
    }
}
