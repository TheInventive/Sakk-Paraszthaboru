package hu.chess.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BoardModel {
    private final IntegerProperty boardId = new SimpleIntegerProperty(this, "boardId");
    private final StringProperty boardString = new SimpleStringProperty(this, "boardString");
    private final IntegerProperty boardNumber = new SimpleIntegerProperty(this, "boardNumber");
    private final IntegerProperty gameId = new SimpleIntegerProperty(this, "gameId");

    public int getBoardId() {
        return boardId.get();
    }

    public void setBoardId(int boardId) {
        this.boardId.set(boardId);
    }

    public String getBoardString() {
        return boardString.get();
    }

    public void setBoardString(String boardString) {
        this.boardString.set(boardString);
    }

    public int getBoardNumber() {
        return boardNumber.get();
    }

    public void setBoardNumber(int boardNumber) {
        this.boardNumber.set(boardNumber);
    }

    public int getGameId() {
        return gameId.get();
    }

    public void setGameId(int gameId) {
        this.gameId.set(gameId);
    }
}
