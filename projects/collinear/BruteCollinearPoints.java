/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Examines 4 points at a time and checks whether they all lie on the same line segment, returning
 * all such line segments.
 */
public class BruteCollinearPoints {
    // all points
    private Point[] points;
    private int pointsNum;
    private List<LineSegment> lineSegments;
    private boolean computeLineSegment;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        Arrays.sort(points);
        this.points = points;
        this.pointsNum = this.points.length;
        this.lineSegments = new ArrayList<LineSegment>();
        this.computeLineSegment = false;
    }

    // the number of line segments
    public int numberOfSegments() {
        if (!this.computeLineSegment) {
            this.segments();
        }
        return this.lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        this.computeLineSegment = true;

        for (int i = 0; i < this.pointsNum - 3; ++i) {
            for (int j = i + 1; j < this.pointsNum - 2; ++j) {
                for (int k = j + 1; k < this.pointsNum - 1; ++k) {
                    for (int l = k + 1; l < this.pointsNum; ++l) {
                        double slope_i = points[i].slopeTo(points[j]);
                        double slope_k = points[i].slopeTo(points[k]);
                        double slope_l = points[i].slopeTo(points[l]);

                        if ((Math.abs(slope_i - slope_k) < 1.0e-7
                                && Math.abs(slope_i - slope_l) < 1.0e-7) || 
                                (slope_i == Double.POSITIVE_INFINITY && 
                                slope_k == Double.POSITIVE_INFINITY && 
                                slope_l == Double.POSITIVE_INFINITY)) {
                            this.lineSegments.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }

        return this.lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
