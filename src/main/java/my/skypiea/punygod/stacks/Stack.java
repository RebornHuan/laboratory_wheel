package my.skypiea.punygod.stacks;

import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {

    private Node first;
    private int N;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(Item item) {
        Node oldFirst = first;
        Node node = new Node();
        node.item = item;
        first = node;
        first.next = oldFirst;
        N++;
    }

    public Item pop() {
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }

    private class StackIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current == null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    @Override
    public Iterator<Item> iterator() {
        return new StackIterator();
    }


    private class Node {
        Item item;
        Node next;
    }
}
