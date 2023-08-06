import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Optional;

public class Percolation {
    private boolean[][] sites;
    private int openSitesNum;
    private WeightedQuickUnionUF wqu;
    private int sideLength;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation (int n) {
        sites = new boolean[n+1][n+1];
        openSitesNum = 0;
        sideLength = n;

        wqu = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 1; i <= sideLength; ++i) {
            wqu.union(0, i);
        }
        for (int i = 1; i <=sideLength; ++i) {
            wqu.union(n*n+1, getOrder(sideLength, i));
        }

        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j){
                sites[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open (int row, int col) {
        sites[row][col] = true;
        openSitesNum += 1;
        if (row-1 >= 1 && isOpen(row-1, col)) {
            wqu.union(getOrder(row, col), getOrder(row-1, col));
        }
        if (col-1 >= 1 && isOpen(row, col-1)){
            wqu.union(getOrder(row, col), getOrder(row, col-1));
        }
        if (row+1 <= sideLength && isOpen(row+1, col)){
            wqu.union(getOrder(row, col), getOrder(row+1, col));
        }
        if (col+1 <= sideLength && isOpen(row, col+1)){
            wqu.union(getOrder(row, col), getOrder(row, col+1));
        }
    }

    // get the order of site (row, col)
    private int getOrder (int row, int col) {
        return row*sideLength-sideLength + col;
    }

    // is the site (row, col) open?
    public boolean isOpen (int row, int col) {
        return sites[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull (int row, int col) {
        return wqu.find(getOrder(row, col)) == wqu.find(0);
    }

    // is the system percolate?
    public boolean percolates () {
        return wqu.find(0) == wqu.find(sideLength*sideLength+1);
    }

    // returns the number of open sites
    public int numberOfOpenSites () {
        return openSitesNum;
    }
}
