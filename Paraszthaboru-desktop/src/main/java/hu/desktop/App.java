package hu.desktop;

import hu.chess.engine.board.Board;
import hu.chess.statics.Statics;
import hu.desktop.gui.Graphics;
import hu.desktop.gui.Helper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    public static Board lastBoard(){
        return Helper.lastElement(Statics.getGame().getBoards());
    }
    public static void resetBoard(){
        Statics.getGame().resetBoard();
    }

    public static void drawBoard(){ Graphics.drawPieces(chessTiles, lastBoard()); }
    public static void addBoard(Board board) { Statics.getGame().getBoards().add(board);}

    public static List<Button> chessTiles = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        SceneManager.init(stage);
        Scene scene1 = new Scene(loadFXML("start_page"), 640, 480);
        Scene scene2 = new Scene(loadFXML("board"), 720, 720);
        SceneManager.addScene("start_page", scene1);
        SceneManager.addScene("board", scene2);
        SceneManager.setActualScene("start_page");
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}