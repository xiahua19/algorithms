/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

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
    public double energy(int x, int y) {
        validateXY(x, y);
        throw new RuntimeException("not implement");
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        throw new RuntimeException("not implement");
    }

    // sequence of indices for vertical seam
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
