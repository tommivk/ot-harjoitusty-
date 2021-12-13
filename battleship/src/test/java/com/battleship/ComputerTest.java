package com.battleship;

import static org.junit.Assert.assertEquals;

import com.battleship.domain.Game;
import com.battleship.domain.Ship;
import com.battleship.domain.Square;
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
        player1Squares = game.getPlayer1Squares();
    }

    @Test
    public void placeComputerShipsPlacesSixUniqueShips() {
        game.getComputer().placeComputerShips();
        Square[][] squares = game.getPlayer2Squares();
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
    public void hitRightWorks() {
        computer.setPrevHitCoordinates(5, 5);
        computer.hitRight();
        assertEquals(true, player1Squares[5][6].getIsHit());
    }

    @Test
    public void hitBottomWorks() {
        computer.setPrevHitCoordinates(5, 5);
        computer.hitBottom();
        assertEquals(true, player1Squares[6][5].getIsHit());
    }

    @Test
    public void hitTopWorks() {
        computer.setPrevHitCoordinates(5, 5);
        computer.hitTop();
        assertEquals(true, player1Squares[4][5].getIsHit());
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
    }

    @Test
    public void hitColumnEndLeftWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(0, 5);
        computer.hitColumnEndLeft();
        assertEquals(true, player1Squares[0][2].getIsHit());
    }

    @Test
    public void hitRowEndTopWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(5, 5);
        computer.hitRowEndTop();
        assertEquals(true, player1Squares[3][5].getIsHit());
    }

    @Test
    public void hitRowEndBottomWorks() {
        computer.addPrevHit();
        computer.addPrevHit();
        computer.setPrevHitCoordinates(2, 5);
        computer.hitRowEndBottom();
        assertEquals(true, player1Squares[4][5].getIsHit());
    }

}
