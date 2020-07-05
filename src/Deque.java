import edu.princeton.cs.algs4.LinkedStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
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

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() { return n == 0; }

    // return the number of items on the deque
    public int size() { return n; }

    // add the item to the front
    public void addFirst(Item item) {
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

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("list is already empty");
        Item item = first.item;
        first = first.next;
        if (first != null) first.prev = null;
        n--;
        if (isEmpty()) last = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("list is already empty");
        Item item = last.item;
        last = last.prev;
        if (last != null) last.next = null;
        n--;
        if (isEmpty()) first = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new ListIterator(); }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> test = new Deque<>();
        test.addFirst("Hi");
        test.addFirst("Wow");
        test.addFirst("Interesting");
        test.addLast("Bob");
        test.removeFirst();
        test.removeLast();

        for (String s : test)
            System.out.println(s);

    }

}