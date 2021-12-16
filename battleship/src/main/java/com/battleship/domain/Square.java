package com.battleship.domain;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Square that is part of a ship
 */
public class Square {
    private boolean hasBeenHit;
    private Ship ship = null;
    private Rectangle rectangle;

    public Square() {
        this.hasBeenHit = false;

        Rectangle rectangle = new Rectangle(30, 30);
        rectangle.setFill(Color.WHITESMOKE);
        rectangle.setStyle("-fx-stroke: dimgray; -fx-stroke-width: 1;");
        this.rectangle = rectangle;
    }

    public void addShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return this.ship;
    }

    /**
     * returns true if Square has a ship
     */
    public boolean hasShip() {
        return this.ship == null ? false : true;
    }

    /**
     * Hits square. If square does not contain ship it sets square color to blue. If
     * Square contains ship it sets ship color to red.
     * If ship is dead after the hit color is set to black
     */
    public boolean hitSquare() {
        this.hasBeenHit = true;
        if (this.ship == null) {
            this.setMissBackground();
            return false;
        }
        this.ship.hit();
        if (this.ship.isDead()) {
            this.ship.setShipImage();
        } else {
            this.setHitBackground();
        }
        return true;
    }

    public boolean getHasBeenHit() {
        return this.hasBeenHit;
    }

    public void addRectangle(Rectangle button) {
        this.rectangle = button;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    /**
     * Sets background image
     */
    public void setHitBackground() {
        Image image = new Image("file:images/hit.png");
        this.rectangle.setFill(new ImagePattern(image));
    }

    /**
     * Sets grey button color
     */
    public void setGreyButtonColor() {
        this.rectangle.setFill(Color.GREY);
    }

    /**
     * Sets background image
     */
    public void setMissBackground() {
        Image image = new Image("file:images/miss.png");
        this.rectangle.setFill(new ImagePattern(image));
    }

    /**
     * Removes button image
     */
    public void removeButtonImage() {
        this.rectangle.setFill(Color.WHITESMOKE);
        this.rectangle.setStyle("-fx-stroke: dimgray; -fx-stroke-width: 1;");
    }
}
