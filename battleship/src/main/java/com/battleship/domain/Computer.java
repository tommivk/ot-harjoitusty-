package com.battleship.domain;

import java.util.Random;

import com.battleship.enums.ShipDirection;
import com.battleship.enums.Player;

/**
 * AI opponent
 */
public class Computer {
    private int[] previousHitCoordinates;
    private ShipDirection previousHitDirection;
    private int previousHits;
    private Game game;

    public Computer(Game game) {
        this.previousHitDirection = ShipDirection.VERTICAL;
        this.previousHits = 0;
        this.previousHitCoordinates = new int[2];
        this.game = game;
    }

    /**
     * Places computer players ships to random locations
     */
    public void placeComputerShips() {
        Random random = new Random();

        while (true) {
            if (this.game.playerTwoShipsIsEmpty()) {
                break;
            }
            Ship ship = this.game.peekNextPlayerTwoShip();
            if (random.nextInt(2) == 0) {
                this.game.changeShipDirection();
            }
            int row = random.nextInt(10);
            int column = random.nextInt(10);

            if (this.game.canPlaceShip(ship, row, column, this.game.getPlayerTwoSquares())) {
                this.game.placeShip(row, column, Player.PLAYER2);
            }
        }
    }

    /**
     * Stores the coodinates of previous shot that hit a ship
     */
    public void setPreviousHitCoordinates(int row, int column) {
        this.previousHitCoordinates[0] = row;
        this.previousHitCoordinates[1] = column;
    }

    public void addPreviousHit() {
        this.previousHits++;
    }

    public void setPreviousHits(int num) {
        this.previousHits = num;
    }

    /**
     * returns how many continuous hits computer has
     */
    public int getPreviousHits() {
        return this.previousHits;
    }

    /**
     * gets the coodinates of previous shot that hit a ship
     */
    public int[] getPreviousHitCoordinates() {
        return this.previousHitCoordinates;
    }

    /**
     * Stores computers hit and shot count to the database
     */
    private void storeHit(boolean hit, GameService gameService) {
        if (hit) {
            gameService.addPlayerTwoHit();
        }
        gameService.addPlayerTwoShot();
    }

    /**
     * Starts while loop where computer tries to hit squares
     */
    public void computersTurn(GameService gameService) {
        while (true) {
            boolean hit = hitAvailableSquare();
            this.storeHit(hit, gameService);
            if (!hit) {
                this.game.setTurn(Player.PLAYER1);
                break;
            }
        }
    }

    /**
     * Hits random square if previous hits is 0. If previous hits is 1 it tries to
     * hit squares around the previous hit coordinates. If previous hits is > 1
     * it tries to hit squares depending on what the last two hits direction was
     */
    public boolean hitAvailableSquare() {
        if (previousHits == 1) {
            return this.hitRowOrColumn();
        }
        if (previousHits > 1) {
            boolean hit = this.previousHitDirection == ShipDirection.HORIZONTAL ? this.hitColumn()
                    : this.hitRow();
            return hit;
        }
        return this.hitRandom();
    }

    /**
     * Tries to hit squares on the row of the previous hits
     */
    public boolean hitRow() {
        if (this.canHitTop()) {
            boolean hit = this.hitTop();
            return hit;
        }
        if (this.canHitBottom()) {
            boolean hit = this.hitBottom();
            return hit;
        }
        if (this.canHitRowEndBottom()) {
            boolean hit = hitRowEndBottom();
            return hit;
        }
        return hitRowEndTop();
    }

    /**
     * Tries to hit squares on the column of the previous hits
     */
    public boolean hitColumn() {
        if (this.canHitLeft()) {
            boolean hit = this.hitLeft();
            return hit;
        }
        if (this.canHitRight()) {
            boolean hit = this.hitRight();
            return hit;
        }
        if (this.canHitColumnEndLeft()) {
            boolean hit = this.hitColumnEndLeft();
            return hit;
        }
        return this.hitColumnEndRight();
    }

