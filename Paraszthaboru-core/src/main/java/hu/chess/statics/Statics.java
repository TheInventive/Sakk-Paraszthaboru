package hu.chess.statics;

import hu.chess.model.Game;

import java.util.ArrayList;

public class Statics {
    private static Game game = new Game(new ArrayList<>());
    public static Game getGame(){
        return game;
    }
    public static void setGame(Game newGame){ game = newGame; }
}
