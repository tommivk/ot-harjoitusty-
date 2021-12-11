package com.battleship;

import static org.junit.Assert.assertEquals;

import com.battleship.domain.Ship;
import com.battleship.domain.Square;

import org.junit.Before;
import org.junit.Test;

public class ShipTest {
    Square square;
    Ship ship;

    @Before
    public void setup() {
        square = new Square();
        ship = new Ship(5);
    }

    @Test
    public void shipGetSizeWorks() {
        assertEquals(5, ship.getSize());
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
}
