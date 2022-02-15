package hu.chess.engine.pieces;

import hu.chess.engine.board.Board;
import hu.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    private final int cashedHashCode;

    Piece(final int piecePosition, final Alliance pieceAlliance){
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        this.cashedHashCode = computeHashCode();
    }

    private int computeHashCode(){
        int result = pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other) return true;
        if(!(other instanceof Piece)) return false;
        final Piece otherPiece = (Piece) other;
        return pieceAlliance == otherPiece.getPieceAlliance() && piecePosition == otherPiece.getPiecePosition();
    }

    @Override
    public int hashCode(){
        return this.cashedHashCode;
    }

    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
    public abstract Collection<Move> calculateBackwardMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public int getPiecePosition() {
        return this.piecePosition;
    }
}
