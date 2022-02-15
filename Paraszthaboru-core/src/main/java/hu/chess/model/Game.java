package hu.chess.model;

import hu.chess.engine.board.Board;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final IntegerProperty gameId = new SimpleIntegerProperty(this, "gameId");
    private final StringProperty whitePlayerName = new SimpleStringProperty(this, "whitePlayerName");
    private final StringProperty blackPlayerName = new SimpleStringProperty(this, "blackPlayerName");
    private final ObjectProperty<List<Board>> Boards = new SimpleObjectProperty<>(this, "boards");
    private final ObjectProperty<LocalDate> timeOfPlay = new SimpleObjectProperty<>(this,"timeOfPlay");
    private final IntegerProperty winner = new SimpleIntegerProperty(this, "winner");

    public void resetBoard(){
        Boards.get().clear();
        Boards.get().add(Board.createStandardBoard());
    }

    public void resetBoard(List<Board> newBoards){
        Boards.get().clear();
        Boards.get().addAll(newBoards);
    }

    public Game(List<Board> boards){
        setBoards(boards);
    }

    public Game() {setBoards(new ArrayList<>());}

    public int getWinner() { return winner.get(); }

    public void setWinner(int winner) { this.winner.set(winner); }

    public String getWhitePlayerName() {
        return whitePlayerName.get();
    }

    public void setWhitePlayerName(String whitePlayerName) {
        this.whitePlayerName.set(whitePlayerName);
    }

    public String getBlackPlayerName() {
        return blackPlayerName.get();
    }

    public void setBlackPlayerName(String blackPlayerName) {
        this.blackPlayerName.set(blackPlayerName);
    }

    public List<Board> getBoards() {
        return Boards.get();
    }

    public void setBoards(List<Board> boards) {
        this.Boards.set(boards);
    }

    public LocalDate getTimeOfPlay() {
        return timeOfPlay.get();
    }

    public void setTimeOfPlay(LocalDate timeOfPlay) {
        this.timeOfPlay.set(timeOfPlay);
    }

    public int getGameId() {
        return gameId.get();
    }

    public void setGameId(int gameId) {
        this.gameId.set(gameId);
    }
}
