package hu.chess.engine.pieces;

import com.google.common.collect.ImmutableList;
import hu.chess.engine.board.Board;
import hu.chess.engine.board.BoardUtils;
import hu.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{

    private static final int[] CANDIDATE_MOVE_COORDINATE = { 7, 9 , 14, 18};
    private static final int[] CANDIDATE_BACKWARD_MOVE_COORDINATE = {-7, -9 , -14, -18};

    public Pawn(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = (List<Move>) getMoves(board);
        final List<Move> otherMoves = new ArrayList<>();
        for (Move legalMove : legalMoves) {
            if (legalMove instanceof Move.JumpMove) {
                Piece movedPiece = legalMove.getMovePiece();
                List<Move> mm = (List<Move>) getOtherMoves(board, legalMove.getDestinationCoordinate());
                for (Move move : mm) {
                    move.setMovedPiece(movedPiece);
                }
                otherMoves.addAll(mm);
            }
        }
        legalMoves.addAll(otherMoves);
        return legalMoves;
    }

    private Collection<Move> getMoves(final Board board){
        final List<Move> legalMoves = new ArrayList<>();

        //Recalculate possible moves again from the tiles
        for (final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATE){
            int candidateDestinationCoordinate =
                    this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);
            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHT_COLUMN[piecePosition] && pieceAlliance.isWhite()) ||
                            (BoardUtils.FIRST_COLUMN[piecePosition] && pieceAlliance.isBlack()))){
                if(!board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }

            }else if(currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[piecePosition] && pieceAlliance.isWhite()) ||
                            (BoardUtils.EIGHT_COLUMN[piecePosition] && pieceAlliance.isBlack()))){
                if(!board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }
            }
            else {
                checkDoubleMove(board, legalMoves, currentCandidateOffset, candidateDestinationCoordinate, piecePosition);
            }
        }
        return legalMoves;
    }

    private void checkDoubleMove(Board board, List<Move> legalMoves, int currentCandidateOffset, int candidateDestinationCoordinate, int piecePosition) {
        if(currentCandidateOffset == 14 &&
                !(((BoardUtils.EIGHT_COLUMN[piecePosition]  || BoardUtils.SEVENTH_COLUMN[piecePosition] )&& pieceAlliance.isWhite()) ||
                        ((BoardUtils.FIRST_COLUMN[piecePosition] || BoardUtils.SECOND_COLUMN[piecePosition]) && pieceAlliance.isBlack()))){
            if(board.getTile(candidateDestinationCoordinate -(7 * this.getPieceAlliance().getDirection())).isTileOccupied()
                    && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                legalMoves.add(new Move.JumpMove(board, this, candidateDestinationCoordinate));
            }
        }
        else if(currentCandidateOffset == 18 &&
                !(((BoardUtils.FIRST_COLUMN[piecePosition] || BoardUtils.SECOND_COLUMN[piecePosition]) && pieceAlliance.isWhite()) ||
                        ((BoardUtils.EIGHT_COLUMN[piecePosition] || BoardUtils.SEVENTH_COLUMN[piecePosition]) && pieceAlliance.isBlack()))){
            if(board.getTile(candidateDestinationCoordinate-(9 * this.getPieceAlliance().getDirection())).isTileOccupied()
                    && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                legalMoves.add(new Move.JumpMove(board, this, candidateDestinationCoordinate));
            }
        }
    }

    private Collection<Move> getOtherMoves(final Board board, int piecePosition){
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATE){
            int candidateDestinationCoordinate =
                    piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
              checkDoubleMove(board, legalMoves, currentCandidateOffset, candidateDestinationCoordinate, piecePosition);
        }
        return legalMoves;
    }

    @Override
    public Collection<Move> calculateBackwardMoves(final Board board){
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset: CANDIDATE_BACKWARD_MOVE_COORDINATE){
            int candidateDestinationCoordinate =
                    this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);
            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == -7 &&
                    !((BoardUtils.EIGHT_COLUMN[piecePosition] && pieceAlliance.isBlack()) ||
                            (BoardUtils.FIRST_COLUMN[piecePosition] && pieceAlliance.isWhite()))){
                if(!board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }

            }else if(currentCandidateOffset == -9 &&
                    !((BoardUtils.FIRST_COLUMN[piecePosition] && pieceAlliance.isBlack()) ||
                            (BoardUtils.EIGHT_COLUMN[piecePosition] && pieceAlliance.isWhite()))){
                if(!board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }
            }
            else if(currentCandidateOffset == -14 &&
                    !(((BoardUtils.EIGHT_COLUMN[piecePosition]  || BoardUtils.SEVENTH_COLUMN[piecePosition] )&& pieceAlliance.isBlack()) ||
                            ((BoardUtils.FIRST_COLUMN[piecePosition] || BoardUtils.SECOND_COLUMN[piecePosition]) && pieceAlliance.isWhite()))){
                if(board.getTile(candidateDestinationCoordinate +(7 * this.getPieceAlliance().getDirection())).isTileOccupied()
                        && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new Move.JumpMove(board, this, candidateDestinationCoordinate));
                }
            }
            else if(currentCandidateOffset == -18 &&
                    !(((BoardUtils.FIRST_COLUMN[piecePosition] || BoardUtils.SECOND_COLUMN[piecePosition]) && pieceAlliance.isBlack()) ||
                            ((BoardUtils.EIGHT_COLUMN[piecePosition] || BoardUtils.SEVENTH_COLUMN[piecePosition]) && pieceAlliance.isWhite()))){
                if(board.getTile(candidateDestinationCoordinate+(9 * this.getPieceAlliance().getDirection())).isTileOccupied()
                        && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new Move.JumpMove(board, this, candidateDestinationCoordinate));
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Piece movePiece(final Move move) {
        return new Pawn(move.getDestinationCoordinate(),move.getMovePiece().getPieceAlliance());
    }

    @Override
    public String toString(){
        return "P";
    }
}
