import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int size;
    private Board twin;
    private final int[] board;  // 1d array representation of a board

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        board = new int[tiles.length * tiles.length];
        size = tiles.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i * size + j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(size);
        sb.append(System.lineSeparator());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(board[i * size + j]);
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] != 0 && board[i] != i + 1) {
                distance++;
            }
        }

        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;

        for (int i = 0; i < size * size; i++) {
            if (board[i] == 0) {
                continue;
            }
            int index = board[i] - 1;

            int currentRow = i / size + 1;
            int currentCol = i % size + 1;
            int goalRow = index / size + 1;
            int goalCol = index % size + 1;

            distance += Math.abs(currentRow - goalRow) + Math.abs(currentCol - goalCol);
        }
        return distance;

    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] != 0  && board[i] != i + 1)
                return false;
        }
        return true;

    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }

        if (y == null) {
            return false;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board other = (Board) y;

        if (this.dimension() != other.dimension()) {
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            if (board[i] != other.board[i]) {
                return false;
            }
        }

        return true;
    }

    private int[][] monoToBidi(final int[] array) {
        int[][] bidi = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(array, (i * size), bidi[i], 0, size);
        }
        return bidi;
    }

    private boolean isValidCoord(int r, int c) {
        if (r >= 0 && r < size && c >= 0 && c < size) {
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // shift in (x, y) coords
        int[][] directions = { { 1, 0 }, {-1, 0 }, { 0, 1 }, { 0, -1 } };

        Stack<Board> neighbors = new Stack<>();

        for (int i = 0; i < board.length && neighbors.size() == 0; i++) {
            // find index of empty tile
            if (board[i] != 0) {
                continue;
            }

            int[] boardClone;

            // create a new board for each possible neighboring direction and push to stack
            for (int d = 0; d < directions.length; d++) {
                boardClone = board.clone();

                // get row and column of neighboring tile (0-based index)
                int r = (i / size) + directions[d][0];
                int c = (i % size) + directions[d][1];

                // check if (row, column) coordinate of neighboring tile is within the board
                if (isValidCoord(r, c)) {
                    // swap empty tile with neighboring tile
                    int tmp = boardClone[i];
                    boardClone[i] = boardClone[r * size + c];
                    boardClone[r * size + c] = tmp;
                    neighbors.push(new Board(monoToBidi(boardClone)));
                }

            }
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twin == null) {
            int[] boardClone = board.clone();

            int indexFrom = StdRandom.uniform(0, board.length);
            int indexTo = StdRandom.uniform(0, board.length);

            while (boardClone[indexFrom] == 0) {
                indexFrom = StdRandom.uniform(0, board.length);
            }

            while (boardClone[indexTo] == 0 || boardClone[indexFrom] == boardClone[indexTo]) {
                indexTo = StdRandom.uniform(0, board.length);
            }

            int tmp = boardClone[indexFrom];
            boardClone[indexFrom] = boardClone[indexTo];
            boardClone[indexTo] = tmp;

            twin = new Board(monoToBidi(boardClone));
        }

        return twin;

    }


    // unit testing (not graded)
    public static void main(String[] args) {
        Board test = new Board(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}});
        System.out.println(test);
//        System.out.println(test.dimension());
//        System.out.println(test.manhattan());
//        System.out.println(test.hamming());
//        for (int i = 0; i < 50; i++) {
//            System.out.println(test.twin());
//        }
        Stack<Board> neighbors = (Stack<Board>) test.neighbors();
        for (Board b : neighbors) {
            System.out.println(b);
        }
    }

}