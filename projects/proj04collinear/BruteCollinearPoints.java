/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/*
 * examines 4 points at a time and checks whether they all lie on the same line segment,
 * returning all such line segments.
 *
 */
public class BruteCollinearPoints {
    private Point[] points;
    private int numberOfSeg;
    

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        this.points = points;

        if (points == null) {
            throw new IllegalArgumentException("input points is null");
        }
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) {
                throw new IllegalArgumentException("input points contains null");
            }
        }
        Point origin = new Point(0, 0);
        Arrays.sort(points, origin.slopeOrder());
        for (int i = 1; i < points.length; ++i) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("input points contains duplicated points");
            }
        }

    }

    // the number of line segememts
    public int numberOfSegments() {

    }

    // include each line segment containing 4 points exactly once. If 4 points appear on a line segment
    // in the order p->q->r->s, then you should include either the line segment p->s or s->p (but not both)
    // and you should not include subsegments such as p->r or q->r.
    public LineSegment[] segments() {

    }

    // unit testing
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
