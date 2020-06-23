import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("invalid n or number of trials");

        this.trials = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }

            this.trials[trial] = ((double) perc.numberOfOpenSites() / (int) (Math.pow(n, 2)));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trials);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trials);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean()) - (CONFIDENCE_95 * stddev() / (Math.sqrt(trials.length)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean()) + (CONFIDENCE_95 * stddev() / (Math.sqrt(trials.length)));
    }

    // test client (see below)
    public static void main(String[] args) {
//        String s1 = "2";
//        String s2 = "1000";

        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }
}
