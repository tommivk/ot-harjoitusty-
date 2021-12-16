package com.battleship;

import static org.junit.Assert.assertEquals;

import com.battleship.domain.Game;
import com.battleship.domain.GameService;
import com.battleship.domain.Ship;
import com.battleship.domain.ShipDirection;
import com.battleship.domain.Square;
import com.battleship.domain.Turn;
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
        computer.setPrevHitCoordinates(5, 6);
        player1Squares[5][5].hitSquare();
        assertEquals(false, computer.canHitLeft());
        computer.setPrevHitCoordinates(5, 5);
        assertEquals(true, computer.canHitLeft());
    }

    @Test
    public void canHitRightWorks() {
        computer.setPrevHitCoordinates(5, 6);
        player1Squares[5][7].hitSquare();
        assertEquals(false, computer.canHitRight());
        computer.setPrevHitCoordinates(5, 5);
        assertEquals(true, computer.canHitRight());
    }

    @Test
    public void canHitTopWorks() {
        computer.setPrevHitCoordinates(5, 5);
        player1Squares[4][5].hitSquare();
        assertEquals(false, computer.canHitTop());
        computer.setPrevHitCoordinates(3, 5);
        assertEquals(true, computer.canHitTop());
    }

    @Test
    public void canHitBottomWorks() {
        computer.setPrevHitCoordinates(5, 5);
        player1Squares[6][5].hitSquare();
        assertEquals(false, computer.canHitBottom());
        computer.setPrevHitCoordinates(2, 5);
        assertEquals(true, computer.canHitBottom());
    }

    @Test
    public void hitLeftWorks() {
        computer.setPrevHitCoordinates(5, 5);
        computer.hitLeft();
        assertEquals(true, player1Squares[5][4].getIsHit());
    }

    @Test
    public void hitLeftReturnsTrueAndResetsPrevHitsWhenShipDies() {
        game.changeShipDirection();
        game.placeShip(5, 5, 1);
        player1Squares[5][6].hitSquare();
        player1Squares[5][7].hitSquare();
        player1Squares[5][8].hitSquare();
        player1Squares[5][9].hitSquare();

        computer.setPrevHitCoordinates(5, 6);
        boolean res = computer.hitLeft();
        assertEquals(true, res);
        assertEquals(0, computer.getPrevHits());
    }

    @Test
    public void hitLeftReturnsTrueAndAddsPrevHitsWhenHit() {
        game.changeShipDirection();
        game.placeShip(5, 5, 1);
        computer.setPrevHitCoordinates(5, 6);
        boolean res = computer.hitLeft();
        assertEquals(true, res);
        assertEquals(1, computer.getPrevHits());
    }

    @Test
    public void hitRightWorks() {
        computer.setPrevHitCoordinates(5, 5);
        computer.hitRight();
        assertEquals(true, player1Squares[5][6].getIsHit());
    }

    @Test
    public void hitRightReturnsTrueAndResetsPrevHitsWhenShipDies() {
        game.changeShipDirection();
        game.placeShip(5, 5, 1);
        player1Squares[5][5].hitSquare();
        player1Squares[5][6].hitSquare();
        player1Squares[5][7].hitSquare();
        player1Squares[5][8].hitSquare();

        computer.setPrevHitCoordinates(5, 8);
        boolean res = computer.hitRight();
        assertEquals(true, res);
        assertEquals(0, computer.getPrevHits());
    }

    @Test
    public void hitRightReturnsTrueAndAddsPrevHitsWhenHit() {
        game.changeShipDirection();
        game.placeShip(5, 5, 1);
        computer.setPrevHitCoordinates(5, 5);
        boolean res = computer.hitRight();
        assertEquals(true, res);
        assertEquals(1, computer.getPrevHits());
    }

    @Test
    public void hitBottomWorks() {
        computer.setPrevHitCoordinates(5, 5);
        computer.hitBottom();
        assertEquals(true, player1Squares[6][5].getIsHit());
    }

    @Test
    public void hitBottomReturnsTrueAndAddsPrevHitsWhenHit() {
        game.placeShip(5, 5, 1);
        computer.setPrevHitCoordinates(5, 5);
        boolean res = computer.hitBottom();
        assertEquals(true, res);
        assertEquals(1, computer.getPrevHits());
    }

    @Test
    public void hitBottomReturnsTrueAndResetsPrevHitsWhenShipDies() {
        game.placeShip(5, 5, 1);
        player1Squares[5][5].hitSquare();
        player1Squares[6][5].hitSquare();
        player1Squares[7][5].hitSquare();
        player1Squares[8][5].hitSquare();

        computer.setPrevHitCoordinates(8, 5);
        boolean res = computer.hitBottom();
        assertEquals(true, res);
        assertEquals(0, computer.getPrevHits());
    }

    @Test
    public void hitTopWorks() {
        computer.setPrevHitCoordinates(5, 5);
        computer.hitTop();
        assertEquals(true, player1Squares[4][5].getIsHit());
    }

    @Test
    public void hitTopReturnsTrueAndAddsPrevHitsWhenHit() {
        game.placeShip(5, 5, 1);
        computer.setPrevHitCoordinates(6, 5);
        boolean res = computer.hitTop();
        assertEquals(true, res);
        assertEquals(1, computer.getPrevHits());
    }

    @Test
    public void hitTopReturnsTrueAndResetsPrevHitsWhenShipDies() {
        game.placeShip(5, 5, 1);
        player1Squares[6][5].hitSquare();
        player1Squares[7][5].hitSquare();
        player1Squares[8][5].hitSquare();
        player1Squares[9][5].hitSquare();

        computer.setPrevHitCoordinates(6, 5);
        boolean res = computer.hitTop();
        assertEquals(true, res);
        assertEquals(0, computer.getPrevHits());
    }

    @Test
    public void canHitColumnEndRightWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(0, 0);
        assertEquals(true, computer.canHitColumnEndRight());
        player1Squares[0][2].hitSquare();
        assertEquals(false, computer.canHitColumnEndRight());
    }

    @Test
    public void canHitColumnEndLeftWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(0, 5);
        assertEquals(true, computer.canHitColumnEndLeft());
        player1Squares[0][3].hitSquare();
        assertEquals(false, computer.canHitColumnEndLeft());
    }

    @Test
    public void canHitRowEndTopWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(5, 5);
        assertEquals(true, computer.canHitRowEndTop());
        player1Squares[3][5].hitSquare();
        assertEquals(false, computer.canHitRowEndTop());
    }

    @Test
    public void canHitRowEndBottomWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(5, 5);
        assertEquals(true, computer.canHitRowEndBottom());
        player1Squares[7][5].hitSquare();
        assertEquals(false, computer.canHitRowEndBottom());
    }

    @Test
    public void hitColumnEndRightWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(0, 0);
        computer.hitColumnEndRight();
        assertEquals(true, player1Squares[0][2].getIsHit());

        game.changeShipDirection();
        assertEquals(ShipDirection.HORIZONTAL, game.getShipDirection());

        game.placeShip(1, 1, 1);
        player1Squares[1][1].hitSquare();
        player1Squares[1][2].hitSquare();
        player1Squares[1][3].hitSquare();
        player1Squares[1][4].hitSquare();

        computer.setPrevHitCoordinates(1, 1);

        computer.setPrevHits(4);
        computer.hitColumnEndRight();
        assertEquals(true, player1Squares[1][5].getShip().isDead());
    }

    @Test
    public void hitColumnEndLeftWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(0, 5);
        computer.hitColumnEndLeft();
        assertEquals(true, player1Squares[0][2].getIsHit());

        game.changeShipDirection();
        assertEquals(ShipDirection.HORIZONTAL, game.getShipDirection());
        game.placeShip(1, 1, 1);
        player1Squares[1][2].hitSquare();
        player1Squares[1][3].hitSquare();
        player1Squares[1][4].hitSquare();
        player1Squares[1][5].hitSquare();

        computer.setPrevHitCoordinates(1, 5);

        computer.setPrevHits(4);
        computer.hitColumnEndLeft();
        assertEquals(true, player1Squares[1][1].getShip().isDead());
    }

    @Test
    public void hitRowEndTopWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(5, 5);
        computer.hitRowEndTop();
        assertEquals(true, player1Squares[3][5].getIsHit());

        game.placeShip(1, 6, 1);
        player1Squares[2][6].hitSquare();
        player1Squares[3][6].hitSquare();
        player1Squares[4][6].hitSquare();
        player1Squares[5][6].hitSquare();

        computer.setPrevHitCoordinates(5, 6);

        computer.setPrevHits(4);
        computer.hitRowEndTop();
        assertEquals(true, player1Squares[1][6].getShip().isDead());
    }

    @Test
    public void hitRowEndBottomWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(2, 5);
        computer.hitRowEndBottom();
        assertEquals(true, player1Squares[4][5].getIsHit());

        game.placeShip(1, 6, 1);
        player1Squares[4][6].hitSquare();
        player1Squares[3][6].hitSquare();
        player1Squares[2][6].hitSquare();
        player1Squares[1][6].hitSquare();

        computer.setPrevHitCoordinates(1, 6);

        computer.setPrevHits(4);
        computer.hitRowEndBottom();
        assertEquals(true, player1Squares[5][6].getShip().isDead());

    }

    @Test
    public void computerHitRandomWorks() {
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                this.player1Squares[i][k].addShip(new Ship(5));
            }
        }

        for (int i = 0; i < 100; i++) {
            computer.computerHitRandom();
        }

        boolean allHit = true;
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                if (!player1Squares[i][k].getIsHit()) {
                    allHit = false;
                }
            }
        }
        assertEquals(true, allHit);
    }

    @Test
    public void computerHitRandomReturnsFalseWhenNoHit() {
        boolean res = computer.computerHitRandom();
        assertEquals(false, res);
    }

    @Test
    public void turnChangesAfterComputersTurn() {
        DBGameDao dbGameDao = new DBGameDao();
        GameService gameService = new GameService(dbGameDao, "");
        game.setTurn(Turn.PLAYER2);
        computer.computersTurn(gameService);
        assertEquals(Turn.PLAYER1, game.getTurn());
    }

    @Test
    public void canHitRowWorks() {
        computer.setPrevHitCoordinates(5, 5);
        assertEquals(true, computer.canHitRow());
        player1Squares[4][5].hitSquare();
        assertEquals(true, computer.canHitRow());
        player1Squares[6][5].hitSquare();
        assertEquals(false, computer.canHitRow());
    }

    @Test
    public void hitRowWorks() {
        computer.setPrevHitCoordinates(5, 5);
        computer.hitRow();
        boolean rowHit = false;
        if (player1Squares[6][5].getIsHit() || player1Squares[4][5].getIsHit()) {
            rowHit = true;
        }
        assertEquals(true, rowHit);

        player1Squares[6][5].hitSquare();
        player1Squares[4][5].hitSquare();
        computer.addPrevHit();
        computer.addPrevHit();
        computer.hitRow();

        boolean hasHit = false;
        if (player1Squares[7][5].getIsHit() || player1Squares[3][5].getIsHit()) {
            hasHit = true;
        }

        assertEquals(true, hasHit);

    }

    @Test
    public void hitColumnWorks() {
        computer.setPrevHitCoordinates(5, 5);
        computer.hitColumn();
        boolean rowHit = false;
        if (player1Squares[5][6].getIsHit() || player1Squares[5][4].getIsHit()) {
            rowHit = true;
        }
        assertEquals(true, rowHit);

        player1Squares[5][6].hitSquare();
        player1Squares[5][4].hitSquare();
        computer.addPrevHit();
        computer.addPrevHit();
        computer.hitColumn();

        boolean hasHit = false;
        if (player1Squares[5][3].getIsHit() || player1Squares[5][7].getIsHit()) {
            hasHit = true;
        }

        assertEquals(true, hasHit);
    }

    @Test
    public void hitRowOrColumnWorks() {
        computer.setPrevHitCoordinates(5, 5);

        computer.computerHitRowOrColumn();
        assertEquals(true, player1Squares[5][4].getIsHit());

        computer.computerHitRowOrColumn();
        assertEquals(true, player1Squares[4][5].getIsHit());

        computer.computerHitRowOrColumn();
        assertEquals(true, player1Squares[5][6].getIsHit());

        computer.computerHitRowOrColumn();
        assertEquals(true, player1Squares[6][5].getIsHit());
    }

    @Test
    public void setAndGetPrevHitCoordinatesWorks() {
        computer.setPrevHitCoordinates(3, 5);
        int[] coordinates = computer.getPrevHitCoordinates();
        assertEquals(3, coordinates[0]);
        assertEquals(5, coordinates[1]);
    }
}
