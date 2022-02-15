package hu.chess.dao;

import hu.chess.engine.board.Board;
import hu.chess.model.Game;
import hu.chess.statics.Statics;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameDAOImpl implements GameDAO{
    private static final String SELECT_ALL = "SELECT * FROM Game";
    private static final String SELECT_BY_ID = "SELECT * FROM Game WHERE GameId=?";
    private static final String INSERT_GAME = "INSERT INTO Game (WhitePLayerName, BlackPlayerName, TimeOfPlay, Winner) VALUES (?,?,?,?)";
    private static final String UPDATE_GAME = "UPDATE Game SET WhitePlayerName=?, BlackPlayerName=?, Winner=? WHERE GameId=?";

    public GameDAOImpl() { }

    @Override
    public List<Game> findAll() {
        List<Game> resultList = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(Properties.connectionUrl)){
            Statement stm = c.createStatement();
            ResultSet resultSet = stm.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                resultList.add(getGame(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public Game findById(final int id) {
        Game result = null;
        try(Connection c = DriverManager.getConnection(Properties.connectionUrl)){
            PreparedStatement stm = c.prepareStatement(SELECT_BY_ID);
            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery(SELECT_BY_ID);
            result = getGame(resultSet);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void save(Game game, int gameResult) {
        try(Connection c = DriverManager.getConnection(Properties.connectionUrl)){
            PreparedStatement stm;
            if(game.getGameId() <= 0) {
                stm = c.prepareStatement(INSERT_GAME);
                stm.setString(1, game.getWhitePlayerName());
                stm.setString(2, game.getBlackPlayerName());
                stm.setString(3, LocalDate.now().toString());
                stm.setInt(4, gameResult);
            } else {
                stm = c.prepareStatement(UPDATE_GAME);
                stm.setInt(4,game.getGameId());
                stm.setString(1, game.getWhitePlayerName());
                stm.setString(2, game.getBlackPlayerName());
                stm.setInt(3, gameResult);
            }

            int affectedRows = stm.executeUpdate();
            if(affectedRows == 0) return;

            BoardDAO boardDAO = new BoardDAOImpl();
            if(game.getGameId() <= 0){
                ResultSet genKeys = stm.getGeneratedKeys();
                if(genKeys.next()) {
                    game.setGameId(genKeys.getInt(1));
                    boardDAO.save(game.getBoards(),genKeys.getInt(1));
                }
            }
            else{
                //Add only the new boards to the database not ones that already present
                List<Board> existingBoards = boardDAO.findByGameId(game);
                List<Board> copy = new ArrayList<>();
                for (int i = existingBoards.size(); i < Statics.getGame().getBoards().size(); i++) {
                    copy.add(Statics.getGame().getBoards().get(i));
                }
                boardDAO.save(copy,game.getGameId());
                System.out.println(copy.size());
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private Game getGame(ResultSet resultSet) throws SQLException {
        Game game = new Game();
        game.setGameId(resultSet.getInt("GameId"));
        game.setWhitePlayerName(resultSet.getString("WhitePlayerName"));
        game.setBlackPlayerName(resultSet.getString("BlackPlayerName"));
        game.setTimeOfPlay(Date.valueOf(resultSet.getString("TimeOfPlay")).toLocalDate());
        game.setWinner(resultSet.getInt("Winner"));
        return game;
    }
}
