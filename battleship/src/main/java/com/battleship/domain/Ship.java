package com.battleship.domain;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

public class Ship {
    private int size;
    private int squaresLeft;
    private ArrayList<Rectangle> buttons;

    public Ship(int size) {
        this.size = size;
        this.squaresLeft = size;
        this.buttons = new ArrayList<Rectangle>();
    }

    public boolean isDead() {
        return this.squaresLeft == 0;
    }

    public int getSquaresLeft() {
        return this.squaresLeft;
    }

    public void addButton(Rectangle button) {
        this.buttons.add(button);
    }

    public ArrayList<Rectangle> getButtons() {
        return this.buttons;
    }

    public void setButtonsDisabled() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setDisable(true);
        }
    }

    public void hit() {
        this.squaresLeft--;
    }

    public int getSize() {
        return this.size;
    }
}
