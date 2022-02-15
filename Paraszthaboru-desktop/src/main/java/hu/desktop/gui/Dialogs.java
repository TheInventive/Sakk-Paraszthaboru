package hu.desktop.gui;

import hu.chess.statics.Statics;
import hu.desktop.App;
import hu.desktop.SceneManager;
import hu.chess.dao.GameDAO;
import hu.chess.dao.GameDAOImpl;
import hu.chess.engine.pieces.Alliance;
import hu.chess.model.Game;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public final class Dialogs {

    public static void victoryDialog(){
        Alert victory = new Alert(Alert.AlertType.INFORMATION);
        victory.setContentText(App.lastBoard().currentPlayer().getOpponent().getAlliance() + " Játékos nyert!");
        victory.show();
        Stage stage = (Stage) victory.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        stage.toFront();
        saveGame(App.lastBoard().getCurrentPlayer().getOpponent().getAlliance().getNumVal());
        SceneManager.setActualScene("start_page");
        resetGame();
    }

    private static void saveGame(int gameResult){
        Game game = Statics.getGame();
        GameDAO gameDAO = new GameDAOImpl();
        gameDAO.save(game, gameResult);
    }

    public static void surrenderDialog(){
        Alert surrender = new Alert(Alert.AlertType.CONFIRMATION);
        surrender.setTitle("Játékos: " + App.lastBoard().currentPlayer().getOpponent().getAlliance());
        surrender.setContentText("Biztosan feladod a játékot?");
        Optional<ButtonType> result = surrender.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Dialogs.victoryDialog();
        }
    }

    public static void tieDialog(){
        Alert tie = new Alert((Alert.AlertType.CONFIRMATION));
        tie.setTitle("Játékos: " + App.lastBoard().currentPlayer().getAlliance());
        tie.setContentText("Elfogadod a döntetlent?");
        Optional<ButtonType> result = tie.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            saveGame(Alliance.TIE.getNumVal());
            resetGame();
            Alert victory = new Alert(Alert.AlertType.INFORMATION);
            victory.setContentText("A játék döntetlen!");
            victory.showAndWait();
            SceneManager.setActualScene("start_page");
        }
    }

    public static void resetGame(){
        App.lastBoard().changeCurrentPlayer(Alliance.WHITE);
        App.resetBoard();
        App.drawBoard();
    }
}
