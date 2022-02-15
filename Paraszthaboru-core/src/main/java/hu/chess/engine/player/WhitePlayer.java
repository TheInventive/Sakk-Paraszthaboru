package hu.chess.engine.player;

import hu.chess.engine.pieces.Alliance;
import hu.chess.engine.board.Board;
import hu.chess.engine.board.Move;
import hu.chess.engine.pieces.Piece;
import javafx.util.Duration;

import java.util.Collection;

public class WhitePlayer extends Player{
    public static Duration remaining;

    public WhitePlayer(final Board board, final Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    public static final String graphicsPath = "hu/desktop/images/whitePawn.png";

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }
}
