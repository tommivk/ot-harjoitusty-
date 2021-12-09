package com.battleship.dao;

import java.sql.*;

import com.battleship.domain.Game;
import com.battleship.domain.User;

public class DBGameDao implements GameDao {

    public Game createGame(User playerOne, User playerTwo) throws SQLException {
        Connection db = DriverManager.getConnection("jdbc:sqlite:data.db");

        try {
            PreparedStatement p = db.prepareStatement(
                    "INSERT INTO Games(playerone, playertwo, playeroneshots, playertwoshots, finished) VALUES (?, ?, ?, ? ,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, 0);
            p.setInt(2, 1);
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
