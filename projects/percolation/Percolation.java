import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] sites;
    private int openSitesNum;
    private WeightedQuickUnionUF wqu;
    private int sideLength;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation (int n) {
        sites = new boolean[n][n];
        openSitesNum = 0;
        wqu = new WeightedQuickUnionUF(n * n);
        sideLength = n;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j){
                sites[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open (int row, int col) {
        sites[row][col] = true;
        openSitesNum += 1;
        if (row-1 >= 0 && isOpen(row-1, col)) {
            wqu.union(getOrder(row, col), getOrder(row-1, col));
        }
        if (col-1 >= 0 && isOpen(row, col-1)){
            wqu.union(getOrder(row, col), getOrder(row, col-1));
        }
        if (row+1 < sideLength && isOpen(row+1, col)){
            wqu.union(getOrder(row, col), getOrder(row+1, col));
        }
        if (col+1 < sideLength && isOpen(row, col+1)){
            wqu.union(getOrder(row, col), getOrder(row, col+1));
        }
    }

    // get the order of site (row, col)
    private int getOrder (int row, int col) {
        return row*sideLength + col;
    }

    // is the site (row, col) open?
    public boolean isOpen (int row, int col) {
        return sites[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull (int row, int col) {
        for (int i = 0; i < sideLength; ++i) {
            if (wqu.find(getOrder(row, col)) == wqu.find(getOrder(0, i))) {
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites () {
        return openSitesNum;
    }
}
