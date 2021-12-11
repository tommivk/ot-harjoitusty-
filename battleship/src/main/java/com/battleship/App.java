package com.battleship;

import java.sql.*;
import java.sql.SQLException;
import com.battleship.ui.BattleshipUi;

public class App {
    public static void main(String[] args) throws SQLException {
        Connection db = DriverManager.getConnection("jdbc:sqlite:data.db");
        try {
            Statement s = db.createStatement();
            String createUsers = "CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, username TEXT UNIQUE);";
            String createGame = "CREATE TABLE IF NOT EXISTS Games(id INTEGER PRIMARY KEY, finished BOOLEAN,playeroneshots INTEGER, playertwoshots INTEGER, playeronehits INTEGER, playertwohits INTEGER, winner INTEGER, playerone INTEGER, playertwo INTEGER, FOREIGN KEY(playerone) REFERENCES Users(id),FOREIGN KEY(playertwo) REFERENCES Users(id), FOREIGN KEY(winner) REFERENCES Users(id));";
            String addComputer = "INSERT OR IGNORE INTO Users(username) VALUES(\"Computer\");";
            s.execute(createUsers);
            s.execute(createGame);
            s.execute(addComputer);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            db.close();
        }
        BattleshipUi.main(args);
    }
}
