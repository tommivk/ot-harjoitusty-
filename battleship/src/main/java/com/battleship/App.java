package com.battleship;

import java.sql.*;
import com.battleship.ui.BattleshipUi;

public class App {
    public static void main(String[] args) throws SQLException {
        try {
            Connection db = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement s = db.createStatement();
            s.execute("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, username TEXT UNIQUE)");
        } catch (SQLException e) {
            System.out.println(e);
        }
        BattleshipUi.main(args);
    }
}
