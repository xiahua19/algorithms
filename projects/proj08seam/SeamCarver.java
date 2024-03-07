/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    private static final int[] DIR = new int[] { -1, 0, 1 };

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
        int[] seam = new int[width];
        // distTo
        double[][] distTo = new double[width][height];
        for (double[] col : distTo)
            Arrays.fill(col, Double.POSITIVE_INFINITY);
        Arrays.fill(distTo[0], 0);
        // edgeTo
        int[][] edgeTo = new int[width][height];

        int endRow = 0;
        double minDist = Double.POSITIVE_INFINITY;

        // iterate follow topological order
        for (int col = 1; col < width; col++) {
            for (int row = 0; row < height; row++) {
                for (int offset : DIR) {
                    int nRow = row + offset;
                    if (nRow < 0 || nRow >= height) continue;
                    // relax
                    if (distTo[col][row] > distTo[col - 1][nRow] + energy(col, row)) {
                        distTo[col][row] = distTo[col - 1][nRow] + energy(col, row);
                        edgeTo[col][row] = nRow;
                    }
                }
                // find the minimum distTo
                if (col == width - 1 && distTo[col][row] < minDist) {
                    minDist = distTo[col][row];
                    endRow = row;
                }
            }
        }
        for (int col = width - 1, row = endRow; col >= 0; row = edgeTo[col][row], col--)
            seam[col] = row;

        return seam;
    }

    // sequence of indices for vertical seam
    // this is similar to the classic shortest path problem in an edge-weighted digraph
    // except:
    //  1. The weights are on the vertices instead of the edges.
    //  2. We want to find the shortest path from any of W pixels in the top row to any of W pixels in the bottom row
    //  3. The digraph is acyclic, where there is a downward from (x,y) to (x-1,y+1),(x,y+1),(x+1,y+1)
    public int[] findVerticalSeam() {
        int[] seam = new int[height];
        // distTo
        double[][] distTo = new double[height][width];
        for (double[] row : distTo)
            Arrays.fill(row, Double.POSITIVE_INFINITY);
        Arrays.fill(distTo[0], 0);
        // edgeTo
        int[][] edgeTo = new int[height][width];

        int endCol = 0;
        double minDist = Double.POSITIVE_INFINITY;

        // iterate follow topological order
        for (int row = 1; row < height; row++) {
            for (int col = 0; col < width; col++) {
                for (int offset : DIR) {
                    int nCol = col + offset;
                    if (nCol < 0 || nCol >= width) continue;
                    // relax
                    if (distTo[row][col] > distTo[row - 1][nCol] + energy(col, row)) {
                        distTo[row][col] = distTo[row - 1][nCol] + energy(col, row);
                        edgeTo[row][col] = nCol;
                    }
                }
                // find the minimum distTo
                if (row == height - 1 && distTo[row][col] < minDist) {
                    minDist = distTo[row][col];
                    endCol = col;
                }
            }
        }
        for (int row = height - 1, col = endCol; row >= 0; col = edgeTo[row][col], row--)
            seam[row] = col;

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
        validateSeam(seam, false);
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
        validateSeam(seam, true);
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
    private void validateSeam(int[] seam, boolean isVertical) {
        if (isVertical && seam.length != height) {
            throw new IllegalArgumentException("seam is unvalidated");
        }
        if (!isVertical && seam.length != width) {
            throw new IllegalArgumentException("seam is unvalidated");
        }
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
}
