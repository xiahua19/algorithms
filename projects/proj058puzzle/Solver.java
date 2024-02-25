/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

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
    private boolean solvable;
    private int moves;
    private Board goal;
    private Node dest;

    private MinPQ<Node> minPQ;
    private MinPQ<Node> minPQTwin;
    private ArrayList<Board> solutions;

    private class Node implements Comparable<Node> {
        Board board;
        Node prev;
        int moves;
        int manhattanScore;

        Node(Board b, Node p, int m) {
            board = b;
            prev = p;
            moves = m;
            manhattanScore = m + b.manhattan();
        }

        public int compareTo(Node that) {
            return Integer.compare(this.manhattanScore, that.manhattanScore);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board intial) {
        if (intial == null) {
            throw new IllegalArgumentException("input board is null");
        }
        // initialize variables
        this.dest = null;
        this.moves = 0;
        this.solvable = false;
        this.minPQ = new MinPQ<Node>();
        this.minPQTwin = new MinPQ<Node>();
        this.solutions = new ArrayList<Board>();
        int[][] goalTiles = new int[intial.dimension()][intial.dimension()];
        int numStart = 1;
        for (int i = 0; i < intial.dimension(); ++i) {
            for (int j = 0; j < intial.dimension(); ++j) {
                if (i == intial.dimension() - 1 && j == intial.dimension() - 1) {
                    goalTiles[i][j] = 0;
                }
                else {
                    goalTiles[i][j] = numStart;
                    numStart++;
                }
            }
        }
        this.goal = new Board(goalTiles);

        // A* search for initial board
        Node minNode = new Node(intial, null, this.moves);
        this.minPQ.insert(minNode);
        Node minNodeTwin = new Node(intial.twin(), null, this.moves);
        this.minPQTwin.insert(minNodeTwin);
        while (true) {
            minNode = this.minPQ.delMin();
            if (minNode.board.equals(this.goal)) {
                this.dest = minNode;
                break;
            }

            minNodeTwin = this.minPQTwin.delMin();
            if (minNodeTwin.board.equals(this.goal)) {
                break;
            }

            for (Board b : minNode.board.neighbors()) {
                if (minNode.prev != null && b.equals(minNode.prev.board)) {
                    continue;
                }
                this.moves = minNode.moves + 1;
                this.minPQ.insert(new Node(b, minNode, this.moves));
            }

            for (Board b : minNodeTwin.board.neighbors()) {
                if (minNodeTwin.prev != null && b.equals(minNodeTwin.prev.board)) {
                    continue;
                }
                this.minPQTwin.insert(new Node(b, minNodeTwin, minNodeTwin.moves + 1));
            }
        }

        //  find the dest
        if (this.dest != null) {
            this.solvable = true;
            while (minNode != null) {
                this.solutions.add(minNode.board);
                minNode = minNode.prev;
            }
            Collections.reverse(this.solutions);
        }

    }

    // is the initial board solvable (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return this.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return this.solutions;
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
