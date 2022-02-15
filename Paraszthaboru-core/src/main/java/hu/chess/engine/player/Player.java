package hu.chess.engine.player;

import hu.chess.engine.board.Board;
import hu.chess.engine.board.Move;
import hu.chess.engine.pieces.Alliance;
import hu.chess.engine.pieces.Piece;

import java.util.Collection;

public abstract class Player {
    protected final Board board;
    protected final Collection<Move> legalMoves;
    protected final Collection<Move> backwardMoves;

    protected Player(final Board board, final Collection<Move> legalMoves,
                     final Collection<Move> backwardMoves) {
        this.board = board;
        this.legalMoves = legalMoves;
        this.backwardMoves = backwardMoves;
    }

    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }
    public Collection<Move> getBackwardMoves() {return this.backwardMoves;}

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
}
