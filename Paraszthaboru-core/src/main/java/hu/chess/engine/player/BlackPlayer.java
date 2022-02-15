package hu.chess.engine.player;

import hu.chess.engine.board.Board;
import hu.chess.engine.board.Move;
import hu.chess.engine.pieces.Alliance;
import hu.chess.engine.pieces.Piece;
import javafx.util.Duration;

import java.util.Collection;

public class BlackPlayer extends Player{
    public static Duration remaining;

    public BlackPlayer(final Board board, final Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    public static final String graphicsPath = "hu/desktop/images/blackPawn.png";

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
}
