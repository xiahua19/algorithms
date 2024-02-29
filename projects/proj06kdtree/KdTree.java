/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;
    private double minDist;
    private Point2D minPoint;

    private class Node {
        private Point2D p;
        private boolean isVertical;
        private Node left;
        private Node right;

        public Node(Point2D p, boolean isVertical, Node left, Node right) {
            this.p = p;
            this.isVertical = isVertical;
            this.left = left;
            this.right = right;
        }
    }

    // construct an empty set of points
    public KdTree() {
        this.root = null;
        this.size = 0;
        this.minDist = Double.MAX_VALUE;
        this.minPoint = null;
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
            this.root = insert2(p, this.root, new Node(p, false, null, null));
            this.size++;
        }
    }

    private Node insert2(Point2D p, Node node, Node parent) {
        if (node == null) {
            return new Node(p, !parent.isVertical, null, null);
        }
        else {
            if ((node.isVertical && p.x() < node.p.x()) || (!node.isVertical
                    && p.y() < node.p.y())) {
                node.left = insert2(p, node.left, node);
            }
            else if ((node.isVertical && p.x() >= node.p.x()) || (!node.isVertical
                    && p.y() >= node.p.y())) {
                node.right = insert2(p, node.right, node);
            }
        }
        return node;
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
                return contains2(p, node.left);
            }
            else if ((node.isVertical && p.x() >= node.p.x()) || (!node.isVertical
                    && p.y() >= node.p.y())) {
                return contains2(p, node.right);
            }
            else {
                return true;
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw2(this.root, null);
    }

    private void draw2(Node node, Point2D p) {
        if (node != null) {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(node.p.x(), node.p.y());
            StdDraw.setPenRadius(0.002);
            if (node.isVertical) {
                StdDraw.setPenColor(StdDraw.RED);
                if (p == null) {
                    StdDraw.line(node.p.x(), 0, node.p.x(), 1);
                }
                else {
                    if (node.p.y() > p.y()) {
                        StdDraw.line(node.p.x(), p.y(), node.p.x(), 1);
                    }
                    else {
                        StdDraw.line(node.p.x(), 0, node.p.x(), p.y());
                    }
                }
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                if (node.p.x() < p.x()) {
                    StdDraw.line(0, node.p.y(), p.x(), node.p.y());
                }
                else {
                    StdDraw.line(p.x(), node.p.y(), 1, node.p.y());
                }
            }
            draw2(node.left, node.p);
            draw2(node.right, node.p);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> pointList = new ArrayList<Point2D>();
        range2(rect, this.root, pointList);
        return pointList;
    }

    private void range2(RectHV rect, Node node, ArrayList<Point2D> pointList) {
        if (node == null) {
            return;
        }

        if (rect.contains(node.p)) {
            pointList.add(node.p);
        }

        if (node.isVertical) {
            if (rect.xmax() <= node.p.x()) {
                range2(rect, node.left, pointList);
            }
            else if (rect.xmin() >= node.p.x()) {
                range2(rect, node.right, pointList);
            }
            else {
                range2(rect, node.left, pointList);
                range2(rect, node.right, pointList);
            }
        }
        else {
            if (rect.ymin() >= node.p.y()) {
                range2(rect, node.right, pointList);
            }
            else if (rect.ymax() <= node.p.y()) {
                range2(rect, node.left, pointList);
            }
            else {
                range2(rect, node.left, pointList);
                range2(rect, node.right, pointList);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        nearest2(p, this.root);
        return this.minPoint;
    }

    private void nearest2(Point2D p, Node node) {
        if (node == null) {
            return;
        }
        double dist = node.p.distanceTo(p);
        if (dist < this.minDist) {
            this.minPoint = node.p;
        }
        if ((node.isVertical && p.x() < node.p.x()) || (!node.isVertical
                && p.y() < node.p.y())) {
            nearest2(p, node.left);
        }
        else if ((node.isVertical && p.x() >= node.p.x()) || (!node.isVertical
                && p.y() >= node.p.y())) {
            nearest2(p, node.right);
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.25, 0.25));
        kdTree.insert(new Point2D(0.1, 0.1));
        StdOut.println(kdTree.contains(new Point2D(0.25, 0.25)));

        kdTree.draw();
    }
}
