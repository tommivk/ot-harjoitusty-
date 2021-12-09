package com.battleship.dao;

import java.sql.*;

import com.battleship.domain.Game;
import com.battleship.domain.User;

public class DBGameDao implements GameDao {

    public int getPlayerShotCount(int playerId) throws SQLException {
        Connection db = DriverManager.getConnection("jdbc:sqlite:data.db");
        try {
            PreparedStatement p = db.prepareStatement(
                    "SELECT ((SELECT COALESCE(SUM(playeroneshots), 0) FROM Games WHERE playerone = ?) + (SELECT COALESCE(SUM(playertwoshots), 0) from games WHERE playertwo = ?)) as shots;");
            p.setInt(1, playerId);
            p.setInt(2, playerId);
            ResultSet r = p.executeQuery();
            return r.getInt("shots");

        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            db.close();
        }
    }

    public void addPlayerOneShot(int gameId) throws SQLException {
        Connection db = DriverManager.getConnection("jdbc:sqlite:data.db");
        try {
            PreparedStatement p = db
                    .prepareStatement("UPDATE Games SET playeroneshots = playeroneshots + 1 WHERE id=?");
            p.setInt(1, gameId);

            p.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.close();
        }
    }

    public void addPlayerTwoShot(int gameId) throws SQLException {
        Connection db = DriverManager.getConnection("jdbc:sqlite:data.db");
        try {
            PreparedStatement p = db
                    .prepareStatement("UPDATE Games SET playertwoshots = playertwoshots + 1 WHERE id=?");
            p.setInt(1, gameId);

            p.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.close();
        }
    }

    public Game createGame(User playerOne, User playerTwo) throws SQLException {
        Connection db = DriverManager.getConnection("jdbc:sqlite:data.db");

        try {
            PreparedStatement p = db.prepareStatement(
                    "INSERT INTO Games(playerone, playertwo, playeroneshots, playertwoshots, finished) VALUES (?, ?, ?, ? ,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, playerOne.getId());
            p.setInt(2, playerTwo.getId());
            p.setInt(3, 0);
            p.setInt(4, 0);
            p.setBoolean(5, false);

            p.executeUpdate();

            ResultSet resultSet = p.getGeneratedKeys();
            resultSet.next();
            int gameId = resultSet.getInt(1);
            return new Game(10, gameId, playerOne, playerTwo);

        } catch (SQLException e) {
            System.out.println("Couldn't create new game" + e);
            return null;
        } finally {
            db.close();
        }
    }
}
