package hu.desktop.controller;

import hu.chess.dao.GameDAO;
import hu.chess.dao.GameDAOImpl;
import hu.chess.engine.board.Board;
import hu.chess.engine.board.Move;
import hu.chess.engine.board.SourceDestination;
import hu.chess.engine.pieces.Alliance;
import hu.chess.model.Game;
import hu.chess.statics.Statics;
import hu.desktop.SceneManager;
import hu.desktop.gui.Dialogs;
import hu.desktop.gui.Graphics;
import hu.desktop.gui.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;

import java.util.Arrays;

import static hu.desktop.App.*;

public class BoardController {
    public Button pane00;
    public Button pane01;
    public Button pane02;
    public Button pane03;
    public Button pane04;
    public Button pane05;
    public Button pane06;
    public Button pane07;
    public Button pane08;
    public Button pane09;
    public Button pane10;
    public Button pane11;
    public Button pane12;
    public Button pane13;
    public Button pane14;
    public Button pane15;
    public Button pane16;
    public Button pane17;
    public Button pane18;
    public Button pane19;
    public Button pane20;
    public Button pane21;
    public Button pane22;
    public Button pane23;
    public Button pane24;
    public Button pane25;
    public Button pane26;
    public Button pane27;
    public Button pane28;
    public Button pane29;
    public Button pane30;
    public Button pane31;
    public Button pane32;
    public Button pane33;
    public Button pane34;
    public Button pane35;
    public Button pane36;
    public Button pane37;
    public Button pane38;
    public Button pane39;
    public Button pane40;
    public Button pane41;
    public Button pane42;
    public Button pane43;
    public Button pane44;
    public Button pane45;
    public Button pane46;
    public Button pane47;
    public Button pane48;
    public Button pane49;
    public Button pane50;
    public Button pane51;
    public Button pane52;
    public Button pane53;
    public Button pane54;
    public Button pane55;
    public Button pane56;
    public Button pane57;
    public Button pane58;
    public Button pane59;
    public Button pane60;
    public Button pane61;
    public Button pane62;
    public Button pane63;
    public Label display;

    public static Label staticDisplay;

    private final SourceDestination sourceDestination = new SourceDestination(0, 0);

    @FXML
    public void initialize() {
        createButtonList();
        addBoard(Board.createStandardBoard());
        drawBoard();
        Graphics.setStyle(chessTiles);
        addEventHandlers();
        staticDisplay = display;
    }

    @FXML
    private void saveGame(){
        Game game = Statics.getGame();
        GameDAO gameDAO = new GameDAOImpl();
        gameDAO.save(game,0);
    }

    @FXML
    private void newGame(){
        Dialogs.resetGame();
    }

    @FXML
    private void exitGame(){
        System.exit(0);
    }

    @FXML
    private void loadGame(){ SceneManager.setActualScene("start_page"); }

    @FXML
    private void surrender(){
        Dialogs.surrenderDialog();
    }

    @FXML
    public void tie() {
        Dialogs.tieDialog();
    }

    private void createButtonList(){
        chessTiles.addAll(Arrays.asList(pane00, pane01, pane02, pane03, pane04, pane05, pane06, pane07, pane08, pane09, pane10, pane11, pane12, pane13, pane14, pane15, pane16, pane17, pane18, pane19, pane20, pane21, pane22, pane23, pane24, pane25, pane26, pane27, pane28, pane29, pane30, pane31, pane32, pane33, pane34, pane35, pane36, pane37, pane38, pane39, pane40, pane41, pane42, pane43, pane44, pane45, pane46, pane47, pane48, pane49, pane50, pane51, pane52, pane53, pane54, pane55, pane56, pane57, pane58, pane59, pane60, pane61, pane62, pane63));
    }

    private void addEventHandlers(){
        chessTiles.forEach(thisButton -> thisButton.setOnMouseClicked(event ->
        {
            Graphics.setStyle(chessTiles);
            if (event.getButton() == MouseButton.PRIMARY)
                sourceDestination.setSource(Helper.getButtonCoordinate(thisButton));
            else if (event.getButton() == MouseButton.SECONDARY)
                sourceDestination.setDestination(Helper.getButtonCoordinate(thisButton));

            //Highlight possible moves
            for (Move highlightedMove: lastBoard().getAllLegalMoves())
                if (highlightedMove.getCurrentCoordinate() == sourceDestination.getSource())
                    Graphics.highlight(chessTiles.get(highlightedMove.getDestinationCoordinate()));

            if (sourceDestination.checkIfBothSet()) {
                try {
                    Alliance allianceOfPiece = lastBoard().getTile(sourceDestination.getSource()).getPiece().getPieceAlliance();
                    Alliance currentPlayerAlliance = lastBoard().currentPlayer().getAlliance();
                    if (allianceOfPiece == currentPlayerAlliance) {
                        Graphics.setStyle(chessTiles);
                        Move move = Move.MoveFactory.createMove(lastBoard(), sourceDestination.getSource(), sourceDestination.getDestination());
                        Board board = move.execute();
                        Statics.getGame().getBoards().add(board);
                        drawBoard();
                        Alliance gameOver = board.victory.checkVictory();
                        if (gameOver != null) Dialogs.victoryDialog();
                    } else
                        System.out.println("Nem te következel!");
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }));
    }
}
