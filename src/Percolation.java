import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {
    WeightedQuickUnionUF gridConnections;
    int[][] grid;
    final int N;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) throws IllegalArgumentException {
        if (n <= 0)
            throw new IllegalArgumentException("n must be greater than 0");
        N = n;
        gridConnections = new WeightedQuickUnionUF((int)(Math.pow(n, 2) + 2));
        grid = new int[n][n];

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) throws IllegalArgumentException {
        if ((row > N || row < 0) || (col > N || col < 0))
            throw new IllegalArgumentException("not a valid row or column");
        if (! isOpen(row, col)) {
            grid[row - 1][col - 1] = 1;
            // connect to virtual top or bottom site if in topmost or bottommost row
            if (row == 1) {
                gridConnections.union(gridLoc(row, col), 0);
            } else if (row == N) {
                gridConnections.union(gridLoc(row, col), (int) (Math.pow(N, 2)) + 1);
            }
            // connect adjacent open tiles
            if (row - 1 > 0 && isOpen(row - 1, col))
                gridConnections.union(gridLoc(row - 1, col), gridLoc(row, col));
            if (row + 1 < N + 1 && isOpen(row + 1, col))
                gridConnections.union(gridLoc(row + 1, col), gridLoc(row, col));
            if (col - 1 > 0 && isOpen(row, col - 1))
                gridConnections.union(gridLoc(row, col - 1), gridLoc(row, col));
            if (col + 1 < N + 1 && isOpen(row, col + 1))
                gridConnections.union(gridLoc(row, col + 1), gridLoc(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) throws IllegalArgumentException {
        if ((row > N || row < 0) || (col > N || col < 0))
            throw new IllegalArgumentException("not a valid row or column");

        return grid[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) throws IllegalArgumentException {
        if ((row > N || row < 0) || (col > N || col < 0))
            throw new IllegalArgumentException("not a valid row or column");

        return gridConnections.find(gridLoc(row, col)) == gridConnections.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int openNum = 0;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (grid[row][col] == 1)
                    openNum++;
            }
        }
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return gridConnections.find((int) (Math.pow(N, 2)) + 1) == gridConnections.find(0);
    }

    // returns the index given (row, col)
    private int gridLoc(int row, int col) {
        return N * (row - 1) + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(5);
        for (int i = 0; i < perc.grid.length; i++)
            System.out.println(Arrays.toString(perc.grid[i]));
        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(3, 1);
        perc.open(4, 1);
        perc.open(5, 1);
        for (int i = 0; i < perc.grid.length; i++)
            System.out.println(Arrays.toString(perc.grid[i]));
        System.out.println(perc.percolates());
        System.out.println(perc.numberOfOpenSites());
    }
}
