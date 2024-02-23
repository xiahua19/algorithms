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
public class FastCollinearPoints {
    private Point[] points;
    private int pointsLen;
    private int numberOfSeg;
    private boolean computeSeg;
    private int capacity;
    private LineSegment[] segmentsResult;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        Arrays.sort(points, new Point(0, 0).slopeOrder());

        this.points = points;
        this.pointsLen = points.length;
        this.numberOfSeg = 0;
        this.capacity = 100;
        this.computeSeg = false;
        this.segmentsResult = new LineSegment[this.capacity];

        // check whether points is null
        if (points == null) {
            throw new IllegalArgumentException("input points is null");
        }
        // check whether points contain null
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) {
                throw new IllegalArgumentException("input points contains null");
            }
        }
        // check whether points has duplicated point
        for (int i = 1; i < points.length; ++i) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("input points contains duplicated points");
            }
        }

    }

    private void resize() {
        LineSegment[] newLineSeg = new LineSegment[this.capacity * 2];
        for (int i = 0; i < this.capacity; ++i) {
            newLineSeg[i] = this.segmentsResult[i];
        }
        this.capacity *= 2;
        this.segmentsResult = newLineSeg;
    }

    private LineSegment[] split() {
        LineSegment[] newLineSeg = new LineSegment[this.numberOfSeg];
        for (int i = 0; i < this.numberOfSeg; ++i) {
            newLineSeg[i] = this.segmentsResult[i];
        }
        return newLineSeg;
    }

    // the number of line segments
    public int numberOfSegments() {
        if (!this.computeSeg) {
            segments();
        }
        return this.numberOfSeg;
    }

    // include each line segment containing 4 points exactly once. If 4 points appear on a line segment
    // in the order p->q->r->s, then you should include either the line segment p->s or s->p (but not both)
    // and you should not include subsegments such as p->r or q->r.
    public LineSegment[] segments() {
        if (!this.computeSeg) {
            for (int i = 0; i < this.pointsLen - 1; ++i) {
                Point[] tmp = Arrays.copyOfRange(this.points, i + 1, this.pointsLen);
                Arrays.sort(tmp, this.points[i].slopeOrder());
                StdOut.println(tmp.length);
                for (int j = 0; j < tmp.length - 1; ) {
                    int m = j + 1;
                    while (this.points[i].slopeTo(tmp[j]) == this.points[i].slopeTo(tmp[m])) {
                        m++;
                        if (m >= tmp.length) {
                            j = m;
                            break;
                        }
                    }
                    if (m - j > 3) {
                        LineSegment seg = new LineSegment(points[i], tmp[--m]);
                        this.segmentsResult[this.numberOfSeg] = seg;
                        this.numberOfSeg++;
                        if (this.numberOfSeg >= this.capacity) {
                            resize();
                        }
                    }
                    j = m;
                }
            }
            this.computeSeg = true;
        }
        LineSegment[] result = split();
        return result;
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
