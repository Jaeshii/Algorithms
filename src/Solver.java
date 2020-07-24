import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;
        private final int moves;
        private final SearchNode previous;

        private SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        @Override
        public int compareTo(SearchNode that) {
            int priorityDiff = (this.board.manhattan() + this.moves) - (that.board.manhattan() + that.moves);
            return  priorityDiff == 0 ? this.board.manhattan() - that.board.manhattan() : priorityDiff;
        }
    }

//    private class SearchNodesOrder implements Comparator<SearchNode> {
//
//        public int  compare(SearchNode sn1, SearchNode sn2) {
//            int sn1Manhattan = sn1.board.manhattan();
//            int sn2Manhattan = sn2.board.manhattan();
//            int sn1Priority = sn1.moves + sn1Manhattan;
//            int sn2Priority = sn2.moves + sn1Manhattan;
//
//            if (sn1Priority < sn2Priority) {
//                return -1;
//            }
//
//            if (sn1Priority > sn2Priority) {
//                return 1;
//            }
//
//            if (sn1Manhattan < sn2Manhattan) {
//                return -1;
//            }
//
//            if (sn1Manhattan > sn2Manhattan) {
//                return 1;
//            }
//
//            return 0;
//
//        }
//    }

    private boolean solvable;
    private final Stack<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        solvable = false;
        solution = new Stack<>();
        MinPQ<SearchNode> search = new MinPQ<>();

        search.insert(new SearchNode(initial, 0, null));
        search.insert(new SearchNode(initial.twin(), 0, null));

        while (!search.min().board.isGoal()) {
            SearchNode searchNode = search.delMin();

            for (Board neighbor : searchNode.board.neighbors()) {
                if (searchNode.previous == null || searchNode.previous != null && !searchNode.previous.board.equals(neighbor))
                    search.insert(new SearchNode(neighbor, searchNode.moves + 1, searchNode));
            }
        }

        SearchNode currentNode = search.min();
        solution.push(currentNode.board);
        while (currentNode.previous != null) {
            solution.push(currentNode.previous.board);
            currentNode = currentNode.previous;
        }

        if (currentNode.board.equals(initial)) solvable = true;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? solution.size() - 1 : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solvable ? solution : null;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}