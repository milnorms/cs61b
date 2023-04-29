package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private final Board _board;
    /** Current score. */
    private int _score;
    /** Maximum score so far.  Updated when game ends. */
    private int _maxScore;
    /** True iff game is ended. */
    private boolean _gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        _board = new Board(size);
        _score = _maxScore = 0;
        _gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        _board = new Board(rawValues);
        this._score = score;
        this._maxScore = maxScore;
        this._gameOver = gameOver;
    }

    /** Same as above, but gameOver is false. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore) {
        this(rawValues, score, maxScore, false);
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     * */
    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return _board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /** Return the current score. */
    public int score() {
        return _score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return _maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /** Allow initial game board to announce a hot start to the GUI. */
    public void hotStartAnnounce() {
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void tilt(Side side) {
        // TODO: Fill in this function.

        checkGameOver();
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {

        int size = b.size();
        boolean isEmpty = false;


//        Iterate through all tiles (rows and columns)
        for (int i=0; i < size; i++) {

            for (int j=0; j < size; j++) {
//                System.out.println(b.tile(i,j));
//                check if current tile is null/empty
                if (b.tile(i,j) == null) {
                    isEmpty = true;
                    break;
                }

            }
        }
        return isEmpty;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int size = b.size();
        boolean isMax = false;

//        Iterate through all tiles (rows and columns)
        for (int i=0; i < size; i++) {

            for (int j=0; j < size; j++) {
                // Error checking value. If the tile is empty, set the value to 0
                int value = b.tile(i,j) == null ? 0 : b.tile(i,j).value();
//                System.out.println(value);
//                check if current tile is the max (2048)
                if (value == MAX_PIECE) {
                    isMax = true;
                    break;
                }
            }
        }
        return isMax;

    }

    // atLeastOneMoveExists helper
    public static boolean checkBelow (Board b, int i, int j, int curValue){
        // safe to check below
        int belowValue = b.tile(i+1,j) == null ? 0 : b.tile(i+1,j).value();
        return (curValue == belowValue);
    }

    public static boolean checkAbove (Board b, int i, int j, int curValue){
        // safe to check below
        int aboveValue = b.tile(i-1,j) == null ? 0 : b.tile(i-1,j).value();
        return (curValue == aboveValue);
    }

    public static boolean checkLeft (Board b, int i, int j, int curValue){
        // safe to check below
        int leftValue = b.tile(i,j-1) == null ? 0 : b.tile(i,j-1).value();
        return (curValue == leftValue);
    }

    public static boolean checkRight (Board b, int i, int j, int curValue){
        // safe to check below
        int rightValue = b.tile(i,j+1) == null ? 0 : b.tile(i,j+1).value();
        return (curValue == rightValue);
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: REFACTOR THE HECK OUTTA THIS
        int size = b.size();
        boolean moveExists = false;


        // Check for empty space
        if (emptySpaceExists(b)) {
            return true;
        }

        //        Iterate through all tiles (rows and columns)
        for (int i=0; i < size; i++) {
            // break out of outer loop
            if (moveExists) break;

            for (int j=0; j < size; j++) {
                // Error checking value. If the tile is empty, set the value to 0
                int curValue = b.tile(i,j) == null ? 0 : b.tile(i,j).value();

                // position bound trackers
                // Rows are opposite???
                // rows
                boolean isLastRow = i == size-1;
                boolean isFirstRow = i == 0;
                //cols
                boolean isFirstCol = j == 0;
                boolean isLastCol = j == size-1;


                // boundary checks

                // handles all first row
                if (isFirstRow) {
                    // safe to check above
                   moveExists = checkBelow(b, i, j, curValue);
//                    System.out.println("curval: "+curValue+" i: "+i+" j: "+j+" moveExists: "+moveExists);

                   if (moveExists) break;

                   // first row, first col. safe to check right only
                   if (isFirstCol) {
                       moveExists = checkRight(b, i, j, curValue);
                       if (moveExists) break;
                   }

                    // first row, last col. safe to check left only
                    else if (isLastCol) {
                        moveExists = checkLeft(b, i, j, curValue);
                        if (moveExists) break;
                    }
                    // any other space on first row. safe to check left and right
                    else {

                       moveExists = checkLeft(b, i, j, curValue) || checkRight(b, i, j, curValue);
                       if (moveExists) break;

                   }
                }

                // handles all last row

                else if (isLastRow) {
                    // safe to check above
                    moveExists = checkAbove(b, i, j, curValue);

                    if (moveExists) break;

                    // last row, first col. safe to check right only
                    if (isFirstCol) {
                        moveExists = checkRight(b, i, j, curValue);
                        if (moveExists) break;
                    }

                    // last row, last col. safe to check left only
                    else if (isLastCol) {
                        moveExists = checkLeft(b, i, j, curValue);
                        if (moveExists) break;
                    }
                    // any other space on last row. safe to check left and right
                    else {

                        moveExists = checkLeft(b, i, j, curValue) || checkRight(b, i, j, curValue);
                        if (moveExists) break;

                    }
                }
                // check 1st and last cols

                else if (isFirstCol) {
                    // check right
                    moveExists = checkRight(b, i, j, curValue);
                    if (moveExists) break;

                } else if (isLastCol) {
                    // check left
                    moveExists = checkLeft(b, i, j, curValue);
                    if (moveExists) break;
                }


                // not in any of the above boundary scenarios, safe to check all 4 adjacent tiles

                else {

                    if (checkBelow(b, i, j, curValue)) {
                        moveExists = true;
                        break;
                    }
                    if (checkRight(b, i, j, curValue)) {
                        moveExists = true;
                        break;
                    }
                    if (checkAbove(b, i, j, curValue)) {
                        moveExists = true;
                        break;
                    }
                    if (checkLeft(b, i, j, curValue)) {
                        moveExists = true;
                        break;
                    }

//                    System.out.println("curval: "+curValue+" i: "+i+" j: "+j+" moveExists: "+moveExists);

                }



            }
        }


//        System.out.println(" moveExists: "+moveExists);

        return moveExists;
    }

    /** Returns the model as a string, used for debugging. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    /** Returns whether two models are equal. */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    /** Returns hash code of Model’s string. */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
