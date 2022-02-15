package hu.chess.engine.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);
    public static final int NUM_TILES = 64;
    public static final int NUM_TILE_PER_ROW = 8;

    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[64];

        do{
            column[columnNumber] = true;
            columnNumber += NUM_TILE_PER_ROW;
        }while (columnNumber < NUM_TILES);

        return column;
    }

    public static boolean isValidTileCoordinate(int tileCoordinate){
        return tileCoordinate >= 0 && tileCoordinate < 64;
    }
}
