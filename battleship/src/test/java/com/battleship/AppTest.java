package com.battleship;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;

@RunWith(JfxRunner.class)
public class AppTest {
    Game game;
    Square square;
    Ship ship;

    @Before
    public void setup() {
        game = new Game();
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
        assertEquals(1, game.getTurn());
    }

    @Test
    public void changingTurnWorks() {
        assertEquals(1, game.getTurn());
        game.changeTurn();
        assertEquals(2, game.getTurn());
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
}
