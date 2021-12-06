package com.battleship;

import static org.junit.Assert.assertEquals;

import com.battleship.domain.Game;
import com.battleship.domain.Ship;
import com.battleship.domain.ShipDirection;
import com.battleship.domain.Square;
import com.battleship.domain.Turn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    public void gettingPlayer1SquaresWorks() {
        Square[][] squares = game.getPlayer1Squares();
        assertEquals(10, squares[0].length);
    }

    @Test
    public void gettingPlayer2SquaresWorks() {
        Square[][] squares = game.getPlayer2Squares();
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
    public void newSquareDoesNotHaveShip() {
        assertEquals(null, square.getShip());
    }

    @Test
    public void initialSquareIsHitIsFalse() {
        assertEquals(false, square.getIsHit());
    }

    @Test
    public void shipGetSizeWorks() {
        assertEquals(5, ship.getSize());
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
    public void getShipIsDeadWorks() {
        ship.hit();
        assertEquals(false, ship.isDead());
        ship.hit();
        assertEquals(false, ship.isDead());
        ship.hit();
        assertEquals(false, ship.isDead());
        ship.hit();
        assertEquals(false, ship.isDead());
        ship.hit();
        assertEquals(true, ship.isDead());
    }

    @Test
    public void changingButtonColorToBlackWorks() {
        square.setBlackButtonColor();
        Rectangle button = square.getButton();
        assertEquals(Color.BLACK, button.getFill());
    }

    @Test
    public void removingButtonColorWorks() {
        square.setBlackButtonColor();
        square.removeButtonColor();
        Rectangle button = square.getButton();
        assertEquals(Color.WHITESMOKE, button.getFill());
    }

    @Test
    public void peekingNextShipWorksAndDoesntPop() {
        Ship p1ship = game.peekNextPlayer1Ship();
        Ship p2ship = game.peekNextPlayer2Ship();
        assertEquals(5, p1ship.getSize());
        assertEquals(5, p2ship.getSize());
        Ship p1ship2 = game.peekNextPlayer1Ship();
        Ship p2ship2 = game.peekNextPlayer2Ship();
        assertEquals(5, p1ship2.getSize());
        assertEquals(5, p2ship2.getSize());
    }

    @Test
    public void getNewPlayer1ShipWorks() {
        Ship ship = game.getNewPlayer1Ship();
        assertEquals(5, ship.getSize());
        Ship ship2 = game.getNewPlayer1Ship();
        assertEquals(4, ship2.getSize());
    }

    @Test
    public void getNewPlayer2ShipWorks() {
        Ship ship = game.getNewPlayer2Ship();
        assertEquals(5, ship.getSize());
        Ship ship2 = game.getNewPlayer2Ship();
        assertEquals(4, ship2.getSize());
    }

    @Test
    public void player1ShipsIsEmptyWorks() {
        game.getNewPlayer1Ship();
        game.getNewPlayer1Ship();
        game.getNewPlayer1Ship();
        game.getNewPlayer1Ship();
        game.getNewPlayer1Ship();
        assertEquals(false, game.player1ShipsIsEmpty());
        game.getNewPlayer1Ship();

        assertEquals(true, game.player1ShipsIsEmpty());
    }

    @Test
    public void player2ShipsIsEmptyWorks() {
        game.getNewPlayer2Ship();
        game.getNewPlayer2Ship();
        game.getNewPlayer2Ship();
        game.getNewPlayer2Ship();
        game.getNewPlayer2Ship();
        assertEquals(false, game.player2ShipsIsEmpty());
        game.getNewPlayer2Ship();

        assertEquals(true, game.player2ShipsIsEmpty());
    }

    @Test
    public void placingNewPlayer1ShipWorks() {
        game.placeShip(0, 0, 1);
        assertEquals(true, game.getPlayer1Squares()[0][0].hasShip());
        assertEquals(true, game.getPlayer1Squares()[4][0].hasShip());
        assertEquals(false, game.getPlayer1Squares()[5][0].hasShip());
        assertEquals(false, game.getPlayer1Squares()[0][1].hasShip());
    }

    @Test
    public void placingNewPlayer2ShipWorks() {
        game.placeShip(0, 0, 2);
        assertEquals(true, game.getPlayer2Squares()[0][0].hasShip());
        assertEquals(true, game.getPlayer2Squares()[4][0].hasShip());
        assertEquals(false, game.getPlayer2Squares()[5][0].hasShip());
        assertEquals(false, game.getPlayer2Squares()[0][1].hasShip());
    }

    @Test
    public void hittingPlacedShipWorks() {
        game.placeShip(0, 0, 1);
        assertEquals(false, game.getPlayer2Squares()[5][0].hitSquare());
        assertEquals(false, game.getPlayer2Squares()[0][1].hitSquare());
        assertEquals(true, game.getPlayer1Squares()[0][0].hitSquare());
        assertEquals(true, game.getPlayer1Squares()[4][0].hitSquare());

    }
}
