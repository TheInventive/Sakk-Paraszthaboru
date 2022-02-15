package hu.chess.dao;

import hu.chess.engine.board.Board;
import hu.chess.model.BoardModel;
import hu.chess.model.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAOImpl implements BoardDAO{
    private static final String SELECT_BOARDS_BY_GAME_ID = "SELECT * FROM Board WHERE GameId=? ORDER BY BoardNumber";
    private static final String INSERT_BOARDS = "INSERT INTO Board (GameId, BoardString, BoardNumber) VALUES (?,?,?)";
    private static final String SELECT_COUNT = "SELECT COUNT() FROM Board WHERE GameId=?";

    public BoardDAOImpl() { }

    @Override
    public List<Board> findByGameId(Game game) {
        List<Board> boards = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(Properties.connectionUrl)) {
            PreparedStatement statement = c.prepareStatement(SELECT_BOARDS_BY_GAME_ID);
            statement.setInt(1, game.getGameId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Board board = new Board(resultSet.getString("BoardString"));
                boards.add(board);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return boards;
    }

    @Override
    public void save(List<Board> boards, int gameId) {
        int rows = getRowsInDatabase(gameId);
        System.out.println(rows);
        for (int i = 0, boardsSize = boards.size(); i < boardsSize; i++) {
            BoardModel board = new BoardModel();
            board.setGameId(gameId);
            board.setBoardNumber(rows++);
            board.setBoardString(boards.get(i).toString());
            try (Connection c = DriverManager.getConnection(Properties.connectionUrl)) {
                PreparedStatement stm = c.prepareStatement(INSERT_BOARDS);
                stm.setInt(1, board.getGameId());
                stm.setString(2, board.getBoardString());
                stm.setInt(3, board.getBoardNumber());

                int affectedRows = stm.executeUpdate();
                if (affectedRows == 0) return;

                if (board.getBoardId() <= 0) {
                    ResultSet genKeys = stm.getGeneratedKeys();
                    if (genKeys.next())
                        board.setBoardId(genKeys.getInt(1));
                }

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }
    private int getRowsInDatabase(int gameId){
        try(Connection c = DriverManager.getConnection(Properties.connectionUrl)) {
            PreparedStatement statement = c.prepareStatement(SELECT_COUNT);
            statement.setInt(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt("COUNT()");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }
}
