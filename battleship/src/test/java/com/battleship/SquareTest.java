package com.battleship;

import static org.junit.Assert.assertEquals;

import com.battleship.domain.Ship;
import com.battleship.domain.Square;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import de.saxsys.javafx.test.JfxRunner;

@RunWith(JfxRunner.class)
public class SquareTest {
    Square square;
    Ship ship;

    @Before
    public void setup() {
        square = new Square();
        ship = new Ship(5);
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
    public void settingHitBackgroundImageWorks() {
        square.setHitBackground();
        Image image = new Image("file:images/hit.png");
        Rectangle button = square.getButton();
        Image buttonImage = ((ImagePattern) button.getFill()).getImage();

        PixelReader ogReader = image.getPixelReader();
        PixelReader reader = buttonImage.getPixelReader();

        assertEquals(ogReader.getColor(10, 10), reader.getColor(10, 10));
        assertEquals(ogReader.getColor(0, 0), reader.getColor(0, 0));
        assertEquals(ogReader.getColor(15, 6), reader.getColor(15, 6));
        assertEquals(ogReader.getColor(25, 15), reader.getColor(25, 15));
    }

    @Test
    public void removingButtonImageWorks() {
        square.setHitBackground();
        square.removeButtonImage();
        Rectangle button = square.getButton();
        assertEquals(Color.WHITESMOKE, button.getFill());
    }

}
