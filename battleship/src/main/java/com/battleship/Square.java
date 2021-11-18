package com.battleship;

public class Square {
    private boolean isHit;
    private Ship ship = null;

    public Square() {
        this.isHit = false;
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
}
