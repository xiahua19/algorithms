/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> head;
    private Node<Item> tail;
    private Node<Item>[] all_nodes;
    private int size;
    private int size_;
    private int capacity;

    private void resize() {
        int new_capacity = 2 * capacity;
        Node<Item>[] new_nodes = (Node<Item>[]) new Object[new_capacity];
        for (int i = 0; i < capacity; ++i) {
            new_nodes[i] = all_nodes[i];
        }
        all_nodes = new_nodes;
        capacity = new_capacity;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        head = null;
        tail = null;
        size = 0;
        size_ = 0;
        capacity = 100;
        all_nodes = (Node<Item>[]) new Object[capacity];
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

        all_nodes[size] = node;
        size++;
        size_++;

        if (size_ == capacity) {
            resize();
        }
    }

    // remove and return a random item
    public Item dequeue() {
        int random_pos = StdRandom.uniformInt(size_);
        while (all_nodes[random_pos] == null) {
            random_pos = StdRandom.uniformInt(size_);
        }
        Node<Item> removed_node = all_nodes[random_pos];
        Node<Item> prev_node = removed_node.getPrev();
        Node<Item> next_node = removed_node.getNext();
        if (prev_node == null && next_node == null) {
            this.head = null;
            this.tail = null;
        }
        else if (prev_node == null) {
            this.head = next_node;
            next_node.setPrev(null);
        }
        else if (next_node == null) {
            this.tail = prev_node;
            prev_node.setNext(null);
        }

        all_nodes[random_pos] = null;
        size--;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        int random_pos = StdRandom.uniformInt(size_);
        while (all_nodes[random_pos] == null) {
            random_pos = StdRandom.uniformInt(size_);
        }
        return all_nodes[random_pos].getData();
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return null;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}