package my.skypiea.punygod.system_table;

import java.util.NoSuchElementException;

/**
 * 完美平衡2-3树的插入变化准则:
 * 先将3-结点变成临时的4-结点然后变化
 * 1.如果插入的结点是根节点且是3-结点。 则直接变化成3个2-结点
 * 2.如果插入的结点是3-结点且父节点是2-结点。则将父节点变成3-结点
 * 3.如果插入的结点是3-结点且父节点是3-结点。要一次向上变化。
 * <p>
 * 红黑树标准定义:
 * 1.红色的链接只能是做链接；
 * 2.不能有一个结点同时和两个红色的链接连接；
 * 3.空链到根节点的黑色链接数相等。
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int N;
        private boolean color;

        public Node(Key key, Value value, Node left, Node right, int n, boolean color) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            N = n;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    public Value get(Key key) {
        if (key == null) throw new NullPointerException("argument to get() is null");
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.value;
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


    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    public void put(Key key, Value value) {
        if (key == null) throw new NullPointerException("first argument to put() is null");
        if (value == null) {

        }
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {
            return new Node(key, val, null, null, 1, RED);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.value = val;
        if (!isRed(x.left) && isRed(x.right)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) flipColors(x);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public boolean contains(Key key) {
        if (key == null) throw new NullPointerException("argument to contains() is null");
        return get(key) != null;
    }

    public void delete(Key key) {
        if (key == null) throw new NullPointerException("argument to delete() is null");
        if (!contains(key)) return;
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node x, Key key) {
        if (key.compareTo(x.key) < 0) {
            if (!isRed(x.left) && !isRed(x.left.left)) {
                x = moveRedLeft(x);
            }
            x = delete(x, key);
        } else {
            if (isRed(x.left))
                x = rotateRight(x);
            if (key.compareTo(x.key) == 0 && x.right == null) {
                return null;
            }
            if (!isRed(x.right) && !isRed(x.right.left))
                x = moveRedRight(x);
            if (key.compareTo(x.key) == 0) {
                Node h = min(x.right);
                x.key = h.key;
                x.value = h.value;
                h.right = deleteMin(h.right);
            } else x.right = delete(x.right, key);

        }
        return balance(x);
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMin(root);

        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return null;
        if (!isRed(x.left) && !isRed(x.left.left)) {
            moveRedLeft(x);
        }
        x.left = deleteMin(x.left);
        return balance(x);
    }

    // Assuming that x is red and both x.left and h.left.left
    // are black, make x.left or one of its children red.
    private Node moveRedLeft(Node x) {
        flipColors(x);
        if (isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            flipColors(x);
        }
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);

        if (!isEmpty()) root.color = BLACK;

    }

    private Node deleteMax(Node x) {
        if (isRed(x.left)) {
            x = rotateRight(x);
        }
        if (x.right == null) return null;

        if (!isRed(x.right) && !isRed(x.right.left)) {
            x = moveRedRight(x);
        }

        x.right = deleteMax(x.right);
        return balance(x);
    }

    // Assuming that x is red and both x.right and x.right.left
    // are black, make x.right or one of its children red.
    private Node moveRedRight(Node x) {
        flipColors(x);
        if (isRed(x.left) && isRed(x.left.left)) {
            rotateRight(x);
            flipColors(x);
        }
        return x;
    }

    private Node balance(Node x) {
        if (x == null) return null;
        if (isRed(x.right)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) flipColors(x);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }


}
