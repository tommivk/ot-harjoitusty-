package com.battleship;

import static org.junit.Assert.assertEquals;

import java.sql.*;

import com.battleship.dao.DBGameDao;
import com.battleship.domain.GameService;
import com.battleship.domain.User;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class GameServiceTest {
    GameService gameService;

    @Before
    public void setUp() throws SQLException {
        String testDatabaseAdress = "jdbc:sqlite:testdata.db";
        Connection db = DriverManager.getConnection(testDatabaseAdress);

        try {
            Statement s = db.createStatement();

            String dropUsers = "DROP TABLE IF EXISTS Users;";
            String dropGame = "DROP TABLE IF EXISTS Games;";
            s.execute(dropUsers);
            s.execute(dropGame);
            String createUsers = "CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, username TEXT UNIQUE);";
            String createGame = "CREATE TABLE IF NOT EXISTS Games(id INTEGER PRIMARY KEY, finished BOOLEAN,playeroneshots INTEGER, playertwoshots INTEGER, playeronehits INTEGER, playertwohits INTEGER, winner INTEGER, playerone INTEGER, playertwo INTEGER, FOREIGN KEY(playerone) REFERENCES Users(id),FOREIGN KEY(playertwo) REFERENCES Users(id), FOREIGN KEY(winner) REFERENCES Users(id));";

            s.execute(createUsers);
            s.execute(createGame);
        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        } finally {
            db.close();
        }

        DBGameDao dbGameDao = new DBGameDao();
        gameService = new GameService(dbGameDao, "jdbc:sqlite:testdata.db");
    }

    @AfterClass
    public static void dropTables() throws SQLException {
        String testDatabaseAdress = "jdbc:sqlite:testdata.db";
        Connection db = DriverManager.getConnection(testDatabaseAdress);
        try {
            String dropUsers = "DROP TABLE Users;";
            String dropGame = "DROP TABLE Games;";

            Statement s = db.createStatement();

            s.execute(dropUsers);
            s.execute(dropGame);
        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        } finally {
            db.close();
        }
    }

    @Test
    public void creatingNewGameWorks() {
        boolean res = gameService.createGame(new User("user", 0), new User("userTwo", 1));
        assertEquals(true, res);
    }

    @Test
    public void addingNewPlayerOneShotWorks() {
        gameService.createGame(new User("user", 0), new User("userTwo", 1));
        gameService.addPlayerOneShot();
        assertEquals(1, gameService.getPlayerShotCount(0));
        gameService.addPlayerOneShot();
        assertEquals(2, gameService.getPlayerShotCount(0));
    }

    @Test
    public void addingNewPlayerTwoShotWorks() {
        gameService.createGame(new User("user", 0), new User("userTwo", 1));
        gameService.addPlayerTwoShot();
        assertEquals(1, gameService.getPlayerShotCount(1));
        gameService.addPlayerTwoShot();
        assertEquals(2, gameService.getPlayerShotCount(1));
    }

    @Test
    public void addingNewPlayerOneHitWorks() {
        gameService.createGame(new User("user", 0), new User("userTwo", 1));
        gameService.addPlayerOneHit();
        assertEquals(1, gameService.getPlayerHitCount(0));
        gameService.addPlayerOneHit();
        assertEquals(2, gameService.getPlayerHitCount(0));
    }

    @Test
    public void addingNewPlayerTwoHitWorks() {
        gameService.createGame(new User("user", 0), new User("userTwo", 1));
        gameService.addPlayerTwoHit();
        assertEquals(1, gameService.getPlayerHitCount(1));
        gameService.addPlayerTwoHit();
        assertEquals(2, gameService.getPlayerHitCount(1));
    }
}
