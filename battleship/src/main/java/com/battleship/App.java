package com.battleship;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.SQLException;
import java.util.Properties;

import com.battleship.ui.BattleshipUi;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));
        String databaseFile = properties.getProperty("databaseFile");

        Connection db = DriverManager.getConnection("jdbc:sqlite:" + databaseFile);

        try {
            Statement s = db.createStatement();
            String createUsers = "CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, username TEXT UNIQUE);";
            String createGame = "CREATE TABLE IF NOT EXISTS Games(id INTEGER PRIMARY KEY, playeroneshots INTEGER, playertwoshots INTEGER, playeronehits INTEGER, playertwohits INTEGER, winner INTEGER, playerone INTEGER, playertwo INTEGER, FOREIGN KEY(playerone) REFERENCES Users(id),FOREIGN KEY(playertwo) REFERENCES Users(id), FOREIGN KEY(winner) REFERENCES Users(id));";
            String addComputer = "INSERT OR IGNORE INTO Users(username) VALUES(\"Computer\");";
            s.execute(createUsers);
            s.execute(createGame);
            s.execute(addComputer);

            BattleshipUi.main(args);
        } catch (SQLException e) {
            System.out.println("Something went wrong with the database connection:\n" + e);
        } finally {
            db.close();
        }

    }
}
