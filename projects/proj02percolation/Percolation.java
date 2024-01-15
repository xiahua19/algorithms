/* *****************************************************************************
 *  Name:              Xiahua
 *  Coursera User ID:  2301210115
 *  Last modified:     1/15/2024
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] sites;
    private WeightedQuickUnionUF uf;
    private int size;
    private int openNum;

    private void validate(int p) {
        if (p < 0 || p > size + 1) {
            throw new IllegalArgumentException("index " + p + " must between 1 and " + size);
        }
    }

    private void addConnection(int i, int j) {
        if (!isOpen(i, j)) {
            return;
        }
        int curr = i * sites.length + j;
        int neighbors[] = {
                i * sites.length + j - 1,
                i * sites.length + j + 1,
                (i - 1) * sites.length + j,
                (i + 1) * sites.length + j,
                };
        for (int neighbor : neighbors) {
            if (isOpen(neighbor / sites.length, neighbor % sites.length)) {
                uf.union(curr, neighbor);
            }
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    // blocked: 0; open: 1;
    public Percolation(int n) {
        openNum = 0;
        size = n;
        sites = new int[n + 2][n + 2];
        for (int i = 0; i < n + 2; ++i) {
            for (int j = 0; j < n + 2; ++j) {
                sites[i][j] = 0;
            }
        }
        uf = new WeightedQuickUnionUF((n + 2) * (n + 2));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row);
        validate(col);
        sites[row][col] = 1;
        openNum++;
        addConnection(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return sites[row][col] != 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        for (int i = 1; i <= size; ++i) {
            if (uf.find(sites.length + i) == uf.find(row * sites.length + col)) {
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= size; ++i) {
            if (isFull(size, i)) {
                return true;
            }
        }
        return false;
    }
}
