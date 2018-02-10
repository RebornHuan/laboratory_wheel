package my.skypiea.punygod.queues;

import java.util.NoSuchElementException;

public class IndexMaxPQ<Item extends Comparable<Item>> {
    private int N;
    private int maxN;
    private int[] pq;
    private int[] qp;

    /**
     * 放置元素数据
     **/
    private Item[] items;

    public IndexMaxPQ(int maxN) {
        this.maxN = maxN;
        qp = new int[maxN + 1];
        pq = new int[maxN + 1];
        items = (Item[]) new Object[maxN + 1];
        for (int i = 0; i < qp.length; i++) {
            qp[i] = -1;
        }
    }

    public void insert(int i, Item item) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        N++;
        qp[i] = N;
        pq[N] = i;
        items[i] = item;
        swim(N);
    }

    public void change(int i, Item item) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");

        int index = qp[i];
        items[i] = item;
        sink(index);
        swim(index);
    }

    public boolean contains(int k) {
        return qp[k] != -1;
    }

    public void delete(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exch(index, N--);
        sink(index);
        swim(index);
        items[i] = null;
        qp[i] = -1;
    }

    public Item max() {
        if (isEmpty()) return null;
        return items[pq[1]];
    }

    public int maxIndex() {
        if (isEmpty()) throw new NoSuchElementException("index is not in the priority queue");
        return pq[1];
    }

    public Item maxItem() {
        if (isEmpty()) throw new NoSuchElementException("index is not in the priority queue");
        return items[pq[1]];
    }

    public Item itemOf(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        return items[i];
    }

    public int delMax() {
        if (N == 0) throw new NoSuchElementException("index is not in the priority queue");
        int index = pq[1];
        exch(1, N--);
        sink(1);
        items[index] = null;
        qp[index] = -1;
        return -1;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private boolean less(int p, int q) {
        return items[pq[p]].compareTo(items[pq[q]]) < 0;
    }

    private void exch(int p, int q) {
        int tmp = pq[p];
        pq[p] = pq[q];
        pq[q] = tmp;
        qp[pq[p]] = p;
        qp[pq[q]] = q;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k < N) {
            int j = 2 * k;
            if (less(j, j + 1)) j++;
            if (!less(k, j)) break;
            k = j;
        }
    }

}
