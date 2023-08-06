import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final int n;
    private double[] openSitesNum;
    private Percolation perc;

    // perform independent trials on an n-by-n grid
    public  PercolationStats (int n, int trials){
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;
        this.n = n;
        this.openSitesNum = new double[trials];

        simulation();
    }

    // get random (row, col) from unopened sites
    private int[] getRandomRL() {
        int row = StdRandom.uniformInt(1, n+1);
        int col = StdRandom.uniformInt(1, n+1);
        while (row > n || col > n || row <= 0 || col <= 0 || perc.isOpen(row, col)) {
            row = StdRandom.uniformInt(1, n+1);
            col = StdRandom.uniformInt(1, n+1);
        }
        return new int[] {row, col};
    }

    // start simulations
    private void simulation () {
        for (int t = 0; t < this.trials; ++t) {
            perc = new Percolation(n);
            while (! perc.percolates()) {
                int[] rowCol = getRandomRL();
                perc.open(rowCol[0], rowCol[1]);
            }
            StdOut.println("simulation " + t + ": " + perc.numberOfOpenSites());
            openSitesNum[t] = (double) perc.numberOfOpenSites() / (this.n * this.n);
        }
    }

    // sample mean of percolation threshold
    public double mean () {
        return StdStats.mean(openSitesNum);
    }

    // sample stddev of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSitesNum);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo () {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi () {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client
    public  static void main (String[] agrs) {
        PercolationStats percStat = new PercolationStats(Integer.parseInt(agrs[0]), Integer.parseInt(agrs[1]));

        double mean = percStat.mean();
        double stddev = percStat.stddev();
        double confidenceLo = percStat.confidenceLo();
        double confidenceHi = percStat.confidenceHi();

        StdOut.println("mean                    = " + mean);
        StdOut.println("stddev                  = " + stddev);
        StdOut.println("95% confidence interval = " + "[" + confidenceLo + ", " + confidenceHi + "]");
    }
}
