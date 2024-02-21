/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Node<Item> {
    private Item data;
    private Node<Item> next;
    private Node<Item> prev;

    public Node(Item data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public Item getData() {
        return this.data;
    }

    public Node<Item> getNext() {
        return this.next;
    }

    public Node<Item> getPrev() {
        return this.prev;
    }

    public void setData(Item data) {
        this.data = data;
    }

    public void setNext(Node<Item> next) {
        this.next = next;
    }

    public void setPrev(Node<Item> prev) {
        this.prev = prev;
    }
}


public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    // construct an empty deque
    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item should not be null");
        }
        Node<Item> node = new Node<Item>(item);
        node.setNext(this.head);
        if (this.size() != 0)
            this.head.setPrev(node);
        else
            this.tail = node;
        this.head = node;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item should not be null");
        }
        Node<Item> node = new Node<Item>(item);
        if (this.size() != 0)
            this.tail.setNext(node);
        else
            this.head = node;
        node.setPrev(this.tail);
        this.tail = node;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("there is no any item in deque");
        }
        Item data = this.head.getData();
        this.head = this.head.getNext();
        if (this.head != null)
            this.head.setPrev(null);
        size--;
        if (this.size() == 1) {
            this.tail = this.head;
        }
        else if (this.size() == 0) {
            this.tail = null;
            this.head = null;
        }
        return data;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("there is no any item in deque");
        }
        Item data = this.tail.getData();
        this.tail = this.tail.getPrev();
        if (this.tail != null)
            this.tail.setNext(null);
        size--;
        if (this.size() == 1) {
            this.head = tail;
        }
        else if (this.size() == 0) {
            this.head = null;
            this.tail = null;
        }
        return data;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        Iterator<Item> iter = new Iterator<Item>() {
            private Node<Item> curr = head;

            public boolean hasNext() {
                return curr != null;
            }

            public void remove() {
                throw new UnsupportedOperationException("unsupported remove operation on deque");
            }

            public Item next() {
                if (curr == null) {
                    throw new NoSuchElementException("there is no any item in deque");
                }
                Item data = curr.getData();
                curr = curr.getNext();
                return data;
            }
        };
        return iter;
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(-1);
        deque.addLast(-2);
        deque.addLast(-3);
        deque.removeLast();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        deque.addLast(4);
        // deque.removeFirst();
        StdOut.println("size of deque is " + deque.size());
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }
    }

}
