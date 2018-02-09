package my.skypiea.punygod.sort;

public class Example {


    protected static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0 ? true : false;
    }

    protected static void exch(Comparable[] a, int i, int j) {
        if (i == j) return;
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
        }
        System.out.println();

    }

    protected static boolean isSorted(Comparable[] a) {
        if (a.length < 2) return true;
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

}
