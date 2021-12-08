package com.battleship.domain;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square {
    private boolean isHit;
    private Ship ship = null;
    private Rectangle button;

    public Square() {
        this.isHit = false;

        Rectangle rectangle = new Rectangle(30, 30);
        rectangle.setFill(Color.WHITESMOKE);
        rectangle.setStyle("-fx-stroke: dimgray; -fx-stroke-width: 1;");
        this.button = rectangle;
    }

    public void addShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return this.ship;
    }

    public boolean hasShip() {
        return this.ship == null ? false : true;
    }

    public boolean hitSquare() {
        this.isHit = true;
        if (this.ship == null) {
            this.setBlueButtonColor();
            return false;
        }
        this.ship.hit();
        if (this.ship.isDead()) {
            this.ship.setDeadShipColor();
        } else {
            this.setBlackButtonColor();
        }
        return true;
    }

    public boolean getIsHit() {
        return this.isHit;
    }

    public void addButton(Rectangle button) {
        this.button = button;
    }

    public Rectangle getButton() {
        return this.button;
    }

    public void setBlackButtonColor() {
        this.button.setFill(Color.BLACK);
    }

    public void setGreyButtonColor() {
        this.button.setFill(Color.GREY);
    }

    public void setBlueButtonColor() {
        this.button.setFill(Color.BLUE);
    }

    public void removeButtonColor() {
        this.button.setFill(Color.WHITESMOKE);
    }
}
