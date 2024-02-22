class Node<Item> {
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