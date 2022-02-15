package hu.desktop.gui;

import hu.chess.engine.board.Board;
import hu.chess.engine.board.BoardUtils;
import hu.chess.engine.player.BlackPlayer;
import hu.chess.engine.player.WhitePlayer;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public final class Graphics {

    private static final String WHITE_COLOR = "-fx-background-color: #ffffff;";
    private static final String HIGHLIGHT = "-fx-background-color: #53aa45;";
    private static final String BLACK_COLOR = "-fx-background-color: #6c6c6c;";

    public static void setStyle(List<Button> allPanes){
        int second = 0;
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            //allPanes.get(i).setText(allPanes.get(i).getId());
            if(Helper.isEven(second)){
                if(Helper.isEven(i)) allPanes.get(i).setStyle(WHITE_COLOR);
                else allPanes.get(i).setStyle(BLACK_COLOR);
            }else {
                if(Helper.isOdd(i)) allPanes.get(i).setStyle(WHITE_COLOR);
                else allPanes.get(i).setStyle(BLACK_COLOR);
            }
            if((i+1) % BoardUtils.NUM_TILE_PER_ROW == 0) second++;
        }
    }

    public static void highlight(Button button){
        button.setStyle(HIGHLIGHT);
    }

    public static void drawPieces(List<Button> allPanes, Board board){
        String boardString = board.toString();
        for (int i = 0; i < 64; i++) {
            if(boardString.charAt(i) == 'p')
                drawBlackPawn(allPanes.get(i));
            else if(boardString.charAt(i) == 'P')
                drawWhitePawn(allPanes.get(i));
            else
                clearImage(allPanes.get(i));
        }
    }

    public static void drawBlackPawn(Button button){
        Image img = new Image(BlackPlayer.graphicsPath);
        drawPawn(button, img);
    }

    public static void drawWhitePawn(Button button){
        Image img = new Image(WhitePlayer.graphicsPath);
        drawPawn(button, img);
    }

    private static void drawPawn(Button button, Image image){
        button.setPrefSize(100, 80);
        ImageView view = new ImageView(image);
        view.setFitHeight(50);
        view.setFitWidth(50);
        button.setGraphic(view);
    }

    public static void clearImage(Button button){
        button.setGraphic(null);
    }

}
