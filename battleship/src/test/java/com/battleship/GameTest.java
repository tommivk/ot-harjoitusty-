package com.battleship;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import com.battleship.domain.Game;
import com.battleship.domain.Ship;
import com.battleship.domain.ShipDirection;
import com.battleship.domain.Square;
import com.battleship.domain.Turn;
import com.battleship.domain.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.paint.Color;

@RunWith(JfxRunner.class)
public class GameTest {
    Game game;
    Ship ship;

    @Before
    public void setup() {
        game = new Game(10);
        ship = new Ship(5);
    }

    @Test
    public void newGameIsNotOver() {
        assertEquals(false, game.isGameOver());
    }

    @Test
    public void settingGameOverWorks() {
        game.setGameOver();
        assertEquals(true, game.isGameOver());
    }

    @Test
    public void gettingPlayer1SquaresWorks() {
        Square[][] squares = game.getPlayerOneSquares();
        assertEquals(10, squares[0].length);
    }

    @Test
    public void gettingPlayer2SquaresWorks() {
        Square[][] squares = game.getPlayerTwoSquares();
        assertEquals(10, squares[0].length);
    }

    @Test
    public void initialGameTurnIsCorrect() {
        assertEquals(Turn.PLAYER1, game.getTurn());
    }

    @Test
    public void changingTurnWorks() {
        assertEquals(Turn.PLAYER1, game.getTurn());
        game.changeTurn();
        assertEquals(Turn.PLAYER2, game.getTurn());
        game.changeTurn();
        assertEquals(Turn.PLAYER1, game.getTurn());
    }

    @Test
    public void gettingDirectionWorks() {
        assertEquals(ShipDirection.VERTICAL, game.getShipDirection());
    }

    @Test
    public void changingShipDirectionWorks() {
        assertEquals(ShipDirection.VERTICAL, game.getShipDirection());
        game.changeShipDirection();
        assertEquals(ShipDirection.HORIZONTAL, game.getShipDirection());
        game.changeShipDirection();
        assertEquals(ShipDirection.VERTICAL, game.getShipDirection());
    }

    @Test
    public void peekingNextShipWorksAndDoesntPop() {
        Ship p1ship = game.peekNextPlayerOneShip();
        Ship p2ship = game.peekNextPlayerTwoShip();
        assertEquals(5, p1ship.getSize());
        assertEquals(5, p2ship.getSize());
        Ship p1ship2 = game.peekNextPlayerOneShip();
        Ship p2ship2 = game.peekNextPlayerTwoShip();
        assertEquals(5, p1ship2.getSize());
        assertEquals(5, p2ship2.getSize());
    }

    @Test
    public void getNewPlayer1ShipWorks() {
        Ship ship = game.getNewPlayerOneShip();
        assertEquals(5, ship.getSize());
        Ship ship2 = game.getNewPlayerOneShip();
        assertEquals(4, ship2.getSize());
    }

    @Test
    public void getNewPlayer2ShipWorks() {
        Ship ship = game.getNewPlayerTwoShip();
        assertEquals(5, ship.getSize());
        Ship ship2 = game.getNewPlayerTwoShip();
        assertEquals(4, ship2.getSize());
    }

    @Test
    public void player1ShipsIsEmptyWorks() {
        game.getNewPlayerOneShip();
        game.getNewPlayerOneShip();
        game.getNewPlayerOneShip();
        game.getNewPlayerOneShip();
        game.getNewPlayerOneShip();
        assertEquals(false, game.playerOneShipsIsEmpty());
        game.getNewPlayerOneShip();

        assertEquals(true, game.playerOneShipsIsEmpty());
    }

    @Test
    public void player2ShipsIsEmptyWorks() {
        game.getNewPlayerTwoShip();
        game.getNewPlayerTwoShip();
        game.getNewPlayerTwoShip();
        game.getNewPlayerTwoShip();
        game.getNewPlayerTwoShip();
        assertEquals(false, game.playerTwoShipsIsEmpty());
        game.getNewPlayerTwoShip();

        assertEquals(true, game.playerTwoShipsIsEmpty());
    }

