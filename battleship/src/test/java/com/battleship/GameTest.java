package com.battleship;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import com.battleship.domain.Game;
import com.battleship.domain.Ship;
import com.battleship.domain.Square;
import com.battleship.domain.User;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.paint.Color;

public class GameTest {
    Game game;

    @Before
    public void setup() {
        game = new Game(10);
    }

    @Test
    public void highlightingSquaresWorks() {
        game.highlightSquares(0, 0, 1);

        Square[][] squares = game.getPlayer1Squares();

        assertEquals(Color.GREY, squares[0][0].getButton().getFill());
        assertEquals(Color.GREY, squares[4][0].getButton().getFill());
    }

    @Test
    public void removingHighlightFromSquaresWorks() {
        game.highlightSquares(0, 0, 1);

        Square[][] squares = game.getPlayer1Squares();

        game.removeHighlight(0, 0, 1);
        assertEquals(Color.WHITESMOKE, squares[0][0].getButton().getFill());
        assertEquals(Color.WHITESMOKE, squares[4][0].getButton().getFill());
    }

    @Test
    public void allPlayerOneShipsDeadWorks() {
        game.placeShip(0, 0, 1);
        assertEquals(false, game.allPlayer1ShipsDead());
        Square[][] squares = game.getPlayer1Squares();
        squares[0][0].hitSquare();
        squares[1][0].hitSquare();
        squares[2][0].hitSquare();
        squares[3][0].hitSquare();
        squares[4][0].hitSquare();
        assertEquals(true, game.allPlayer1ShipsDead());
    }

    @Test
    public void allPlayerTwoShipsDeadWorks() {
        game.placeShip(0, 0, 2);
        assertEquals(false, game.allPlayer2ShipsDead());
        Square[][] squares = game.getPlayer2Squares();
        squares[0][0].hitSquare();
        squares[1][0].hitSquare();
        squares[2][0].hitSquare();
        squares[3][0].hitSquare();
        squares[4][0].hitSquare();
        assertEquals(true, game.allPlayer1ShipsDead());
    }

    @Test
    public void getPlayerOneShipsWorks() {
        Stack<Ship> ships = game.getPlayer1Ships();
        ships.pop();
        assertEquals(false, ships.isEmpty());
        ships.pop();
        assertEquals(false, ships.isEmpty());
        ships.pop();
        assertEquals(false, ships.isEmpty());
        ships.pop();
        assertEquals(false, ships.isEmpty());
        ships.pop();
        assertEquals(false, ships.isEmpty());
        ships.pop();
        assertEquals(true, ships.isEmpty());
    }

    @Test
    public void setAndGetPlayerOneWorks() {
        User user = new User("test");
        game.setPlayerOne(user);
        assertEquals("test", game.getPlayerOne().getName());
    }

    @Test
    public void setAndGetPlayerTwoWorks() {
        User user = new User("test");
        game.setPlayerTwo(user);
        assertEquals("test", game.getPlayerTwo().getName());
    }
}
