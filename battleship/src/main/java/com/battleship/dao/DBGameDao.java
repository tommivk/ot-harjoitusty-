package com.battleship.dao;

import java.sql.*;

import com.battleship.domain.Game;
import com.battleship.domain.User;

public class DBGameDao implements GameDao {

    public int getPlayerShotCount(String databaseAdress, int playerId) throws SQLException {
        Connection db = DriverManager.getConnection(databaseAdress);
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

    public int getPlayerHitCount(String databaseAdress, int playerId) throws SQLException {
        Connection db = DriverManager.getConnection(databaseAdress);
        try {
            PreparedStatement p = db.prepareStatement(
                    "SELECT ((SELECT COALESCE(SUM(playeronehits), 0) FROM Games WHERE playerone = ?) + (SELECT COALESCE(SUM(playertwohits), 0) from games WHERE playertwo = ?)) as hits;");
            p.setInt(1, playerId);
            p.setInt(2, playerId);
            ResultSet r = p.executeQuery();
            return r.getInt("hits");

        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            db.close();
        }
    }

    public void addPlayerOneShot(String databaseAdress, int gameId) throws SQLException {
        Connection db = DriverManager.getConnection(databaseAdress);
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

    public void addPlayerTwoShot(String databaseAdress, int gameId) throws SQLException {
        Connection db = DriverManager.getConnection(databaseAdress);
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

    public void addPlayerOneHit(String databaseAdress, int gameId) throws SQLException {
        Connection db = DriverManager.getConnection(databaseAdress);
        try {
            PreparedStatement p = db
                    .prepareStatement("UPDATE Games SET playeronehits = playeronehits + 1 WHERE id=?");
            p.setInt(1, gameId);

            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.close();
        }
    }

    public void addPlayerTwoHit(String databaseAdress, int gameId) throws SQLException {
        Connection db = DriverManager.getConnection(databaseAdress);
        try {
            PreparedStatement p = db
                    .prepareStatement("UPDATE Games SET playertwohits = playertwohits + 1 WHERE id=?");
            p.setInt(1, gameId);

            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.close();
        }
    }

    public void setWinner(String databaseAdress, int gameId, int playerId) throws SQLException {
        Connection db = DriverManager.getConnection(databaseAdress);
        try {
            PreparedStatement p = db
                    .prepareStatement("UPDATE Games SET winner = ? WHERE id=?");
            p.setInt(1, playerId);
            p.setInt(2, gameId);

            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.close();
        }
    }

    public int getPlayerWinCount(String databaseAdress, int playerId) throws SQLException {
        Connection db = DriverManager.getConnection(databaseAdress);
        try {
            PreparedStatement p = db.prepareStatement(
                    "SELECT COUNT(winner) as wins FROM Games WHERE winner = ?");
            p.setInt(1, playerId);
            ResultSet r = p.executeQuery();
            return r.getInt("wins");

        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            db.close();
        }
    }

    public int getPlayerGameCount(String databaseAdress, int playerId) throws SQLException {
        Connection db = DriverManager.getConnection(databaseAdress);
        try {
            PreparedStatement p = db.prepareStatement(
                    "SELECT((SELECT COUNT(playerone) FROM Games WHERE playerone = ? AND winner IS NOT NULL) + (SELECT COUNT(playertwo) FROM Games WHERE playertwo = ? AND winner IS NOT NULL)) as gameCount;");
            p.setInt(1, playerId);
            p.setInt(2, playerId);
            ResultSet r = p.executeQuery();
            return r.getInt("gameCount");

        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            db.close();
        }
    }

    public Game createGame(String databaseAdress, User playerOne, User playerTwo) throws SQLException {
        Connection db = DriverManager.getConnection(databaseAdress);

        try {
            PreparedStatement p = db.prepareStatement(
                    "INSERT INTO Games(playerone, playertwo, playeroneshots, playertwoshots, playeronehits, playertwohits) VALUES (?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, playerOne.getId());
            p.setInt(2, playerTwo.getId());
            p.setInt(3, 0);
            p.setInt(4, 0);
            p.setInt(5, 0);
            p.setInt(6, 0);

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
