/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // items store in contents
    private Item[] contents;

    // the count of items stored in the contents
    private int count;

    // the left pointer, which points to the first item in contents
    private int left;

    // the right pointer, which points to the next to the last item in contents
    private int right;

    // The initial capacity of the contents
    private static final int INIT_CAPACITY = 32;

    // construct an empty deque
    public Deque() {
        contents = (Item[]) new Object[INIT_CAPACITY];
        count = 0;
        left = 0;
        right = 0;
    }

    // resize contents
    private void resize(int capacity) {
        assert capacity >= count;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; ++i) {
            copy[i] = contents[(left + i) % count];
        }
        contents = copy;
        left = 0;
        right = count;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // the items' count in contents
    public int size() {
        return count;
    }

    // is the deque full?
    private boolean isFull() {
        return contents.length == count;
    }


    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isFull()) {
            resize(2 * count);
        }

        left -= 1;
        if (left < 0) {
            left += contents.length;
        }
        contents[left] = item;

        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isFull()) {
            resize(2 * contents.length);
        }
        contents[right] = item;
        right = (right + 1) % contents.length;

        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = contents[left];
        left = (left + 1) % contents.length;

        count--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        right -= 1;
        if (right < 0) {
            right += contents.length;
        }

        count--;
        return contents[right];
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an array iterator, from left to right-1
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < count;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = contents[(left + i) % contents.length];
            ++i;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        int i = 0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            deque.addLast(item);
            deque.addFirst(item);
            i += 2;
        }
        StdOut.println(i);
        StdOut.println("(" + deque.size() + " left on queue)");

        deque.removeFirst();
        StdOut.println("(" + deque.size() + " left on queue)");

        deque.removeLast();
        StdOut.println("(" + deque.size() + " left on queue)");
    }

}
