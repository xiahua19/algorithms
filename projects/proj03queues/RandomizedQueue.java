/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> head;
    private Node<Item> tail;
    private Node<Item>[] allNodes;
    private int size;
    private int sizeN;
    private int capacity;

    private class Node<Item> {
        private Item data;
        private Node<Item> next;
        private Node<Item> prev;

        public Node() {
            this.data = null;
            this.next = null;
            this.prev = null;
        }

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

    // construct an empty randomized queue
    public RandomizedQueue() {
        head = null;
        tail = null;
        size = 0;
        sizeN = 0;
        capacity = 100;
        allNodes = (Node<Item>[]) new Node[capacity];
    }

    private void resize() {
        int newCapacity = 2 * capacity;
        Node<Item>[] newNodes = (Node<Item>[]) new Node[newCapacity];
        for (int i = 0; i < capacity; ++i) {
            newNodes[i] = allNodes[i];
        }
        allNodes = newNodes;
        capacity = newCapacity;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
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

        allNodes[size] = node;
        size++;
        sizeN++;

        if (sizeN == capacity) {
            resize();
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("RandomizeQueue is empty");
        }
        int randomPos = StdRandom.uniformInt(sizeN);
        while (allNodes[randomPos] == null) {
            randomPos = StdRandom.uniformInt(sizeN);
        }
        Node<Item> removedNode = allNodes[randomPos];
        Node<Item> prevNode = removedNode.getPrev();
        Node<Item> nextNode = removedNode.getNext();
        if (prevNode == null && nextNode == null) {
            this.head = null;
            this.tail = null;
        }
        else if (prevNode == null) {
            this.head = nextNode;
            nextNode.setPrev(null);
        }
        else if (nextNode == null) {
            this.tail = prevNode;
            prevNode.setNext(null);
        }
        else {
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
        }
        allNodes[randomPos] = null;
        size--;
        return removedNode.getData();
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("RandomizeQueue is empty");
        }
        int randomPos = StdRandom.uniformInt(sizeN);
        while (allNodes[randomPos] == null) {
            randomPos = StdRandom.uniformInt(sizeN);
        }
        return allNodes[randomPos].getData();
    }

    // return an independent iterator over items in random order
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
        RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();
        deque.enqueue(1);
        deque.enqueue(2);
        deque.enqueue(3);
        deque.enqueue(4);
        deque.enqueue(5);
        StdOut.println("remove element " + deque.dequeue());
        StdOut.println("size of deque is " + deque.size());
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }
    }

}