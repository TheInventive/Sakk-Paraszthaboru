module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;
    requires java.sql;

    exports hu.chess.engine.board;
    opens hu.chess.engine.board to javafx.fxml;
    exports hu.chess.engine.pieces;
    opens hu.chess.engine.pieces to javafx.fxml;
    exports hu.chess.model;
    exports hu.chess.engine.player;
    exports hu.chess.dao;
    exports hu.chess.statics;
}