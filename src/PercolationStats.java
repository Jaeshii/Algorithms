import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trials;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) throws IllegalArgumentException {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("invalid n or number of trials");

        this.trials = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            Percolation perc = new Percolation(n);
            int numOpen = 0;
            while (! perc.percolates()) {
                perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
                numOpen++;
            }
            this.trials[trial] = (double) (numOpen / (int) (Math.pow(n, 2)));
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
        return (mean() - 1.96 * stddev()) / (Math.sqrt(trials.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + 1.96 * stddev()) / (Math.sqrt(trials.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        String s1 = "2";
        String s2 = "1000";

        PercolationStats test = new PercolationStats(Integer.parseInt(s1), Integer.parseInt(s2));
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }
}
