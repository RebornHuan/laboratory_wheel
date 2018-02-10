package my.skypiea.punygod.system_table;

public class SequentialSearchST<Key extends Comparable<Key>, Value> extends ST<Key, Value> {
    private Node first;
    private int N;

    private class Node {
        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.value = value;
            this.key = key;
            this.next = next;
        }
    }

    @Override
    public void put(Key key, Value val) {
        if (key == null) throw new NullPointerException("argument to delete() is null");
        if (val == null) {
            delete(key);
            return;
        }
        for (Node node = first; node != null; node = node.next) {
            if (key.equals(node.key)) node.value = val;
        }
        first = new Node(key, val, first);
        N++;
    }

    @Override
    public Value get(Key key) {
        if (key == null) throw new NullPointerException("argument to delete() is null");
        for (Node node = first; node != null; node = node.next) {
            if (key.equals(node.key)) return node.value;
        }
        return null;
    }

    public void delete(Key key) {
        if (key == null) throw new NullPointerException("argument to delete() is null");
        first = delete(first, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        if (x.key.equals(key)) return x.next;
        return delete(x.next, key);
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<Key> keys() {
        return null;
    }
}