    @Test
    public void placingNewPlayer1ShipWorks() {
        game.placeShip(0, 0, 1);
        assertEquals(true, game.getPlayerOneSquares()[0][0].hasShip());
        assertEquals(true, game.getPlayerOneSquares()[4][0].hasShip());
        assertEquals(false, game.getPlayerOneSquares()[5][0].hasShip());
        assertEquals(false, game.getPlayerOneSquares()[0][1].hasShip());
    }

    @Test
    public void placingNewPlayer2ShipWorks() {
        game.placeShip(0, 0, 2);
        assertEquals(true, game.getPlayerTwoSquares()[0][0].hasShip());
        assertEquals(true, game.getPlayerTwoSquares()[4][0].hasShip());
        assertEquals(false, game.getPlayerTwoSquares()[5][0].hasShip());
        assertEquals(false, game.getPlayerTwoSquares()[0][1].hasShip());
    }

    @Test
    public void hittingPlacedShipWorks() {
        game.placeShip(0, 0, 1);
        assertEquals(false, game.getPlayerTwoSquares()[5][0].hitSquare());
        assertEquals(false, game.getPlayerTwoSquares()[0][1].hitSquare());
        assertEquals(true, game.getPlayerOneSquares()[0][0].hitSquare());
        assertEquals(true, game.getPlayerOneSquares()[4][0].hitSquare());

    }

    @Test
    public void highlightingSquaresWorks() {
        game.highlightSquares(0, 0, 1);

        Square[][] squares = game.getPlayerOneSquares();

        assertEquals(Color.GREY, squares[0][0].getButton().getFill());
        assertEquals(Color.GREY, squares[4][0].getButton().getFill());
    }

    @Test
    public void removingHighlightFromSquaresWorks() {
        game.highlightSquares(0, 0, 1);

        Square[][] squares = game.getPlayerOneSquares();

        game.removeButtonImage(0, 0, 1);
        assertEquals(Color.WHITESMOKE, squares[0][0].getButton().getFill());
        assertEquals(Color.WHITESMOKE, squares[4][0].getButton().getFill());
    }

    @Test
    public void allPlayerOneShipsDeadWorks() {
        game.placeShip(0, 0, 1);
        assertEquals(false, game.allPlayerOneShipsDead());
        Square[][] squares = game.getPlayerOneSquares();
        squares[0][0].hitSquare();
        squares[1][0].hitSquare();
        squares[2][0].hitSquare();
        squares[3][0].hitSquare();
        squares[4][0].hitSquare();
        assertEquals(true, game.allPlayerOneShipsDead());
    }

    @Test
    public void allPlayerTwoShipsDeadWorks() {
        game.placeShip(0, 0, 2);
        assertEquals(false, game.allPlayerTwoShipsDead());
        Square[][] squares = game.getPlayerTwoSquares();
        squares[0][0].hitSquare();
        squares[1][0].hitSquare();
        squares[2][0].hitSquare();
        squares[3][0].hitSquare();
        squares[4][0].hitSquare();
        assertEquals(true, game.allPlayerOneShipsDead());
    }

    @Test
    public void getPlayerOneShipsWorks() {
        Stack<Ship> ships = game.getPlayerOneShips();
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

    @Test
    public void clearingButtonColorsWorks() {
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                game.getPlayerOneSquares()[i][k].setGreyButtonColor();
            }
        }
        assertEquals(Color.GREY, game.getPlayerOneSquares()[5][5].getButton().getFill());
        game.clearButtonColors(1);
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                assertEquals(Color.WHITESMOKE, game.getPlayerOneSquares()[5][5].getButton().getFill());
            }
        }
    }

    @Test
    public void setAndGetAgainstComputerWorks() {
        game.setIsAgainstComputer(true);
        assertEquals(true, game.getIsAgainstComputer());
        game.setIsAgainstComputer(false);
        assertEquals(false, game.getIsAgainstComputer());
    }
}
