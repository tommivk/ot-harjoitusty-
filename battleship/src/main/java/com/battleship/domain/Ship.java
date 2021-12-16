package com.battleship.domain;

import java.util.ArrayList;

import com.battleship.enums.ShipDirection;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Ship that can placed on the board
 */
public class Ship {
    private int size;
    private int squaresLeft;
    private ArrayList<Rectangle> rectangles;
    private ShipDirection shipDirection;

    public Ship(int size) {
        this.size = size;
        this.squaresLeft = size;
        this.rectangles = new ArrayList<Rectangle>();
    }

    /**
     * Checks if Ship is dead (all the squares of the ship has been hit)
     * 
     * @return returns true if dead false if it's not
     */
    public boolean isDead() {
        return this.squaresLeft == 0;
    }

    /**
     * Returns how many unhit squares the ship has
     */
    public int getSquaresLeft() {
        return this.squaresLeft;
    }

    public void addRectangle(Rectangle rectangle) {
        this.rectangles.add(rectangle);
    }

    public void setShipDirection(ShipDirection direction) {
        this.shipDirection = direction;
    }

    /**
     * Returns all the rectangles connected to Ship
     */
    public ArrayList<Rectangle> getRectangles() {
        return this.rectangles;
    }

    /**
     * Sets ship image to rectangles
     */
    public void setShipImage() {
        Image head = new Image("file:images/ship-head.png");
        Image middle = new Image("file:images/ship-middle.png");
        Image end = new Image("file:images/ship-end.png");

        if (this.shipDirection == ShipDirection.VERTICAL) {
            this.rectangles.get(this.rectangles.size() - 1).setFill(new ImagePattern(head));
            this.rectangles.get(0).setFill(new ImagePattern(end));
        } else {

            this.rectangles.get(this.rectangles.size() - 1).setFill(new ImagePattern(end));
            this.rectangles.get(this.rectangles.size() - 1).setStyle("-fx-rotate: 90");
            this.rectangles.get(0).setFill(new ImagePattern(head));
            this.rectangles.get(0).setStyle("-fx-rotate: 90");
        }
        for (int i = 1; i < rectangles.size() - 1; i++) {
            rectangles.get(i).setFill(new ImagePattern(middle));
            if (this.shipDirection == ShipDirection.HORIZONTAL) {
                rectangles.get(i).setStyle("-fx-rotate: 90");
            }
        }

    }

    /**
     * Hits ship and reduces it's alive squares by one
     */
    public void hit() {
        this.squaresLeft--;
    }

    /**
     * Returns ships size
     */
    public int getSize() {
        return this.size;
    }
}
