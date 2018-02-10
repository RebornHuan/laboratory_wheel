package my.skypiea.punygod.system_table;

import my.skypiea.punygod.queues.Queue;

import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int N;

        public Node(Key key, Value value, Node left, Node right, int n) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            N = n;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Value get(Key key) {
        if (key == null) return null;
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.value;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void put(Key key, Value val) {
        if (key == null) throw new NullPointerException("first argument to put() is null");
        if (val == null) {

        }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {
            return new Node(key, val, null, null, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.value = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        if (key == null) throw new NullPointerException("argument to delete() is null");
    }

    private Node delete(Node x, Key key) {
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            Node t = x;
            x = min(t);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    private Node deleteMin(Node x) {
        if (x == null) return null;
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x == null) return null;
        if (x.left == null) return x;
        else return min(x.left);
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x == null) return null;
        if (x.right == null) return x;
        else return max(x.right);
    }

    private Node deleteMax(Node x) {
        if (x == null) return null;
        if (x.right == null) return x.left;
        else x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Key floor(Key key) {
        if (key == null) throw new NullPointerException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("called floor() with empty symbol table");
        return floor(root, key).key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return floor(x.left, key);
        else if (cmp == 0) return x;
        else {
            Node t = floor(x.right, key);
            return (t == null) ? x : t;
        }
    }

    public Key ceiling(Key key) {
        if (key == null) throw new NullPointerException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
        return ceiling(root, key).key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        else if (cmp > 0) return ceiling(x.right, key);
        else {
            Node t = ceiling(x.left, key);
            return (t == null) ? x : t;
        }
    }

    public Key select(int k) {
        if (k < 0 || k >= size()) throw new IllegalArgumentException();
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t < k) return select(x, k - t - 1);
        else if (t > k) return select(x.left, k);
        else return x;
    }

    /**
     * 小于 key（不包括key）
     *
     * @param key
     * @return
     */
    public int rank(Key key) {
        if (key == null) throw new NullPointerException("argument to rank() is null");
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x, key);
        else if (cmp > 0) return size(x.left) + 1 + rank(x.right, key);
        else return size(x.left);
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new NullPointerException("first argument to keys() is null");
        if (hi == null) throw new NullPointerException("second argument to keys() is null");
        Queue<Key> queue = new Queue();
        keys(root, queue, lo, hi);
        return queue;
    }

    public void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo > 0) keys(x.right, queue, lo, hi);
        else if (cmphi < 0) keys(x.left, queue, lo, hi);
        else {
            queue.enqueue(x.key);
            keys(x.right, queue, lo, hi);
            keys(x.left, queue, lo, hi);
        }
    }

    /**
     * [lo,hi]
     *
     * @param lo
     * @param hi
     * @return
     */
    public int size(Key lo, Key hi) {
        if (lo == null) throw new NullPointerException("first argument to keys() is null");
        if (hi == null) throw new NullPointerException("second argument to keys() is null");
        if (lo.compareTo(hi) > 0) return 0;

        if (contains(hi)) return rank(hi) - rank(lo) + 1;

        return rank(hi) - rank(lo);

    }
}
