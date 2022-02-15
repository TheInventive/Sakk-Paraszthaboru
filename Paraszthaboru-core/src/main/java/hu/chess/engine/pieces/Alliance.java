package hu.chess.engine.pieces;

import hu.chess.engine.player.BlackPlayer;
import hu.chess.engine.player.Player;
import hu.chess.engine.player.WhitePlayer;

public enum Alliance {
    WHITE(1){
        @Override
        public int getDirection(){
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK(2){
        @Override
        public int getDirection(){
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,final BlackPlayer blackPlayer) {
            return blackPlayer;
        }

    },
    TIE(3){
        @Override
        public int getDirection() {
            return 0;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return null;
        }
    };
    private int numVal;

    Alliance(int i) {
        numVal = i;
    }

    public int getNumVal(){
        return numVal;
    }

    public abstract int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);
}
