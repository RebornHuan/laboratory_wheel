package my.skypiea.punygod.system_table;

import java.util.NoSuchElementException;

public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] values;
    private int N;

    public BinarySearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public Value get(Key key) {
        if (key == null) throw new NullPointerException("argument to rank() is null");
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return values[i];
        return null;
    }

    public boolean contains(Key key) {
        if (key == null) throw new NullPointerException("argument to rank() is null");
        return get(key) != null;
    }

    public int rank(Key key) {
        if (key == null) throw new NullPointerException("argument to rank() is null");
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    public void put(Key key, Value value) {
        int i = rank(key);
        if (i < N && keys[i].equals(key)) {
            values[i] = value;
            return;
        }

        if (N == keys.length) resize(2 * keys.length);

        for (int j = N; j > i; j--) {
            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }

        keys[i] = key;
        values[i] = value;
        N++;
    }

    public void delete(Key key) {
        if (key == null) throw new NullPointerException("argument to rank() is null");
        if (isEmpty()) return;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) != 0) return;

        for (int j = i; j < N - 1; j++) {
            keys[j] = keys[j + 1];
            values[j] = values[j + 1];
        }

        if (N > 0 && N == (keys.length) / 4) resize(keys.length / 2);
        N--;
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(min());
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(max());
    }

    public Key min() {
        if (isEmpty()) return null;
        return keys[0];
    }

    public Key max() {
        if (isEmpty()) return null;
        return keys[N - 1];
    }

    public Key select(int k) {
        if (k < 0 || k >= N) return null;
        return keys[k];
    }

    public Key floor(Key key) {
        if (key == null) throw new NullPointerException("argument to floor() is null");
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return key;
        if (i == 0) return null;
        return keys[i - 1];
    }

    public Key ceiling(Key key) {
        if (key == null) throw new NullPointerException("argument to floor() is null");
        int i = rank(key);
        if (i < N) return key;
        return null;
    }

    public int size(Key lo, Key hi) {
        if (lo == null) throw new NullPointerException("first argument to size() is null");
        if (hi == null) throw new NullPointerException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    private void resize(int capacity) {
        assert capacity >= N;
        Key[] keysTmp = (Key[]) new Comparable[capacity];
        Value[] valuesTmp = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            keysTmp[i] = keys[i];
            valuesTmp[i] = values[i];
        }
        keys = keysTmp;
        values = valuesTmp;
    }

}
