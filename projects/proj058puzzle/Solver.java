/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/*
 * Define:
 * 1. A search node of the game to be a board.
 * 2. The number of moves made to reach the board
 * 3. The previous search node
 *
 * Step:
 * 1. Insert the initial search node (the initial board with 0 moves and null previous search node) into a priority queue
 * 2. Delete from the priority queue the search node with the minimum priority
 * 3. Insert onto the priority queue all neighboring search nodes (those can be reached in one move from the dequeued search node
 * Repeat until the search node dequeued corresponds to the goal board
 * */
public class Solver {
    private MinPQ<Board> minPQ = new MinPQ<Board>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board intial) {
        if (intial == null) {
            throw new IllegalArgumentException("input board is null");
        }

    }

    // is the initial board solvable (see below)
    public boolean isSolvable() {

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
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
