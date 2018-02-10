package my.skypiea.punygod.queues;

import java.util.NoSuchElementException;

public class IndexMinPQ<Item extends Comparable<Item>> {
    private int N;
    private int maxN;

    /**
     * 二叉堆
     **/
    private int[] pq;
    private int[] qp;

    private Item[] items;

    public IndexMinPQ(int maxN) {
        this.maxN = maxN;
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 1; i < maxN + 1; i++) {
            qp[i] = -1;
        }
        items = (Item[]) new Object[maxN + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public boolean contains(int i) {
        return qp[i] != -1;
    }

    public void insert(int i, Item item) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");

        items[i] = item;
        pq[N] = i;
        qp[i] = N;
        swim(N);
    }

    public int minIndex() {
        if (isEmpty()) throw new NoSuchElementException("index is not in the priority queue");
        return pq[1];
    }

    public Item minItem() {
        if (isEmpty()) throw new NoSuchElementException("index is not in the priority queue");
        return items[pq[1]];
    }

    public int delMin() {
        if (isEmpty()) throw new NoSuchElementException("index is not in the priority queue");
        int index = pq[1];
        exch(1, N--);
        sink(1);
        items[index] = null;
        qp[index] = -1;
        return index;
    }

    public void change(int i, Item item) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        int index = pq[i];
        items[i] = item;
        swim(index);
        sink(index);
    }


    private boolean greater(int p, int q) {
        return items[p].compareTo(items[q]) > 0;
    }

    private void exch(int p, int q) {
        int temp = pq[p];
        pq[p] = pq[q];
        pq[q] = temp;
        qp[pq[p]] = p;
        qp[pq[q]] = q;
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k < N) {
            int j = 2 * k;
            if (greater(j, j + 1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
}
