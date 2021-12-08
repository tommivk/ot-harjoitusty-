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
        computer.setPrevHit(5, 6);
        player1Squares[5][5].hitSquare();
        assertEquals(false, computer.canHitLeft());
        computer.setPrevHit(5, 5);
        assertEquals(true, computer.canHitLeft());
    }

    @Test
    public void canHitRightWorks() {
        computer.setPrevHit(5, 6);
        player1Squares[5][7].hitSquare();
        assertEquals(false, computer.canHitRight());
        computer.setPrevHit(5, 5);
        assertEquals(true, computer.canHitRight());
    }

    @Test
    public void canHitTopWorks() {
        computer.setPrevHit(5, 5);
        player1Squares[4][5].hitSquare();
        assertEquals(false, computer.canHitTop());
        computer.setPrevHit(3, 5);
        assertEquals(true, computer.canHitTop());
    }

    @Test
    public void canHitBottomWorks() {
        computer.setPrevHit(5, 5);
        player1Squares[6][5].hitSquare();
        assertEquals(false, computer.canHitBottom());
        computer.setPrevHit(2, 5);
        assertEquals(true, computer.canHitBottom());
    }

    @Test
    public void hitLeftWorks() {
        computer.setPrevHit(5, 5);
        computer.hitLeft();
        assertEquals(true, player1Squares[5][4].getIsHit());
    }

    @Test
    public void hitRightWorks() {
        computer.setPrevHit(5, 5);
        computer.hitRight();
        assertEquals(true, player1Squares[5][6].getIsHit());
    }

    @Test
    public void hitBottomWorks() {
        computer.setPrevHit(5, 5);
        computer.hitBottom();
        assertEquals(true, player1Squares[6][5].getIsHit());
    }

    @Test
    public void hitTopWorks() {
        computer.setPrevHit(5, 5);
        computer.hitTop();
        assertEquals(true, player1Squares[4][5].getIsHit());
    }

}
