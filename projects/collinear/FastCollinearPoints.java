/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    // all points
    private Point[] points;
    private int pointsNum;
    private List<LineSegment> lineSegments;
    private boolean computeLineSegment;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
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
        for (int i = 0; i < this.pointsNum; ++i) {
            Comparator<Point> slopeOrder = points[i].slopeOrder();

            Point[] adjacentPoints = new Point[this.pointsNum - 1];
            System.arraycopy(points, 0, adjacentPoints, 0, i);
            System.arraycopy(points, i + 1, adjacentPoints, i, this.pointsNum - i - 1);

            Arrays.sort(adjacentPoints, slopeOrder);

            for (int j = 0; j < this.pointsNum - 4; ++j) {
                double slope_1 = points[i].slopeTo(adjacentPoints[j]);
                double slope_2 = points[i].slopeTo(adjacentPoints[j + 1]);
                double slope_3 = points[i].slopeTo(adjacentPoints[j + 2]);

                if ((Math.abs(slope_1 - slope_2) < 1.0e-7
                        && Math.abs(slope_1 - slope_3) < 1.0e-7) || 
                                (slope_1 == Double.POSITIVE_INFINITY && 
                                slope_2 == Double.POSITIVE_INFINITY && 
                                slope_3 == Double.POSITIVE_INFINITY)) {
                    Point[] points4 = new Point[4];
                    points4[0] = points[i];
                    points4[1] = adjacentPoints[j];
                    points4[2] = adjacentPoints[j + 1];
                    points4[3] = adjacentPoints[j + 2];
                    Arrays.sort(points4);
                    this.lineSegments.add(new LineSegment(points4[0], points4[3]));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
