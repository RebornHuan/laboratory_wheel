package my.skypiea.punygod.system_table;

import my.skypiea.punygod.queues.Queue;

public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;
    private int N;
    private int M;
    private Key[] keys;
    private Value[] values;

    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int m) {
        M = m;
        keys = (Key[]) new Object[m];
        values = (Value[]) new Object[m];
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean contains(Key key) {
        if (key == null) throw new NullPointerException("argument to contains() is null");
        return get(key) != null;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key) {
        if (key == null) throw new NullPointerException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key))
                return values[i];
        }

        return null;
    }

    public void put(Key key, Value value) {
        if (key == null) throw new NullPointerException("first argument to put() is null");
        if (value == null) {
            delete(key);
            return;
        }
        if (N >= M / 2) resize(2 * M);
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
        }
        keys[i] = key;
        values[i] = value;
        N++;
    }

    private void resize(int capacity) {
        LinearProbingHashST<Key, Value> tmp = new LinearProbingHashST(capacity);
        for (int i = 0; i < M; i++) {
            tmp.put(keys[i], values[i]);
        }
        this.N = tmp.N;
        this.M = tmp.M;
        this.keys = tmp.keys;
        this.values = tmp.values;
    }

    public void delete(Key key) {
        if (key == null) throw new NullPointerException("argument to delete() is null");
        if (!contains(key)) return;

        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }
        keys[i] = null;
        values[i] = null;
        N--;
        i = (i + 1) % M;
        while (keys[i] != null) {
            Key keyToRehash = keys[i];
            Value valueToRehash = values[i];
            keys[i] = null;
            values[i] = null;
            put(keyToRehash, valueToRehash);
            i = (i + 1) % M;
        }
        if (N > 0 && N <= M / 8) resize(M / 2);
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
        return queue;
    }
}
