package hu.desktop.controller;

import hu.chess.dao.BoardDAO;
import hu.chess.dao.BoardDAOImpl;
import hu.chess.dao.GameDAO;
import hu.chess.dao.GameDAOImpl;
import hu.chess.engine.board.Board;
import hu.chess.engine.pieces.Alliance;
import hu.chess.engine.player.BlackPlayer;
import hu.chess.engine.player.WhitePlayer;
import hu.chess.model.Game;
import hu.chess.statics.Statics;
import hu.desktop.App;
import hu.desktop.SceneManager;
import hu.desktop.gui.Dialogs;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.List;

import static hu.desktop.controller.BoardController.staticDisplay;

public class StartPageController {
    public TextField whitePlayerName;
    public TextField blackPlayerName;
    public ListView<String> mainListView;
    public Spinner<Integer> spinner;
    public CheckBox useSpinner;
    public Button watchReplay;
    public Button loadGame;

    public static ListView<String> mainListViewStatic;
    private static List<Game> gameList;
    public TextField searchBox;
    private Timeline timer = null;

    @FXML
    public void initialize() {
        watchReplay.setDisable(true);
        loadGame.setDisable(true);
        mainListViewStatic = mainListView;

        mainListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null) return;
            if(newValue.contains("Nyertes") || newValue.contains("Döntetlen")){
                watchReplay.setDisable(false);
                loadGame.setDisable(true);
            }
            else{
                watchReplay.setDisable(true);
                loadGame.setDisable(false);
            }
        });
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 5);

        spinner.setValueFactory(valueFactory);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> search());
    }

    @FXML
    public void startGame(){
        if(!whitePlayerName.getText().equals("") && !blackPlayerName.getText().equals(""))
        {
            Game game = new Game(Statics.getGame().getBoards());
            game.setWhitePlayerName(whitePlayerName.getText());
            game.setBlackPlayerName(blackPlayerName.getText());
            game.setTimeOfPlay(LocalDate.now());
            Statics.setGame(game);
            SceneManager.setActualScene("board");
            WhitePlayer.remaining = new Duration(spinner.getValue()*60000);
            BlackPlayer.remaining = new Duration(spinner.getValue()*60000);

            if(useSpinner.isSelected()){
                timer = new Timeline(
                        new KeyFrame(Duration.seconds(1),
                                event -> {
                                    if (App.lastBoard().currentPlayer().getAlliance() == Alliance.WHITE) {
                                        staticDisplay.setText("Játékos: "+Statics.getGame().getWhitePlayerName() + " Idő: " + WhitePlayer.remaining.toSeconds());
                                        WhitePlayer.remaining = WhitePlayer.remaining.subtract(new Duration(1000));
                                    } else if (App.lastBoard().currentPlayer().getAlliance() == Alliance.BLACK) {
                                        staticDisplay.setText("Játékos: "+ Statics.getGame().getBlackPlayerName() + " Idő: " + BlackPlayer.remaining.toSeconds());
                                        BlackPlayer.remaining = BlackPlayer.remaining.subtract(new Duration(1000));
                                    }
                                    if (WhitePlayer.remaining.toMillis() <= 0 || BlackPlayer.remaining.toMillis() <= 0) {
                                        timer.stop();
                                        Dialogs.victoryDialog();
                                    }
                                }));
                timer.setCycleCount(Timeline.INDEFINITE);
                timer.play();
            }
            else staticDisplay.setText( Statics.getGame().getWhitePlayerName() + " VS "+ Statics.getGame().getBlackPlayerName());
        }
    }

    @FXML
    public void loadGame(){
        String selectedGame = mainListView.getSelectionModel().getSelectedItem().split(" ")[0];
        Game gameLoad = null;
        for (Game game: gameList) {
            if(game.getGameId() == Integer.parseInt(selectedGame))
                gameLoad = game;
        }
        if(gameLoad == null) return;

        Statics.setGame(gameLoad);
        staticDisplay.setText(gameLoad.getWhitePlayerName() + " VS " + gameLoad.getBlackPlayerName());
        BoardDAO boardDAO = new BoardDAOImpl();
        SceneManager.setActualScene("board");
        List<Board> boardList = boardDAO.findByGameId(gameLoad);
        Statics.getGame().resetBoard(boardList);
        App.drawBoard();
        if(gameList.size() % 2 == 1)
            App.lastBoard().changeCurrentPlayer(Alliance.WHITE);
        else
            App.lastBoard().changeCurrentPlayer(Alliance.BLACK);
    }

    public static void updateItems(){
        GameDAO gameDAO = new GameDAOImpl();
        gameList = gameDAO.findAll();
        mainListViewStatic.getItems().clear();
        for (Game game:gameList) {
            setVisible(game);
        }
    }

    public void search() {

            if (searchBox.getText().equals("") && gameList != null){
                gameList.forEach(StartPageController::setVisible);
                return;
            }

        if(gameList != null && gameList.size() != 0){
            mainListViewStatic.getItems().clear();
            for (Game game:gameList) {
                if( searchBox.getText().contains(game.getWhitePlayerName())
                || searchBox.getText().contains(game.getBlackPlayerName())
                || searchBox.getText().contains(game.getTimeOfPlay().toString())
                || searchBox.getText().contains("Döntetlen!")
                || searchBox.getText().contains("Nyertes: " + game.getBlackPlayerName())
                || searchBox.getText().contains("Nyertes: " + game.getWhitePlayerName()))
                    setVisible(game);
            }
        }
    }

    private static void setVisible(Game game) {
        String winner = "";
        if(game.getWinner() == 1)
            winner = "\tNyertes: " + game.getWhitePlayerName();
        else if(game.getWinner() == 2)
            winner = "\tNyertes: " + game.getBlackPlayerName();
        else if(game.getWinner() == 3)
            winner = "\tDöntetlen!";
        String gameString = game.getGameId()+" " + game.getWhitePlayerName()+ " VS "
                + game.getBlackPlayerName() + " at " + game.getTimeOfPlay() + winner;
        if(!mainListViewStatic.getItems().contains(gameString))
            mainListViewStatic.getItems().add(gameString);
    }
}
