package my.skypiea.punygod.queues;

import java.util.Comparator;
import java.util.Iterator;

public class MinPQ<Key> implements Iterable<Key> {
    private Key[] pq;
    private int N;
    private Comparator<Key> comparator;

    public MinPQ() {
        this(1);
    }

    public MinPQ(int max) {
        this(max, null);
    }

    public MinPQ(int max, Comparator<Key> comparator) {
        pq = (Key[]) new Object[max + 1];
        N = max + 1;
        this.comparator = comparator;
    }

    public MinPQ(Key[] a) {
        int N = a.length;
        pq = (Key[]) new Object[N + 1];
        for (int i = 0; i < N; i++) {
            pq[i + 1] = a[i];
        }
        for (int k = N / 2; k > 1; k = k / 2) {
            sink(k);
        }
    }

    public void insert(Key key) {
        if (N > pq.length - 1) resize(2 * pq.length);
        pq[++N] = key;
        swim(N);
    }

    public Key min() {
        if (isEmpty()) return null;
        return pq[1];
    }

    private void resize(int capacity) {
        assert capacity > N;
        Key[] tmp = (Key[]) new Object[capacity + 1];
        for (int i = 1; i < N + 1; i++) {
            tmp[i] = pq[i];
        }
        pq = tmp;
    }

    public Key delMin() {
        if (isEmpty()) return null;
        Key key = pq[1];
        exch(1, N--);
        pq[N + 1] = null;
        sink(1);
        if (N > 0 && N == (pq.length - 1) / 4) resize((pq.length - 1) / 2);
        return key;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private boolean greater(int p, int q) {
        if (comparator == null) {
            return (((Comparable<Key>) pq[p]).compareTo(pq[q]) > 0);
        } else {
            return comparator.compare(pq[p], pq[q]) > 0;
        }
    }

    private void exch(int p, int q) {
        Key tmp = pq[p];
        pq[p] = pq[q];
        pq[q] = tmp;
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


    @Override
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {

        private MinPQ<Key> copy;

        public HeapIterator() {
            if (comparator == null) {
                copy = new MinPQ<>(size());
            } else {
                copy = new MinPQ<>(size(), comparator);
            }
            for (int i = 1; i < N + 1; i++) {
                copy.insert(pq[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Key next() {
            return copy.delMin();
        }
    }
}
