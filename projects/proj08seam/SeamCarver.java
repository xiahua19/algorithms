/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        validateParam(picture);
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    // in this assignment, we use dual-gradient energy function
    public double energy(int x, int y) {
        validateXY(x, y);
        // pixel in the edge
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
            return 1000;
        }
        else {
            return energyInternal(x, y);
        }
    }

    // compute the energy of internal pixel
    private double energyInternal(int x, int y) {
        Color top = this.picture.get(x, y - 1);
        Color bottom = this.picture.get(x, y + 1);
        Color left = this.picture.get(x - 1, y);
        Color right = this.picture.get(x + 1, y);
        double deltaX2 = Math.pow(left.getRed() - right.getRed(), 2) +
                Math.pow(left.getBlue() - right.getBlue(), 2) +
                Math.pow(left.getGreen() - right.getGreen(), 2);
        double deltaY2 = Math.pow(top.getRed() - bottom.getRed(), 2) +
                Math.pow(top.getBlue() - bottom.getBlue(), 2) +
                Math.pow(top.getGreen() - bottom.getGreen(), 2);
        return Math.sqrt(deltaX2 + deltaY2);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(width * height);
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width - 1; ++col) {
                if (row != 0) {
                    DirectedEdge de1 = new DirectedEdge(getOrder(col, row),
                                                        getOrder(col + 1, row - 1),
                                                        energy(col + 1, row - 1));
                    ewd.addEdge(de1);
                }

                DirectedEdge de2 = new DirectedEdge(getOrder(col, row),
                                                    getOrder(col + 1, row),
                                                    energy(col + 1, row));
                ewd.addEdge(de2);

                if (row != height - 1) {
                    DirectedEdge de3 = new DirectedEdge(getOrder(col, row),
                                                        getOrder(col + 1, row + 1),
                                                        energy(col + 1, row + 1));
                    ewd.addEdge(de3);
                }
            }
        }

        int start = 0;
        int end = 0;
        double minLenth = Double.POSITIVE_INFINITY;
        for (int s = 0; s <= getOrder(0, height - 1); s += width) {
            DijkstraSP dsp = new DijkstraSP(ewd, s);
            for (int e = getOrder(width - 1, 0); e <= getOrder(width - 1, height - 1); e += width) {
                if (dsp.hasPathTo(e) && dsp.distTo(e) < minLenth) {
                    minLenth = dsp.distTo(e);
                    start = s;
                    end = e;
                }
            }
        }

        DijkstraSP dsp = new DijkstraSP(ewd, start);
        Stack<DirectedEdge> path = (Stack<DirectedEdge>) dsp.pathTo(end);
        assert path.size() == width - 1;
        int[] seam = new int[width];
        for (int i = 0; i < width - 2; ++i) {
            seam[i] = parseRow(path.pop().from());
        }
        seam[width - 2] = parseRow(path.peek().from());
        seam[width - 1] = parseRow(path.peek().to());
        path.pop();
        assert path.size() == 0;
        return seam;
    }

    // sequence of indices for vertical seam
    // this is similar to the classic shortest path problem in an edge-weighted digraph
    // except:
    //  1. The weights are on the vertices instead of the edges.
    //  2. We want to find the shortest path from any of W pixels in the top row to any of W pixels in the bottom row
    //  3. The digraph is acyclic, where there is a downward from (x,y) to (x-1,y+1),(x,y+1),(x+1,y+1)
    public int[] findVerticalSeam() {
        EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(width * height);
        for (int row = 0; row < height - 1; ++row) {
            for (int col = 0; col < width; ++col) {
                if (col != 0) {
                    DirectedEdge de1 = new DirectedEdge(getOrder(col, row),
                                                        getOrder(col - 1, row + 1),
                                                        energy(col - 1, row + 1));
                    ewd.addEdge(de1);
                }

                DirectedEdge de2 = new DirectedEdge(getOrder(col, row),
                                                    getOrder(col, row + 1),
                                                    energy(col, row + 1));
                ewd.addEdge(de2);

                if (col != width - 1) {
                    DirectedEdge de3 = new DirectedEdge(getOrder(col, row),
                                                        getOrder(col + 1, row + 1),
                                                        energy(col + 1, row + 1));
                    ewd.addEdge(de3);
                }
            }
        }

        int start = 0;
        int end = 0;
        double minLenth = Double.POSITIVE_INFINITY;
        for (int s = 0; s < width; ++s) {
            DijkstraSP dsp = new DijkstraSP(ewd, s);
            for (int e = getOrder(0, height - 1); e <= getOrder(width - 1, height - 1); e++) {
                if (dsp.hasPathTo(e) && dsp.distTo(e) < minLenth) {
                    minLenth = dsp.distTo(e);
                    start = s;
                    end = e;
                }
            }
        }

        DijkstraSP dsp = new DijkstraSP(ewd, start);
        Stack<DirectedEdge> path = (Stack<DirectedEdge>) dsp.pathTo(end);
        assert path.size() == height - 1;
        int[] seam = new int[height];
        for (int i = 0; i < height - 2; ++i) {
            seam[i] = parseCol(path.pop().from());
        }
        seam[height - 2] = parseCol(path.peek().from());
        seam[height - 1] = parseCol(path.peek().to());
        path.pop();
        assert path.size() == 0;
        return seam;
    }

    private int getOrder(int col, int row) {
        return row * width + col;
    }

    private int parseRow(int order) {
        return order / width;
    }

    private int parseCol(int order) {
        return order % width;
    }

    // remove horizontal seam for current picture
    public void removeHorizontalSeam(int[] seam) {
        validateParam(seam);
        validateReH();
        validateSeam(seam);
        Picture newPicture = new Picture(this.width, this.height - 1);
        for (int col = 0; col < width; ++col) {
            for (int row = 0; row < height - 1; ++row) {
                if (row >= seam[col])
                    newPicture.set(col, row, this.picture.get(col, row + 1));
                else
                    newPicture.set(col, row, this.picture.get(col, row));
            }
        }
        this.picture = newPicture;
        this.width = newPicture.width();
        this.height = newPicture.height();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validateParam(seam);
        validateReV();
        validateSeam(seam);
        Picture newPicture = new Picture(this.width - 1, this.height);
        for (int row = 0; row < this.height; ++row) {
            for (int col = 0; col < this.width - 1; ++col) {
                if (col >= seam[row])
                    newPicture.set(col, row, this.picture.get(col + 1, row));
                else
                    newPicture.set(col, row, this.picture.get(col, row));
            }
        }
        this.picture = newPicture;
        this.height = newPicture.height();
        this.width = newPicture.width();
    }

    // check whether the input is null
    private void validateParam(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("input parameter is null");
        }
    }

    // check whether (x,y) is out of range
    private void validateXY(int x, int y) {
        if (x < 0 || x >= width) {
            throw new IllegalArgumentException("x out of range");
        }
        if (y < 0 || y >= height) {
            throw new IllegalArgumentException("y out of range");
        }
    }

    // check seam is whether validate
    private void validateSeam(int[] seam) {
        throw new RuntimeException("not implement");
    }

    // when call removeHorizontalSeam(), the height must larger than 1
    private void validateReH() {
        if (height <= 1) {
            throw new IllegalArgumentException("height is less than or equal to 1");
        }
    }

    // when call removeVerticalSeam(), the width must larger than 1
    private void validateReV() {
        if (width <= 1) {
            throw new IllegalArgumentException("width is less than or equal to 1");
        }
    }

    // unit testing
    public static void main(String[] args) {

    }
}
