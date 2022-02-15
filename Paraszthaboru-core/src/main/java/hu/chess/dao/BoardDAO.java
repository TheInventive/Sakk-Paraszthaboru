package hu.chess.dao;

import hu.chess.engine.board.Board;
import hu.chess.model.Game;

import java.util.List;

public interface BoardDAO {

    List<Board> findByGameId(Game game);
    void save(List<Board> boards, int gameId);
}
