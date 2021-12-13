package com.battleship;

import static org.junit.Assert.assertEquals;

import java.sql.*;
import java.sql.SQLException;
import java.util.List;

import com.battleship.dao.DBUserDao;
import com.battleship.domain.User;
import com.battleship.domain.UserService;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

public class UserServiceTest {
    UserService userService;

    @Before
    public void setUp() throws SQLException {
        String testDatabaseAdress = "jdbc:sqlite:testdata.db";
        Connection db = DriverManager.getConnection(testDatabaseAdress);

        try {
            Statement s = db.createStatement();
            String dropUsers = "DROP TABLE IF EXISTS Users;";
            s.execute(dropUsers);
            String createUsers = "CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, username TEXT UNIQUE);";
            s.execute(createUsers);
        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        } finally {
            db.close();
        }
        userService = new UserService(new DBUserDao(), testDatabaseAdress);
    }

    @After
    public void dropTable() throws SQLException {
        String testDatabaseAdress = "jdbc:sqlite:testdata.db";
        Connection db = DriverManager.getConnection(testDatabaseAdress);

        try {
            PreparedStatement p = db.prepareStatement(
                    "DROP TABLE Users;");
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        } finally {
            db.close();
        }
    }

    @Test
    public void addingNewUserWorks() {
        boolean response = userService.createUser("testUser");
        assertEquals(true, response);
    }

    @Test
    public void addingUserThatAlreadyExistsFails() {
        userService.createUser("testUser");
        boolean response = userService.createUser("testUser");
        assertEquals(false, response);
    }

    @Test
    public void addingNewUserFailsIfDatabaseAddressIsWrong() {
        UserService service = new UserService(new DBUserDao(), "sqlite:testdata.db");
        boolean response = service.createUser("testUser");
        assertEquals(false, response);
    }

    @Test
    public void playerOneLoginWorks() {
        userService.createUser("testUser");
        boolean response = userService.playerOneLogin("testUser");
        assertEquals(true, response);
        assertEquals("testUser", userService.getLoggedPlayerOne().getName());
    }

    @Test
    public void logoutWorks() {
        userService.createUser("testUser");
        userService.playerOneLogin("testUser");
        assertEquals("testUser", userService.getLoggedPlayerOne().getName());
        userService.logout();
        assertEquals(null, userService.getLoggedPlayerOne());
        assertEquals(null, userService.getLoggedPlayerTwo());
    }

    @Test
    public void cannotLoginWithUserThatDoesNotExist() {
        userService.createUser("testUser");
        boolean response = userService.playerOneLogin("hackerman");
        assertEquals(false, response);
        assertEquals(null, userService.getLoggedPlayerOne());
        assertEquals(null, userService.getLoggedPlayerTwo());
        boolean res = userService.playerTwoLogin("testuser2");
        assertEquals(false, res);
        assertEquals(null, userService.getLoggedPlayerOne());
        assertEquals(null, userService.getLoggedPlayerTwo());
    }

    @Test
    public void playerTwoLoginWorks() {
        userService.createUser("testUserTwo");
        boolean response = userService.playerTwoLogin("testUserTwo");
        assertEquals(true, response);
        assertEquals("testUserTwo", userService.getLoggedPlayerTwo().getName());
    }

    @Test
    public void getAllUsersWorks() {
        userService.createUser("testUserOne");
        userService.createUser("testUserTwo");
        userService.createUser("testUserThree");

        List<User> response = userService.getAllUsers();
        assertEquals(3, response.size());
        assertEquals("testUserOne", response.get(0).getName());
        assertEquals("testUserTwo", response.get(1).getName());
        assertEquals("testUserThree", response.get(2).getName());
    }

}