    /**
     * Checks if squares arounds previous hit square are hittable
     */
    public boolean hitRowOrColumn() {
        if (this.canHitLeft()) {
            return this.hitLeft();
        }

        if (this.canHitTop()) {
            return this.hitTop();
        }

        if (this.canHitRight()) {
            return this.hitRight();
        }

        if (this.canHitBottom()) {
            return this.hitBottom();
        }
        return this.hitRandom();
    }

    /**
     * Checks if square from the left of previous hit is hittable;
     */
    public boolean canHitLeft() {
        Square[][] squares = this.game.getPlayerOneSquares();
        if ((this.previousHitCoordinates[1] - 1) >= 0
                && !squares[this.previousHitCoordinates[0]][this.previousHitCoordinates[1] - 1].getHasBeenHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if square from the right of previous hit is hittable;
     */
    public boolean canHitRight() {
        Square[][] squares = this.game.getPlayerOneSquares();
        if ((this.previousHitCoordinates[1] + 1) < 10
                && !squares[this.previousHitCoordinates[0]][this.previousHitCoordinates[1] + 1].getHasBeenHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if square from the top of previous hit is hittable;
     */
    public boolean canHitTop() {
        Square[][] squares = this.game.getPlayerOneSquares();

        if (this.previousHitCoordinates[0] - 1 >= 0
                && !squares[this.previousHitCoordinates[0] - 1][this.previousHitCoordinates[1]].getHasBeenHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if square from the below of previous hit is hittable;
     */
    public boolean canHitBottom() {
        Square[][] squares = this.game.getPlayerOneSquares();

        if (this.previousHitCoordinates[0] + 1 < 10
                && !squares[this.previousHitCoordinates[0] + 1][this.previousHitCoordinates[1]].getHasBeenHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if either square from the top or bottom of previous hit are hittable;
     */
    public boolean canHitRow() {
        if (this.previousHitCoordinates[0] + 1 < 10
                && !this.game.getPlayerOneSquares()[this.previousHitCoordinates[0] + 1][this.previousHitCoordinates[1]]
                        .getHasBeenHit()
                || this.previousHitCoordinates[0] - 1 >= 0
                        && !this.game.getPlayerOneSquares()[this.previousHitCoordinates[0]
                                - 1][this.previousHitCoordinates[1]]
                                        .getHasBeenHit()) {
            return true;
        }
        return false;
    }

    /**
     * Hits the square from the left of the previous hit
     */
    public boolean hitLeft() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row][col - 1].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row][col - 1].getShip().isDead()) {
            this.previousHits = 0;
            return true;
        }
        if (hit) {
            this.previousHits++;
            this.previousHitCoordinates[1] = col - 1;
            this.previousHitDirection = ShipDirection.HORIZONTAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the square from the right of the previous hit
     */
    public boolean hitRight() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row][col + 1].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row][col + 1].getShip().isDead()) {
            this.previousHits = 0;
            return true;
        }
        if (hit) {
            this.previousHits++;
            this.previousHitCoordinates[1] = col + 1;
            this.previousHitDirection = ShipDirection.HORIZONTAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the square from the top of the previous hit
     */
    public boolean hitTop() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row - 1][col].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row - 1][col].getShip().isDead()) {
            this.previousHits = 0;
            return true;
        }
        if (hit) {
            this.previousHits++;
            this.previousHitCoordinates[0] = row - 1;
            this.previousHitDirection = ShipDirection.VERTICAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the square from the bottom of the previous hit
     */
    public boolean hitBottom() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];
        boolean hit = this.game.getPlayerOneSquares()[row + 1][col].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row + 1][col].getShip().isDead()) {
            this.previousHits = 0;
            return true;
        }
        if (hit) {
            this.previousHits++;
            this.previousHitCoordinates[0] = row + 1;
            this.previousHitDirection = ShipDirection.VERTICAL;
            return true;
        }
        return false;
    }

    /**
     * Checks if either square from the left or right of previous hit are hittable;
     */
    public boolean canHitColumnEndRight() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];
        if ((col + this.previousHits) < 10
                && !this.game.getPlayerOneSquares()[row][col + this.previousHits].getHasBeenHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if column from index - prevHits is hittable
     */
    public boolean canHitColumnEndLeft() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];
        if ((col - this.previousHits) >= 0
                && !this.game.getPlayerOneSquares()[row][col - this.previousHits].getHasBeenHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if row from index - prevHits is hittable
     */
    public boolean canHitRowEndTop() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];
        if ((row - this.previousHits) >= 0
                && !this.game.getPlayerOneSquares()[row - previousHits][col].getHasBeenHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if row from index + prevHits is hittable
     */
    public boolean canHitRowEndBottom() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];
        if ((row + this.previousHits) < 10
                && !this.game.getPlayerOneSquares()[row + this.previousHits][col].getHasBeenHit()) {
            return true;
        }
        return false;
    }

    /**
     * Hits the Square in row index + prevHits
     */
    public boolean hitRowEndBottom() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row + previousHits][col].hitSquare();
        if (hit && this.game.getPlayerOneSquares()[row + previousHits][col].getShip().isDead()) {
            this.previousHits = 0;
            return true;
        }
        if (hit) {
            this.previousHitCoordinates[0] = row + previousHits;
            this.previousHits++;
            this.previousHitDirection = ShipDirection.VERTICAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the Square in row index - prevHits
     */
    public boolean hitRowEndTop() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row - previousHits][col].hitSquare();
        if (hit && this.game.getPlayerOneSquares()[row - previousHits][col].getShip().isDead()) {
            this.previousHits = 0;
            return true;
        }
        if (hit) {
            this.previousHitCoordinates[0] = row - previousHits;
            this.previousHits++;
            this.previousHitDirection = ShipDirection.VERTICAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the Square in column index + prevHits
     */
    public boolean hitColumnEndRight() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row][col + this.previousHits].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row][col + this.previousHits].getShip().isDead()) {
            this.previousHits = 0;
            return true;
        }
        if (hit) {
            this.previousHitCoordinates[1] = col + this.previousHits;
            this.previousHits++;
            this.previousHitDirection = ShipDirection.HORIZONTAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the Square in column index - prevHits
     */
    public boolean hitColumnEndLeft() {
        int row = previousHitCoordinates[0];
        int col = previousHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row][col - this.previousHits].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row][col - this.previousHits].getShip().isDead()) {
            this.previousHits = 0;
            return true;
        }
        if (hit) {
            this.previousHitCoordinates[1] = col - this.previousHits;
            this.previousHits++;
            this.previousHitDirection = ShipDirection.HORIZONTAL;
            return true;
        } else {
            return false;
        }

    }

    /**
     * Uses brute force to hit a random square
     */
    public boolean hitRandom() {
        Random random = new Random();
        Square[][] squares = this.game.getPlayerOneSquares();
        while (true) {
            int row = random.nextInt(10);
            int column = random.nextInt(10);
            Square square = squares[row][column];
            if (square.getHasBeenHit()) {
                continue;
            }
            boolean isHit = hitSquare(row, column);
            return isHit;
        }
    }

    /**
     * Hits player one's square
     * 
     * @param row,column coordinates of the square that are going to be hit
     */
    public boolean hitSquare(int row, int column) {
        Square square = this.game.getPlayerOneSquares()[row][column];
        boolean hit = square.hitSquare();
        if (!hit) {
            return false;
        }
        if (square.getShip().isDead()) {
            this.previousHits = 0;
        } else {
            this.previousHits++;
            this.previousHitCoordinates[0] = row;
            this.previousHitCoordinates[1] = column;
        }
        return true;
    }
}
