package com.battleship.domain;

import javafx.scene.control.Button;

public class Square {
    private boolean isHit;
    private Ship ship = null;
    private Button button;

    public Square() {
        this.isHit = false;
        this.button = new Button();
        this.button.setMinSize(30.0, 30.0);
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
            return false;
        }
        this.ship.hit();
        return true;
    }

    public boolean getIsHit() {
        return this.isHit;
    }

    public void addButton(Button button) {
        this.button = button;
    }

    public Button getButton() {
        return this.button;
    }

    public void setBlackButtonColor() {
        this.button.setStyle("-fx-background-color: black");
    }

    public void setGreyButtonColor() {
        this.button.setStyle("-fx-background-color: grey");
    }

    public void removeButtonColor() {
        this.button.setStyle(null);
    }
}
