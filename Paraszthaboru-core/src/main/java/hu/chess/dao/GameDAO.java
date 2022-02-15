package hu.chess.dao;

import hu.chess.model.Game;

import java.util.List;

public interface GameDAO {

    List<Game> findAll();
    Game findById(int id);
    void save(Game game, int gameResult);
}
