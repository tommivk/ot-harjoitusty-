package com.battleship;

import static org.junit.Assert.assertEquals;

import com.battleship.domain.Game;
import com.battleship.domain.GameService;
import com.battleship.domain.Ship;
import com.battleship.domain.Square;
import com.battleship.enums.ShipDirection;
import com.battleship.enums.Player;
import com.battleship.dao.DBGameDao;
import com.battleship.domain.Computer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;

import java.util.ArrayList;

@RunWith(JfxRunner.class)
public class ComputerTest {
    Game game;
    Ship ship;
    Computer computer;
    Square[][] player1Squares;

    @Before
    public void setup() {
        game = new Game(10);
        ship = new Ship(5);
        computer = game.getComputer();
        player1Squares = game.getPlayerOneSquares();
    }

    @Test
    public void placeComputerShipsPlacesSixUniqueShips() {
        game.getComputer().placeComputerShips();
        Square[][] squares = game.getPlayerTwoSquares();
        ArrayList<Integer> hashCodes = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                if (squares[i][k].hasShip() && !hashCodes.contains(squares[i][k].getShip().hashCode())) {
                    hashCodes.add(squares[i][k].getShip().hashCode());
                }
            }
        }
        assertEquals(6, hashCodes.size());
    }

    @Test
    public void canHitLeftWorks() {
        computer.setPreviousHitCoordinates(5, 6);
        player1Squares[5][5].hitSquare();
        assertEquals(false, computer.canHitLeft());
        computer.setPreviousHitCoordinates(5, 5);
        assertEquals(true, computer.canHitLeft());
    }

    @Test
    public void canHitRightWorks() {
        computer.setPreviousHitCoordinates(5, 6);
        player1Squares[5][7].hitSquare();
        assertEquals(false, computer.canHitRight());
        computer.setPreviousHitCoordinates(5, 5);
        assertEquals(true, computer.canHitRight());
    }

    @Test
    public void canHitTopWorks() {
        computer.setPreviousHitCoordinates(5, 5);
        player1Squares[4][5].hitSquare();
        assertEquals(false, computer.canHitTop());
        computer.setPreviousHitCoordinates(3, 5);
        assertEquals(true, computer.canHitTop());
    }

    @Test
    public void canHitBottomWorks() {
        computer.setPreviousHitCoordinates(5, 5);
        player1Squares[6][5].hitSquare();
        assertEquals(false, computer.canHitBottom());
        computer.setPreviousHitCoordinates(2, 5);
        assertEquals(true, computer.canHitBottom());
    }

    @Test
    public void hitLeftWorks() {
        computer.setPreviousHitCoordinates(5, 5);
        computer.hitLeft();
        assertEquals(true, player1Squares[5][4].getHasBeenHit());
    }

    @Test
    public void hitLeftReturnsTrueAndResetsPrevHitsWhenShipDies() {
        game.changeShipDirection();
        game.placeShip(5, 5, Player.PLAYER1);
        player1Squares[5][6].hitSquare();
        player1Squares[5][7].hitSquare();
        player1Squares[5][8].hitSquare();
        player1Squares[5][9].hitSquare();

        computer.setPreviousHitCoordinates(5, 6);
        boolean res = computer.hitLeft();
        assertEquals(true, res);
        assertEquals(0, computer.getPreviousHits());
    }

    @Test
    public void hitLeftReturnsTrueAndAddsPrevHitsWhenHit() {
        game.changeShipDirection();
        game.placeShip(5, 5, Player.PLAYER1);
        computer.setPreviousHitCoordinates(5, 6);
        boolean res = computer.hitLeft();
        assertEquals(true, res);
        assertEquals(1, computer.getPreviousHits());
    }

    @Test
    public void hitRightWorks() {
        computer.setPreviousHitCoordinates(5, 5);
        computer.hitRight();
        assertEquals(true, player1Squares[5][6].getHasBeenHit());
    }

    @Test
    public void hitRightReturnsTrueAndResetsPrevHitsWhenShipDies() {
        game.changeShipDirection();
        game.placeShip(5, 5, Player.PLAYER1);
        player1Squares[5][5].hitSquare();
        player1Squares[5][6].hitSquare();
        player1Squares[5][7].hitSquare();
        player1Squares[5][8].hitSquare();

        computer.setPreviousHitCoordinates(5, 8);
        boolean res = computer.hitRight();
        assertEquals(true, res);
        assertEquals(0, computer.getPreviousHits());
    }

    @Test
    public void hitRightReturnsTrueAndAddsPrevHitsWhenHit() {
        game.changeShipDirection();
        game.placeShip(5, 5, Player.PLAYER1);
        computer.setPreviousHitCoordinates(5, 5);
        boolean res = computer.hitRight();
        assertEquals(true, res);
        assertEquals(1, computer.getPreviousHits());
    }

    @Test
    public void hitBottomWorks() {
        computer.setPreviousHitCoordinates(5, 5);
        computer.hitBottom();
        assertEquals(true, player1Squares[6][5].getHasBeenHit());
    }

    @Test
    public void hitBottomReturnsTrueAndAddsPrevHitsWhenHit() {
        game.placeShip(5, 5, Player.PLAYER1);
        computer.setPreviousHitCoordinates(5, 5);
        boolean res = computer.hitBottom();
        assertEquals(true, res);
        assertEquals(1, computer.getPreviousHits());
    }

    @Test
    public void hitBottomReturnsTrueAndResetsPrevHitsWhenShipDies() {
        game.placeShip(5, 5, Player.PLAYER1);
        player1Squares[5][5].hitSquare();
        player1Squares[6][5].hitSquare();
        player1Squares[7][5].hitSquare();
        player1Squares[8][5].hitSquare();

        computer.setPreviousHitCoordinates(8, 5);
        boolean res = computer.hitBottom();
        assertEquals(true, res);
        assertEquals(0, computer.getPreviousHits());
    }

    @Test
    public void hitTopWorks() {
        computer.setPreviousHitCoordinates(5, 5);
        computer.hitTop();
        assertEquals(true, player1Squares[4][5].getHasBeenHit());
    }

    @Test
    public void hitTopReturnsTrueAndAddsPrevHitsWhenHit() {
        game.placeShip(5, 5, Player.PLAYER1);
        computer.setPreviousHitCoordinates(6, 5);
        boolean res = computer.hitTop();
        assertEquals(true, res);
        assertEquals(1, computer.getPreviousHits());
    }

    @Test
    public void hitTopReturnsTrueAndResetsPrevHitsWhenShipDies() {
        game.placeShip(5, 5, Player.PLAYER1);
        player1Squares[6][5].hitSquare();
        player1Squares[7][5].hitSquare();
        player1Squares[8][5].hitSquare();
        player1Squares[9][5].hitSquare();

        computer.setPreviousHitCoordinates(6, 5);
        boolean res = computer.hitTop();
        assertEquals(true, res);
        assertEquals(0, computer.getPreviousHits());
    }

    @Test
    public void canHitColumnEndRightWorks() {
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.setPreviousHitCoordinates(0, 0);
        assertEquals(true, computer.canHitColumnEndRight());
        player1Squares[0][2].hitSquare();
        assertEquals(false, computer.canHitColumnEndRight());
    }

    @Test
    public void canHitColumnEndLeftWorks() {
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.setPreviousHitCoordinates(0, 5);
        assertEquals(true, computer.canHitColumnEndLeft());
        player1Squares[0][3].hitSquare();
        assertEquals(false, computer.canHitColumnEndLeft());
    }

    @Test
    public void canHitRowEndTopWorks() {
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.setPreviousHitCoordinates(5, 5);
        assertEquals(true, computer.canHitRowEndTop());
        player1Squares[3][5].hitSquare();
        assertEquals(false, computer.canHitRowEndTop());
    }

    @Test
    public void canHitRowEndBottomWorks() {
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.setPreviousHitCoordinates(5, 5);
        assertEquals(true, computer.canHitRowEndBottom());
        player1Squares[7][5].hitSquare();
        assertEquals(false, computer.canHitRowEndBottom());
    }

    @Test
    public void hitColumnEndRightWorks() {
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.setPreviousHitCoordinates(0, 0);
        computer.hitColumnEndRight();
        assertEquals(true, player1Squares[0][2].getHasBeenHit());

        game.changeShipDirection();
        assertEquals(ShipDirection.HORIZONTAL, game.getShipDirection());

        game.placeShip(1, 1, Player.PLAYER1);
        player1Squares[1][1].hitSquare();
        player1Squares[1][2].hitSquare();
        player1Squares[1][3].hitSquare();
        player1Squares[1][4].hitSquare();

        computer.setPreviousHitCoordinates(1, 1);

        computer.setPreviousHits(4);
        computer.hitColumnEndRight();
        assertEquals(true, player1Squares[1][5].getShip().isDead());
    }

    @Test
    public void hitColumnEndLeftWorks() {
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.setPreviousHitCoordinates(0, 5);
        computer.hitColumnEndLeft();
        assertEquals(true, player1Squares[0][2].getHasBeenHit());

        game.changeShipDirection();
        assertEquals(ShipDirection.HORIZONTAL, game.getShipDirection());
        game.placeShip(1, 1, Player.PLAYER1);
        player1Squares[1][2].hitSquare();
        player1Squares[1][3].hitSquare();
        player1Squares[1][4].hitSquare();
        player1Squares[1][5].hitSquare();

        computer.setPreviousHitCoordinates(1, 5);

        computer.setPreviousHits(4);
        computer.hitColumnEndLeft();
        assertEquals(true, player1Squares[1][1].getShip().isDead());
    }

    @Test
    public void hitRowEndTopWorks() {
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.setPreviousHitCoordinates(5, 5);
        computer.hitRowEndTop();
        assertEquals(true, player1Squares[3][5].getHasBeenHit());

        game.placeShip(1, 6, Player.PLAYER1);
        player1Squares[2][6].hitSquare();
        player1Squares[3][6].hitSquare();
        player1Squares[4][6].hitSquare();
        player1Squares[5][6].hitSquare();

        computer.setPreviousHitCoordinates(5, 6);

        computer.setPreviousHits(4);
        computer.hitRowEndTop();
        assertEquals(true, player1Squares[1][6].getShip().isDead());
    }

    @Test
    public void hitRowEndBottomWorks() {
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.setPreviousHitCoordinates(2, 5);
        computer.hitRowEndBottom();
        assertEquals(true, player1Squares[4][5].getHasBeenHit());

        game.placeShip(1, 6, Player.PLAYER1);
        player1Squares[4][6].hitSquare();
        player1Squares[3][6].hitSquare();
        player1Squares[2][6].hitSquare();
        player1Squares[1][6].hitSquare();

        computer.setPreviousHitCoordinates(1, 6);

        computer.setPreviousHits(4);
        computer.hitRowEndBottom();
        assertEquals(true, player1Squares[5][6].getShip().isDead());

    }

    @Test
    public void hitRandomWorks() {
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                this.player1Squares[i][k].addShip(new Ship(5));
            }
        }

        for (int i = 0; i < 100; i++) {
            computer.hitRandom();
        }

        boolean allHit = true;
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                if (!player1Squares[i][k].getHasBeenHit()) {
                    allHit = false;
                }
            }
        }
        assertEquals(true, allHit);
    }

    @Test
    public void hitRandomReturnsFalseWhenNoHit() {
        boolean res = computer.hitRandom();
        assertEquals(false, res);
    }

    @Test
    public void turnChangesAfterComputersTurn() {
        DBGameDao dbGameDao = new DBGameDao();
        GameService gameService = new GameService(dbGameDao, "");
        game.setTurn(Player.PLAYER2);
        computer.computersTurn(gameService);
        assertEquals(Player.PLAYER1, game.getTurn());
    }

    @Test
    public void canHitRowWorks() {
        computer.setPreviousHitCoordinates(5, 5);
        assertEquals(true, computer.canHitRow());
        player1Squares[4][5].hitSquare();
        assertEquals(true, computer.canHitRow());
        player1Squares[6][5].hitSquare();
        assertEquals(false, computer.canHitRow());
    }

    @Test
    public void hitRowWorks() {
        computer.setPreviousHitCoordinates(5, 5);
        computer.hitRow();
        boolean rowHit = false;
        if (player1Squares[6][5].getHasBeenHit() || player1Squares[4][5].getHasBeenHit()) {
            rowHit = true;
        }
        assertEquals(true, rowHit);

        player1Squares[6][5].hitSquare();
        player1Squares[4][5].hitSquare();
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.hitRow();

        boolean hasHit = false;
        if (player1Squares[7][5].getHasBeenHit() || player1Squares[3][5].getHasBeenHit()) {
            hasHit = true;
        }

        assertEquals(true, hasHit);

    }

    @Test
    public void hitColumnWorks() {
        computer.setPreviousHitCoordinates(5, 5);
        computer.hitColumn();
        boolean rowHit = false;
        if (player1Squares[5][6].getHasBeenHit() || player1Squares[5][4].getHasBeenHit()) {
            rowHit = true;
        }
        assertEquals(true, rowHit);

        player1Squares[5][6].hitSquare();
        player1Squares[5][4].hitSquare();
        computer.addPreviousHit();
        computer.addPreviousHit();
        computer.hitColumn();

        boolean hasHit = false;
        if (player1Squares[5][3].getHasBeenHit() || player1Squares[5][7].getHasBeenHit()) {
            hasHit = true;
        }

        assertEquals(true, hasHit);
    }

    @Test
    public void hitRowOrColumnWorks() {
        computer.setPreviousHitCoordinates(5, 5);

        computer.hitRowOrColumn();
        assertEquals(true, player1Squares[5][4].getHasBeenHit());

        computer.hitRowOrColumn();
        assertEquals(true, player1Squares[4][5].getHasBeenHit());

        computer.hitRowOrColumn();
        assertEquals(true, player1Squares[5][6].getHasBeenHit());

        computer.hitRowOrColumn();
        assertEquals(true, player1Squares[6][5].getHasBeenHit());
    }

    @Test
    public void setAndGetPrevHitCoordinatesWorks() {
        computer.setPreviousHitCoordinates(3, 5);
        int[] coordinates = computer.getPreviousHitCoordinates();
        assertEquals(3, coordinates[0]);
        assertEquals(5, coordinates[1]);
    }
}
