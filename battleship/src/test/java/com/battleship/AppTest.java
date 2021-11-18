package com.battleship;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest {
    Game game = new Game();
    Square square = new Square();
    Ship ship = new Ship(5);

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
