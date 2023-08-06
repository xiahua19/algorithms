/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // items store in contents
    private Item[] contents;

    // the left pointer, which points to the first item in contents
    private int left;

    // the right pointer, which points to the next to the last item in contents
    private int right;

    // the count of items stored in the contents
    private int count;

    // The initial capacity of the contents
    private static final int INIT_CAPACITY = 32;

    // construct an empty randomized queue
    public RandomizedQueue() {
        contents = (Item[]) new Object[INIT_CAPACITY];
        count = 0;
        left = 0;
        right = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // is the deque full?
    private boolean isFull() {
        return contents.length == count;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
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

    // add the item
    public void enqueue(Item item) {
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

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniformInt(0, count);
        Item item = contents[(left + index) % contents.length];

        for (int i = index; i < count - 1; ++i) {
            contents[(left + i) % contents.length] = contents[(left + i + 1) % contents.length];
        }

        right -= 1;
        if (right < 0) {
            right += contents.length;
        }

        count--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int index = StdRandom.uniformInt(0, count);
        Item item = contents[(left + index) % contents.length];
        return item;
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

    public static void main(String[] args) {
        RandomizedQueue<String> deque = new RandomizedQueue<>();
        int i = 0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            deque.enqueue(item);
            i += 1;
        }
        StdOut.println(i);
        StdOut.println("(" + deque.size() + " left on queue)");

        deque.dequeue();
        StdOut.println("(" + deque.size() + " left on queue)");

        deque.dequeue();
        StdOut.println("(" + deque.size() + " left on queue)");
    }
}
