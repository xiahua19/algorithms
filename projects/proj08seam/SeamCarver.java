/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

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
            return 100;
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
        throw new RuntimeException("not implement");
    }

    // sequence of indices for vertical seam
    // this is similar to the classic shortest path problem in an edge-weighted digraph
    // except:
    //  1. The weights are on the vertices instead of the edges.
    //  2. We want to find the shortest path from any of W pixels in the top row to any of W pixels in the bottom row
    //  3. The digraph is acyclic, where there is a downward from (x,y) to (x-1,y+1),(x,y+1),(x+1,y+1)
    public int[] findVerticalSeam() {
        throw new RuntimeException("not implement");
    }

    // remove horizontal seam for current picture
    public void removeHorizontalSeam(int[] seam) {
        validateParam(seam);
        validateReH();
        validateSeam(seam);
        throw new RuntimeException("not implement");
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validateParam(seam);
        validateReV();
        validateSeam(seam);
        throw new RuntimeException("not implement");
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
