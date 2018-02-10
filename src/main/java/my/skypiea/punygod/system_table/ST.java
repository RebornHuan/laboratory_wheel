package my.skypiea.punygod.system_table;

public abstract class ST<Key extends Comparable<Key>, Value> {
    public ST() {
    }

    public abstract void put(Key key, Value val);

    public abstract Value get(Key key);

    public void delete(Key key) {
        put(key, null);
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public abstract int size();

    public boolean isEmpty() {
        return size() == 0;
    }


    public abstract Iterable<Key> keys();


}
