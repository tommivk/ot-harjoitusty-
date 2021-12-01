package com.battleship;

import static org.junit.Assert.assertEquals;

import com.battleship.domain.Game;
import com.battleship.domain.Ship;
import com.battleship.domain.Square;
import com.battleship.domain.Turn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.control.Button;

@RunWith(JfxRunner.class)
public class AppTest {
    Game game;
    Square square;
    Ship ship;

    @Before
    public void setup() {
        game = new Game(10);
        square = new Square();
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
    public void initialGameTurnIsCorrect() {
        assertEquals(Turn.PLAYER1, game.getTurn());
    }

    @Test
    public void changingTurnWorks() {
        assertEquals(Turn.PLAYER1, game.getTurn());
        game.changeTurn();
        assertEquals(Turn.PLAYER2, game.getTurn());
    }

    @Test
    public void newSquareDoesNotHaveShip() {
        assertEquals(null, square.getShip());
    }

    @Test
    public void initialSquareIsHitIsFalse() {
        assertEquals(false, square.getIsHit());
    }

    @Test
    public void addingShipToSquareWorks() {
        square.addShip(ship);
        assertEquals(ship, square.getShip());
    }

    @Test
    public void hittingSquareWorksAndShipHealthisReduced() {
        square.addShip(ship);
        assertEquals(5, square.getShip().getSquaresLeft());
        square.hitSquare();
        assertEquals(true, square.getIsHit());
        assertEquals(4, square.getShip().getSquaresLeft());
    }

    @Test
    public void newShipHasCorrectSize() {
        assertEquals(5, ship.getSquaresLeft());
    }

    @Test
    public void newShipIsNotDead() {
        assertEquals(false, ship.isDead());
    }

    @Test
    public void changingButtonColorToBlackWorks() {
        square.setBlackButtonColor();
        Button button = square.getButton();
        assertEquals("-fx-background-color: black", button.getStyle());
    }

    @Test
    public void removingButtonColorWorks() {
        square.setBlackButtonColor();
        square.removeButtonColor();
        Button button = square.getButton();
        assertEquals("", button.getStyle());
    }
}
