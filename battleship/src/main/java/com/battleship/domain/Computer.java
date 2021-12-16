package com.battleship.domain;

import java.util.Random;

/**
 * AI opponent
 */
public class Computer {
    private int[] prevHitCoordinates;
    private ShipDirection prevHitDir;
    private int prevHits;
    private Game game;

    public Computer(Game game) {
        this.prevHitDir = ShipDirection.VERTICAL;
        this.prevHits = 0;
        this.prevHitCoordinates = new int[2];
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
                this.game.placeShip(row, column, 2);
            }
        }
    }

    /**
     * Stores the coodinates of previous shot that hit a ship
     */
    public void setPrevHitCoordinates(int row, int column) {
        this.prevHitCoordinates[0] = row;
        this.prevHitCoordinates[1] = column;
    }

    public void addPrevHit() {
        this.prevHits++;
    }

    /**
     * returns how many continuous hits computer has
     */
    public int getPrevHits() {
        return this.prevHits;
    }

    /**
     * gets the coodinates of previous shot that hit a ship
     */
    public int[] getPrevHitCoordinates() {
        return this.prevHitCoordinates;
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
                this.game.setTurn(Turn.PLAYER1);
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
        if (prevHits == 1) {
            return this.computerHitRowOrColumn();
        }
        if (prevHits > 1) {
            boolean hit = this.prevHitDir == ShipDirection.HORIZONTAL ? this.hitColumn()
                    : this.hitRow();
            return hit;
        }
        return this.computerHitRandom();
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
    public boolean computerHitRowOrColumn() {
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
        return this.computerHitRandom();
    }

    /**
     * Checks if square from the left of previous hit is hittable;
     */
    public boolean canHitLeft() {
        Square[][] squares = this.game.getPlayerOneSquares();
        if ((this.prevHitCoordinates[1] - 1) >= 0
                && !squares[this.prevHitCoordinates[0]][this.prevHitCoordinates[1] - 1].getIsHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if square from the right of previous hit is hittable;
     */
    public boolean canHitRight() {
        Square[][] squares = this.game.getPlayerOneSquares();
        if ((this.prevHitCoordinates[1] + 1) < 10
                && !squares[this.prevHitCoordinates[0]][this.prevHitCoordinates[1] + 1].getIsHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if square from the top of previous hit is hittable;
     */
    public boolean canHitTop() {
        Square[][] squares = this.game.getPlayerOneSquares();

        if (this.prevHitCoordinates[0] - 1 >= 0
                && !squares[this.prevHitCoordinates[0] - 1][this.prevHitCoordinates[1]].getIsHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if square from the below of previous hit is hittable;
     */
    public boolean canHitBottom() {
        Square[][] squares = this.game.getPlayerOneSquares();

        if (this.prevHitCoordinates[0] + 1 < 10
                && !squares[this.prevHitCoordinates[0] + 1][this.prevHitCoordinates[1]].getIsHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if either square from the top or bottom of previous hit are hittable;
     */
    public boolean canHitRow() {
        if (this.prevHitCoordinates[0] + 1 < 10
                && !this.game.getPlayerOneSquares()[this.prevHitCoordinates[0] + 1][this.prevHitCoordinates[1]]
                        .getIsHit()
                || this.prevHitCoordinates[0] - 1 >= 0
                        && !this.game.getPlayerOneSquares()[this.prevHitCoordinates[0] - 1][this.prevHitCoordinates[1]]
                                .getIsHit()) {
            return true;
        }
        return false;
    }

    /**
     * Hits the square from the left of the previous hit
     */
    public boolean hitLeft() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row][col - 1].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row][col - 1].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHits++;
            this.prevHitCoordinates[1] = col - 1;
            this.prevHitDir = ShipDirection.HORIZONTAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the square from the right of the previous hit
     */
    public boolean hitRight() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row][col + 1].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row][col + 1].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHits++;
            this.prevHitCoordinates[1] = col + 1;
            this.prevHitDir = ShipDirection.HORIZONTAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the square from the top of the previous hit
     */
    public boolean hitTop() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row - 1][col].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row - 1][col].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHits++;
            this.prevHitCoordinates[0] = row - 1;
            this.prevHitDir = ShipDirection.VERTICAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the square from the bottom of the previous hit
     */
    public boolean hitBottom() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];
        boolean hit = this.game.getPlayerOneSquares()[row + 1][col].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row + 1][col].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHits++;
            this.prevHitCoordinates[0] = row + 1;
            this.prevHitDir = ShipDirection.VERTICAL;
            return true;
        }
        return false;
    }

    /**
     * Checks if either square from the left or right of previous hit are hittable;
     */
    public boolean canHitColumnEndRight() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];
        if ((col + this.prevHits) < 10 && !this.game.getPlayerOneSquares()[row][col + this.prevHits].getIsHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if column from index - prevHits is hittable
     */
    public boolean canHitColumnEndLeft() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];
        if ((col - this.prevHits) >= 0 && !this.game.getPlayerOneSquares()[row][col - this.prevHits].getIsHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if row from index - prevHits is hittable
     */
    public boolean canHitRowEndTop() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];
        if ((row - this.prevHits) >= 0 && !this.game.getPlayerOneSquares()[row - prevHits][col].getIsHit()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if row from index + prevHits is hittable
     */
    public boolean canHitRowEndBottom() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];
        if ((row + this.prevHits) < 10 && !this.game.getPlayerOneSquares()[row + this.prevHits][col].getIsHit()) {
            return true;
        }
        return false;
    }

    /**
     * Hits the Square in row index + prevHits
     */
    public boolean hitRowEndBottom() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row + prevHits][col].hitSquare();
        if (hit && this.game.getPlayerOneSquares()[row + prevHits][col].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHitCoordinates[0] = row + prevHits;
            this.prevHits++;
            this.prevHitDir = ShipDirection.VERTICAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the Square in row index - prevHits
     */
    public boolean hitRowEndTop() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row - prevHits][col].hitSquare();
        if (hit && this.game.getPlayerOneSquares()[row - prevHits][col].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHitCoordinates[0] = row - prevHits;
            this.prevHits++;
            this.prevHitDir = ShipDirection.VERTICAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the Square in column index + prevHits
     */
    public boolean hitColumnEndRight() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row][col + this.prevHits].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row][col + this.prevHits].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHitCoordinates[1] = col + this.prevHits;
            this.prevHits++;
            this.prevHitDir = ShipDirection.HORIZONTAL;
            return true;
        }
        return false;
    }

    /**
     * Hits the Square in column index - prevHits
     */
    public boolean hitColumnEndLeft() {
        int row = prevHitCoordinates[0];
        int col = prevHitCoordinates[1];

        boolean hit = this.game.getPlayerOneSquares()[row][col - this.prevHits].hitSquare();

        if (hit && this.game.getPlayerOneSquares()[row][col - this.prevHits].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHitCoordinates[1] = col - this.prevHits;
            this.prevHits++;
            this.prevHitDir = ShipDirection.HORIZONTAL;
            return true;
        } else {
            return false;
        }

    }

    /**
     * Uses brute force to hit a random square
     */
    public boolean computerHitRandom() {
        Random random = new Random();
        Square[][] squares = this.game.getPlayerOneSquares();
        while (true) {
            int row = random.nextInt(10);
            int column = random.nextInt(10);
            Square square = squares[row][column];
            if (square.getIsHit()) {
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
            this.prevHits = 0;
        } else {
            this.prevHits++;
            this.prevHitCoordinates[0] = row;
            this.prevHitCoordinates[1] = column;
        }
        return true;
    }
}
