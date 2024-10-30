package Puzzle;

public class Game {

    //Layout
    //North
    //West - statistik (Northwest current? - Soutwest pb?)
    //Center - spelplan

    final int ROWS = 4;
    final int COLS = 4;
    final int TOTAL_SQUARES = ROWS * COLS;
    int[] grid = new int[TOTAL_SQUARES];

    boolean isConnected(int tile, int target) {

        int startOfRow = tile % ROWS;
        int endOfRow = tile % ROWS - 1;


        if (0 != startOfRow && tile == target - 1) {
            return true;
        }

        if (0 != endOfRow && tile == target + 1) {
            return true;
        }

        return tile == target - ROWS || tile == target + ROWS;
    }
}
