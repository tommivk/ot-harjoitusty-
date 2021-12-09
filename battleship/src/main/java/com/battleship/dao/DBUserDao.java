package com.battleship.dao;

import com.battleship.domain.User;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class DBUserDao implements UserDao {

    public List<User> getAll() {
        List<User> list = new ArrayList<User>();

        try {
            Connection db = DriverManager.getConnection("jdbc:sqlite:data.db");
            Statement s = db.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM Users");

            while (r.next()) {
                list.add(new User(r.getString("username")));
            }

            return list;
        } catch (SQLException e) {

        }
        return list;
    }

    public User create(String username) {
        try {
            Connection db = DriverManager.getConnection("jdbc:sqlite:data.db");

            PreparedStatement p = db.prepareStatement("INSERT INTO Users(username) VALUES (?)");
            p.setString(1, username);
            p.executeUpdate();

            System.out.print("New user ");
            System.out.print(username);
            System.out.println(" added");
            return new User(username);
        } catch (SQLException e) {
            System.out.println("Couldn't add new user");
            return null;
        }
    }
}
