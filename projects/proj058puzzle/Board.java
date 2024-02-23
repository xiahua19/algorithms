/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

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
            for (int j = 0; j < this.dim; ++j) {
                tilesStr += String.valueOf(tiles[i][j]);
            }
            tilesStr += "\n";
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
        return this.toString() == ((Board) y).toString();
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
