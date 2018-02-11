package my.skypiea.punygod.system_table;

import my.skypiea.punygod.queues.Queue;

public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int N;
    private int M;
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    public SeparateChainingHashST(int m) {
        M = m;
        st = new SequentialSearchST[m];
        for (int i = 0; i < m; i++) {
            st[i] = new SequentialSearchST();
        }
    }

    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> tmp = new SeparateChainingHashST(chains);
        for (int i = 0; i < N; i++) {
            for (Key key : st[i].keys()) {
                tmp.put(key, st[i].get(key));
            }
        }
        this.N = tmp.N;
        this.M = tmp.M;
        this.st = tmp.st;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Value get(Key key) {
        if (key == null) throw new NullPointerException("argument to get() is null");
        int i = hash(key);
        return st[i].get(key);
    }

    public boolean contains(Key key) {
        if (key == null) throw new NullPointerException("argument to contains() is null");
        return get(key) != null;
    }

    public void put(Key key, Value val) {
        if (key == null) throw new NullPointerException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        if (N >= 10 * M) resize(2 * M);
        if (!contains(key)) N++;
        int i = hash(key);
        st[i].put(key, val);
    }

    public void delete(Key key) {
        if (key == null) throw new NullPointerException("argument to delete() is null");
        int i = hash(key);
        if (contains(key)) N--;
        st[i].delete(key);
        if (M > INIT_CAPACITY && N <= 2 * M) resize(M / 2);
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    }


}

