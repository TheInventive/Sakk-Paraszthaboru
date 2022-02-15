package hu.chess.engine.board;

import hu.chess.engine.pieces.Alliance;

public final class Victory {

    private static final String blackVictory = "-P-P-P-PP-P-P-P";
    private static final String whiteVictory = "p-p-p-pp-p-p-p-";
    private static boolean calledBefore = false;
    private boolean blackInVictoryPosition = false;
    private boolean whiteInVictoryPosition = false;
    private final Board board;

    public Victory(final Board board){
        this.board = board;
    }

    public Alliance checkVictory(){
        if(board.toString().startsWith(blackVictory)){
            blackInVictoryPosition = true;
        }
        if(board.toString().endsWith(whiteVictory)){
            whiteInVictoryPosition = true;
        }

        if(calledBefore){
            return getWinner();
        }

        if(blackInVictoryPosition || whiteInVictoryPosition)
            calledBefore = true;

        return null;
    }

    private Alliance getWinner(){
        if(blackInVictoryPosition && whiteInVictoryPosition)
            return Alliance.TIE;
        if(blackInVictoryPosition)
            return Alliance.BLACK;
        if(whiteInVictoryPosition)
            return Alliance.WHITE;
        return null;
    }
}
