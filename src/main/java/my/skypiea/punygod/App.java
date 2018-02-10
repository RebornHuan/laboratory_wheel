package my.skypiea.punygod;

/**
 * Hello world!
 */
public class App {

    private static int RESIZE_STAMP_BITS = 16;
    private static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;

    public static void main(String[] args) {

        int[] as = new int[4];
        as[0] =1;
        as[1]=2;
        int[] bs = new int[4];
        int i=0;
        bs[++i] =as[i];
        System.out.println(bs[1]);
    }

    static final int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }
}
