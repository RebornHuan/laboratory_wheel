package my.skypiea.punygod.stacks;

import java.util.Iterator;

public class ResizingArrayStack<Item> implements Iterable<Item> {
    private Item[] items;
    private int N;

    public ResizingArrayStack(int capacity) {
        this.items = (Item[]) new Object[capacity];
    }

    public void push(Item item) {
        if (N == items.length) resize(2 * items.length);
        items[N++] = item;
    }

    public Item pop() {
        Item item = items[--N];
        items[N] = null;
        if (N > 0 && N == items.length / 4) resize(items.length / 2);
        return item;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void resize(int max) {
        Item[] tmp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            tmp[i] = items[i];
        }
        items = tmp;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {

        private int i = N;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            return items[--i];
        }
    }
}
