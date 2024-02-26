/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Iterator;

public class PointSET {
    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("input Point2D is null");
        }
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("input Point2D is null");
        }
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Iterator<Point2D> it = set.iterator(); it.hasNext(); ) {
            Point2D p = it.next();
            StdDraw.point(p.x(), p.y());
        }
        StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("input Point2D is null");
        }
        ArrayList<Point2D> range = new ArrayList<Point2D>();
        for (Iterator<Point2D> it = set.iterator(); it.hasNext(); ) {
            Point2D p = it.next();
            if (rect.contains(p)) {
                range.add(p);
            }
        }
        return range;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("input Point2D is null");
        }
        if (set.isEmpty()) {
            return null;
        }
        double minDist = Double.MAX_VALUE;
        Point2D nearPoint = null;
        for (Iterator<Point2D> it = set.iterator(); it.hasNext(); ) {
            Point2D pp = it.next();
            double dist = p.distanceTo(pp);
            if (dist < minDist) {
                minDist = dist;
                nearPoint = pp;
            }
        }
        return nearPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
