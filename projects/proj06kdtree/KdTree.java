/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private Node root;
    private int size;

    private class Node {
        private Point2D p;
        private boolean isVertical;
        private Node left;
        private Node right;
        private Node parent;

        public Node(Point2D p, boolean isVertical) {
            p = p;
            isVertical = isVertical;
            left = null;
            right = null;
            parent = null;
        }

        public Node(Point2D p, boolean isVertical, Node left, Node right, Node parent) {
            p = p;
            isVertical = isVertical;
            left = left;
            right = right;
            parent = parent;
        }
    }

    // construct an empty set of points
    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!this.contains(p)) {
            if (this.root == null) {
                this.root = new Node(p, true, null, null, null);
            }
            else {
                if (p.x() < this.root.p.x()) {
                    insert2(p, this.root.left, this.root);
                }
                else {
                    insert2(p, this.root.right, this.root);
                }
            }
            this.size++;
        }
    }

    private void insert2(Point2D p, Node node, Node parent) {
        if (node == null) {
            node = new Node(p, !parent.isVertical, null, null, parent);
        }
        else {
            if ((node.isVertical && p.x() < node.p.x()) || (!node.isVertical
                    && p.y() < node.p.y())) {
                insert2(p, node.left, node);
            }
            else if ((node.isVertical && p.x() >= node.p.x()) || (!node.isVertical
                    && p.y() >= node.p.y())) {
                insert2(p, node.right, node);
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return contains2(p, this.root);
    }

    private boolean contains2(Point2D p, Node node) {
        if (node == null) {
            return false;
        }
        else if (node.p.compareTo(p) == 0) {
            return true;
        }
        else {
            if ((node.isVertical && p.x() < node.p.x()) || (!node.isVertical
                    && p.y() < node.p.y())) {
                contains2(p, node.left);
            }
            else if ((node.isVertical && p.x() >= node.p.x()) || (!node.isVertical
                    && p.y() >= node.p.y())) {
                contains2(p, node.right);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
