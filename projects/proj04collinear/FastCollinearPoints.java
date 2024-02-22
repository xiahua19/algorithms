/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] points;

    // finds all line segments containint 4 or more points
    public FastCollinearPoints(Point[] points) {
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

    // the number of segments
    public int numberOfSegments() {

    }

    // the line segments
    public LineSegment[] segments() {

    }

    // unit test
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
