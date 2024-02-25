/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private int[][] tiles;
    private int dim;

    // create a board from an n-by-n array of tiles, where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.dim = tiles.length;
    }

    // string representation of this board
    public String toString() {
        String tilesStr = String.valueOf(this.dim) + "\n";
        for (int i = 0; i < this.dim; ++i) {
            for (int j = 0; j < this.dim - 1; ++j) {
                tilesStr += String.valueOf(tiles[i][j]) + " ";
            }
            if (i == this.dim - 1)
                tilesStr += String.valueOf(tiles[i][this.dim - 1]) + " ";
            else
                tilesStr += String.valueOf(tiles[i][this.dim - 1]) + "\n";
        }
        return tilesStr;
    }

    // board dimension n
    public int dimension() {
        return this.dim;
    }

    // number of tiles out of place
    public int hamming() {
        int hamm = 0;
        int curr = 1;
        for (int i = 0; i < this.dim; ++i) {
            for (int j = 0; j < this.dim; ++j) {
                if (i == this.dim - 1 && j == this.dim - 1) {
                    break;
                }
                if (this.tiles[i][j] != curr) {
                    hamm++;
                }
                curr++;
            }
        }
        return hamm;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manh = 0;
        for (int i = 0; i < this.dim; ++i) {
            for (int j = 0; j < this.dim; ++j) {
                if (this.tiles[i][j] == 0) {
                    continue;
                }
                int goalRow = (this.tiles[i][j] - 1) / this.dim;
                int goalCol = (this.tiles[i][j] - 1) % this.dim;
                manh += Math.abs(goalRow - i) + Math.abs(goalCol - j);
            }
        }
        return manh;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < this.dim; ++i) {
            for (int j = 0; j < this.dim; ++j) {
                if (i == this.dim - 1 && j == this.dim - 1) {
                    return this.tiles[i][j] == 0;
                }
                if (this.tiles[i][j] != i * this.dim + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equual y?
    public boolean equals(Object y) {
        return this.toString().equals(((Board) y).toString());
    }

    // exchange two tiles on board and return new board
    private int[][] exch(int oriX, int oriY, int desX, int desY) {
        int[][] exchTiles = new int[this.dim][this.dim];
        for (int i = 0; i < this.dim; ++i) {
            System.arraycopy(this.tiles[i], 0, exchTiles[i], 0, this.dim);
        }
        int tmp = exchTiles[oriX][oriY];
        exchTiles[oriX][oriY] = exchTiles[desX][desY];
        exchTiles[desX][desY] = tmp;
        return exchTiles;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // find zero tile
        int zeroRow = 0;
        int zeroCol = 0;
        boolean findZero = false;
        for (int i = 0; i < this.dim; ++i) {
            for (int j = 0; j < this.dim; ++j) {
                if (this.tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    findZero = true;
                    break;
                }
            }
            if (findZero) {
                break;
            }
        }

        // find neighbors
        ArrayList<Board> neighborBoards = new ArrayList<Board>();
        if (zeroRow > 0 && zeroRow < this.dim - 1 && zeroCol > 0 && zeroCol < this.dim - 1) {
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow - 1, zeroCol)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow + 1, zeroCol)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol - 1)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol + 1)));
        }
        else if (zeroRow == 0 && zeroCol > 0 && zeroCol < this.dim - 1) {
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol - 1)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol + 1)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow + 1, zeroCol)));
        }
        else if (zeroRow == this.dim - 1 && zeroCol > 0 && zeroCol < this.dim - 1) {
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol - 1)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol + 1)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow - 1, zeroCol)));
        }
        else if (zeroCol == 0 && zeroRow > 0 && zeroRow < this.dim - 1) {
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow - 1, zeroCol)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow + 1, zeroCol)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol + 1)));
        }
        else if (zeroCol == this.dim - 1 && zeroRow > 0 && zeroRow < this.dim - 1) {
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow - 1, zeroCol)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow + 1, zeroCol)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol - 1)));
        }
        else if (zeroCol == 0 && zeroRow == 0) {
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow + 1, zeroCol)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol + 1)));
        }
        else if (zeroCol == 0 && zeroRow == this.dim - 1) {
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow - 1, zeroCol)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol + 1)));
        }
        else if (zeroCol == this.dim - 1 && zeroRow == 0) {
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow + 1, zeroCol)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol - 1)));
        }
        else if (zeroCol == this.dim - 1 && zeroRow == this.dim - 1) {
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow - 1, zeroCol)));
            neighborBoards.add(new Board(exch(zeroRow, zeroCol, zeroRow, zeroCol - 1)));
        }
        else {
            throw new UnsupportedOperationException("tiles is invalid");
        }

        return neighborBoards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // find zero tile
        int zeroRow = 0;
        int zeroCol = 0;
        boolean findZero = false;
        for (int i = 0; i < this.dim; ++i) {
            for (int j = 0; j < this.dim; ++j) {
                if (this.tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    findZero = true;
                    break;
                }
            }
            if (findZero) {
                break;
            }
        }

        int[] indices = new int[4];
        int k = 0;
        for (int i = 0; i < this.dim; ++i) {
            for (int j = 0; j < this.dim; ++j) {
                if (i != zeroRow || j != zeroCol) {
                    indices[k++] = i;
                    indices[k++] = j;
                    if (k == indices.length) break;
                }
            }
            if (k == indices.length) break;
        }
        int[][] tiles = exch(indices[0], indices[1], indices[2], indices[3]);
        return new Board(tiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Board inital2 = new Board(tiles);
        StdOut.println(initial.equals(inital2));
        StdOut.println(initial.equals(initial.twin()));

        StdOut.println(initial);
        StdOut.println("hanmming dis: " + initial.hamming());
        StdOut.println("manhattan dis: " + initial.manhattan());
        StdOut.println("Twin: ");
        StdOut.println(initial.twin());
        StdOut.println("Neighbors: ");
        ArrayList<Board> ns = (ArrayList<Board>) initial.neighbors();
        for (Board b : ns) StdOut.println(b);
    }
}
