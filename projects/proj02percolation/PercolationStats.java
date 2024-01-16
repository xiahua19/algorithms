/* *****************************************************************************
 *  Name:              Xiahua
 *  Coursera User ID:  2301210115
 *  Last modified:     1/15/2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] thresholds;
    private double mean;
    private boolean computemean = false;
    private double stddev;
    private boolean computestddev = false;
    private double confidenceLo;
    private boolean computecl = false;
    private double confidenceHi;
    private boolean computehi = false;
    private double confidence95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials nust be positive number");
        }
        thresholds = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation percolation = new Percolation(n);
            double threshold = 0;
            while (!percolation.percolates()) {
                threshold++;
                int random = StdRandom.uniformInt(n + 3, n * n + 3 * n + 1);
                int row = random / (n + 2);
                int col = random % (n + 2);
                // StdOut.println(row + " " + col);
                while (col == 0 || col == n + 1 || percolation.isOpen(row, col)) {
                    random = StdRandom.uniformInt(n + 3, n * n + 3 * n + 1);
                    row = random / (n + 2);
                    col = random % (n + 2);
                }
                percolation.open(row, col);
            }
            thresholds[i] = threshold / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (!computemean) {
            mean = StdStats.mean(thresholds);
            computemean = true;
        }
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (!computestddev) {
            stddev = StdStats.stddev(thresholds);
            computestddev = true;
        }
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        if (!computemean) {
            mean();
        }
        if (!computestddev) {
            stddev();
        }
        return mean - confidence95 * stddev / Math.sqrt(thresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (!computemean) {
            mean();
        }
        if (!computestddev) {
            stddev();
        }
        return mean + confidence95 * stddev / Math.sqrt(thresholds.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", "
                               + percolationStats.confidenceHi() + "]");
    }
}
