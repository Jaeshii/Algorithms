import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int n;
    private Node first, last;


    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException("the remove method is not supported"); }
        public Item next() {
            if (hasNext() != true) throw new java.util.NoSuchElementException("no next element to return");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() { return n == 0; }

    // return the number of items on the randomized queue
    public int size() { return n; }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (oldfirst != null) oldfirst.prev = first;
        if (n == 0) last = first;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("list is already empty");
        Item item = last.item;
        last = last.prev;
        if (last != null) last.next = null;
        n--;
        if (isEmpty()) first = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int randIndex = (int) (StdRandom.uniform(0, n));
        Node randItem = first;
        for (int i = 0; i < randIndex; i++) {
            randItem = randItem.next;
        }
        return randItem.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<>();
        test.enqueue("Hello");
        test.enqueue("Something");
        test.dequeue();

    }

}