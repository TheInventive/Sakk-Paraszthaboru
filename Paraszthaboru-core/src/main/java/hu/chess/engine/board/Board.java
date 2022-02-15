package hu.chess.engine.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import hu.chess.engine.pieces.Alliance;
import hu.chess.engine.pieces.Pawn;
import hu.chess.engine.pieces.Piece;
import hu.chess.engine.player.BlackPlayer;
import hu.chess.engine.player.Player;
import hu.chess.engine.player.WhitePlayer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private Player currentPlayer;

    public final Victory victory = new Victory(this);

    public Board(String boardString){
        Builder builder = new Builder(boardString);
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> whiteBackwardLegalMoves = calculateBackwardMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
        final Collection<Move> blackBackwardLegalMoves = calculateBackwardMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, whiteBackwardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, blackBackwardLegalMoves, blackStandardLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

     protected Board(Builder builder){
         this.gameBoard = createGameBoard(builder);
         this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
         this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);

         final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
         final Collection<Move> whiteBackwardLegalMoves = calculateBackwardMoves(this.whitePieces);
         final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
         final Collection<Move> blackBackwardLegalMoves = calculateBackwardMoves(this.blackPieces);

         this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, whiteBackwardLegalMoves);
         this.blackPlayer = new BlackPlayer(this, blackBackwardLegalMoves, blackStandardLegalMoves);
         this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    public Collection<Piece> getAllPieces() {
        return Stream.concat(this.whitePieces.stream(),
                this.blackPieces.stream()).collect(Collectors.toList());
    }

    public void changeCurrentPlayer(Alliance alliance){
        if (alliance == Alliance.WHITE)
            this.currentPlayer = this.whitePlayer;
        else if(alliance == Alliance.BLACK)
            this.currentPlayer = this.blackPlayer;
    }

    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(tileText);
        }
        return builder.toString();
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces){

         final List<Move> legalMoves = new ArrayList<>();
         for(final Piece piece : pieces){
             legalMoves.addAll(piece.calculateLegalMoves(this));
         }
         return ImmutableList.copyOf(legalMoves);
    }

    private Collection<Move> calculateBackwardMoves(final Collection<Piece> pieces){
         final List<Move> legalMoves = new ArrayList<>();
        for(final Piece piece : pieces){
            legalMoves.addAll(piece.calculateBackwardMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    public Collection<Piece> getWhitePieces(){
         return this.whitePieces;
    }

    public Collection<Piece> getBlackPieces(){
         return this.blackPieces;
    }

    private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
         final List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile : gameBoard) {
            if(tile.isTileOccupied()){
                final Piece piece = tile.getPiece();
                if(piece.getPieceAlliance() == alliance){
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    private List<Tile> createGameBoard(Builder builder) {

        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard(){
         final Builder builder = new Builder();
         builder.setPiece(new Pawn(8, Alliance.BLACK));
         builder.setPiece(new Pawn(1, Alliance.BLACK));
         builder.setPiece(new Pawn(10, Alliance.BLACK));
         builder.setPiece(new Pawn(3, Alliance.BLACK));
         builder.setPiece(new Pawn(12, Alliance.BLACK));
         builder.setPiece(new Pawn(5, Alliance.BLACK));
         builder.setPiece(new Pawn(14, Alliance.BLACK));
         builder.setPiece(new Pawn(7, Alliance.BLACK));

         builder.setPiece(new Pawn(56, Alliance.WHITE));
         builder.setPiece(new Pawn(49, Alliance.WHITE));
         builder.setPiece(new Pawn(58, Alliance.WHITE));
         builder.setPiece(new Pawn(51, Alliance.WHITE));
         builder.setPiece(new Pawn(60, Alliance.WHITE));
         builder.setPiece(new Pawn(53, Alliance.WHITE));
         builder.setPiece(new Pawn(62, Alliance.WHITE));
         builder.setPiece(new Pawn(55, Alliance.WHITE));

         builder.setMoveMaker(Alliance.WHITE);
         return builder.build();
    }

    public Tile getTile(final int tileCoordinate){
        return gameBoard.get(tileCoordinate);
    }

    public Player whitePlayer() {
        return whitePlayer;
    }

    public Player blackPlayer() {
        return blackPlayer;
    }

    public Player currentPlayer(){
         return this.currentPlayer;
    }

    public Iterable<Move> getAllLegalMoves() {
         if(whitePlayer.getLegalMoves().isEmpty() && ! blackPlayer.getLegalMoves().isEmpty())
             return Iterables.unmodifiableIterable(
                     Iterables.concat(this.whitePlayer.getBackwardMoves(), this.blackPlayer.getLegalMoves()));
         else if(blackPlayer.getLegalMoves().isEmpty() && ! whitePlayer.getLegalMoves().isEmpty() )
             return Iterables.unmodifiableIterable(
                     Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getBackwardMoves()));
         else if(whitePlayer.getLegalMoves().isEmpty() && blackPlayer.getLegalMoves().isEmpty() )
             return Iterables.concat(this.whitePlayer.getBackwardMoves(),this.blackPlayer.getBackwardMoves());

         return Iterables.unmodifiableIterable(
                 Iterables.concat(this.whitePlayer.getLegalMoves(),this.blackPlayer.getLegalMoves()));
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker = Alliance.WHITE;
        Move transitionMove;

        public Builder() {
            this.boardConfig = new HashMap<>(32, 1.0f);
        }

        public Builder(String saveFile){
            this.boardConfig = new HashMap<>(32, 1.0f);
            if(saveFile.length() != 64) throw new RuntimeException("Invalid save file!");
            for (int i = 0; i < saveFile.length(); i++) {
                if(saveFile.charAt(i) == 'p')
                    setPiece(new Pawn(i, Alliance.BLACK));
                else if(saveFile.charAt(i) == 'P')
                    setPiece(new Pawn(i, Alliance.WHITE));
            }
        }

        public void setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
        }

        public void setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
        }

        public void setMoveTransition(final Move transitionMove) {
            this.transitionMove = transitionMove;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
