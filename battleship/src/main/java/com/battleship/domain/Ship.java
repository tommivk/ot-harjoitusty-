package com.battleship.domain;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Ship that can placed on the board
 */
public class Ship {
    private int size;
    private int squaresLeft;
    private ArrayList<Rectangle> buttons;

    public Ship(int size) {
        this.size = size;
        this.squaresLeft = size;
        this.buttons = new ArrayList<Rectangle>();
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

    public void addButton(Rectangle button) {
        this.buttons.add(button);
    }

    /**
     * Returns all the rectangles connected to Ship
     */
    public ArrayList<Rectangle> getButtons() {
        return this.buttons;
    }

    /**
     * Sets ships rectangles color to red
     */
    public void setDeadShipColor() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setFill(Color.RED);
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
