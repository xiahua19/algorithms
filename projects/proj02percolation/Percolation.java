/* *****************************************************************************
 *  Name:              Xiahua
 *  Coursera User ID:  2301210115
 *  Last modified:     1/15/2024
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] sites;
    private WeightedQuickUnionUF uf;
    private int size;
    private int openNum;

    // creates n-by-n grid, with all sites initially blocked
    // blocked: 0; open: 1;
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n and trials nust be positive number");
        }
        openNum = 0;
        size = n;
        sites = new boolean[n + 2][n + 2];
        uf = new WeightedQuickUnionUF((n + 2) * (n + 2));
        for (int i = 1; i < n + 1; ++i) {
            for (int j = 1; j < n + 1; ++j) {
                sites[i][j] = false;
            }
        }
        for (int i = 0; i < n + 2; ++i) {
            sites[i][0] = false;
            sites[i][n + 1] = false;
            sites[0][i] = true;
            sites[n + 1][i] = true;
            addConnection(0, i);
            // addConnection(i, 0);
            addConnection(n + 1, i);
            // addConnection(i, n + 1);
        }
    }

    private void validate(int p) {
        if (p < 1 || p > size) {
            throw new IllegalArgumentException("index " + p + " must between 1 and " + size);
        }
    }

    private void addConnection(int i, int j) {
        if (!(i == 0 || i == sites.length - 1 || j == 0 || j == sites.length - 1)) {
            if (!isOpen(i, j)) {
                return;
            }
        }

        int curr = i * sites.length + j;
        int[] neighbors = {
                i * sites.length + j - 1,
                i * sites.length + j + 1,
                (i - 1) * sites.length + j,
                (i + 1) * sites.length + j,
                };
        for (int neighbor : neighbors) {
            int row = neighbor / sites.length;
            int col = neighbor % sites.length;
            if (col < 0 || col > sites.length - 1 || row < 0 || row > sites.length - 1) {
                continue;
            }
            if (col == 0 || col == sites.length - 1 || row == 0 || row == sites.length - 1) {
                uf.union(curr, neighbor);
                continue;
            }
            if (isOpen(row, col)) {
                uf.union(curr, neighbor);
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row);
        validate(col);
        if (!sites[row][col]) {
            sites[row][col] = true;
            openNum++;
        }
        addConnection(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return sites[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        return uf.find(row * sites.length + col) == uf.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find((size + 2) * (size + 2) - 1) == uf.find(0);
    }
}
