package com.battleship.domain;

import java.util.Random;

public class Computer {
    private int[] prevHit;
    private ShipDirection prevHitDir;
    private int prevHits;
    private Game game;

    public Computer(Game game) {
        this.prevHitDir = ShipDirection.VERTICAL;
        this.prevHits = 0;
        this.prevHit = new int[2];
        this.game = game;
    }

    public void placeComputerShips() {
        Random random = new Random();

        while (true) {
            if (this.game.player2ShipsIsEmpty()) {
                break;
            }
            Ship ship = this.game.peekNextPlayer2Ship();
            if (random.nextInt(2) == 0) {
                this.game.changeShipDirection();
            }
            int row = random.nextInt(10);
            int column = random.nextInt(10);

            if (this.game.canPlaceShip(ship, row, column, this.game.getPlayer2Squares())) {
                this.game.placeShip(row, column, 2);
            }
        }
    }

    public void computersTurn() {
        while (true) {
            if (prevHits == 0) {
                boolean hit = this.computerHitRandom();
                if (!hit) {
                    break;
                }
            }

            if (prevHits == 1) {
                boolean hit = this.computerHitRowOrColumn();
                if (!hit) {
                    break;
                }
                continue;
            }

            if (prevHits > 1 && this.prevHitDir == ShipDirection.HORIZONTAL) {
                if (this.canHitLeft()) {
                    boolean hit = this.hitLeft();
                    if (!hit) {
                        break;
                    }
                    continue;
                }
                if (this.canHitRight()) {
                    boolean hit = this.hitRight();
                    if (!hit) {
                        break;
                    }
                    continue;
                }

                if (this.canHitColumnEndLeft()) {
                    boolean hit = this.hitColumnEndLeft();
                    if (!hit) {
                        break;
                    }
                    continue;
                }
                if (this.canHitColumnEndRight()) {
                    boolean hit = this.hitColumnEndRight();
                    if (!hit) {
                        break;
                    }
                    continue;
                }

            }
            if (this.prevHits > 1 && this.prevHitDir == ShipDirection.VERTICAL) {
                if (this.canHitTop()) {
                    boolean hit = this.hitTop();
                    if (!hit) {
                        break;
                    }
                    continue;
                }
                if (this.canHitBottom()) {
                    boolean hit = this.hitBottom();
                    if (!hit) {
                        break;
                    }
                    continue;
                }

                if (this.canHitRowEndBottom()) {
                    boolean hit = hitRowEndBottom();
                    if (!hit) {
                        break;
                    }
                    continue;
                }

                if (this.canHitRowEndTop()) {
                    boolean hit = hitRowEndTop();
                    if (!hit) {
                        break;
                    }
                    continue;
                }
            }

            boolean hit = this.computerHitRandom();
            if (!hit) {
                break;
            }

        }

    }

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

    public boolean canHitLeft() {
        Square[][] squares = this.game.getPlayer1Squares();
        if ((this.prevHit[1] - 1) >= 0
                && !squares[this.prevHit[0]][this.prevHit[1] - 1].getIsHit()) {
            return true;
        }
        return false;
    }

    public boolean canHitRight() {
        Square[][] squares = this.game.getPlayer1Squares();
        if ((this.prevHit[1] + 1) < 10
                && !squares[this.prevHit[0]][this.prevHit[1] + 1].getIsHit()) {
            return true;
        }
        return false;
    }

    public boolean canHitTop() {
        Square[][] squares = this.game.getPlayer1Squares();

        if (this.prevHit[0] - 1 >= 0
                && !squares[this.prevHit[0] - 1][this.prevHit[1]].getIsHit()) {
            return true;
        }
        return false;
    }

    public boolean canHitBottom() {
        Square[][] squares = this.game.getPlayer1Squares();

        if (this.prevHit[0] + 1 < 10
                && !squares[this.prevHit[0] + 1][this.prevHit[1]].getIsHit()) {
            return true;
        }
        return false;
    }

    public boolean canHitRow() {
        if (this.prevHit[0] + 1 < 10 && !this.game.getPlayer1Squares()[this.prevHit[0] + 1][this.prevHit[1]].getIsHit()
                || this.prevHit[0] - 1 >= 0
                        && !this.game.getPlayer1Squares()[this.prevHit[0] - 1][this.prevHit[1]].getIsHit()) {
            return true;
        }
        return false;
    }

    public boolean hitLeft() {
        int row = prevHit[0];
        int col = prevHit[1];

        boolean hit = this.game.getPlayer1Squares()[row][col - 1].hitSquare();

        if (hit && this.game.getPlayer1Squares()[row][col - 1].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHits++;
            this.prevHit[1] = col - 1;
            this.prevHitDir = ShipDirection.HORIZONTAL;
            return true;
        }
        this.game.setTurn(Turn.PLAYER1);
        return false;
    }

    public boolean hitRight() {
        int row = prevHit[0];
        int col = prevHit[1];

        boolean hit = this.game.getPlayer1Squares()[row][col + 1].hitSquare();

        if (hit && this.game.getPlayer1Squares()[row][col + 1].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHits++;
            this.prevHit[1] = col + 1;
            this.prevHitDir = ShipDirection.HORIZONTAL;
            return true;
        }
        this.game.setTurn(Turn.PLAYER1);
        return false;
    }

    public boolean hitTop() {
        int row = prevHit[0];
        int col = prevHit[1];

        boolean hit = this.game.getPlayer1Squares()[row - 1][col].hitSquare();

        if (hit && this.game.getPlayer1Squares()[row - 1][col].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHits++;
            this.prevHit[0] = row - 1;
            this.prevHitDir = ShipDirection.VERTICAL;
            return true;
        }
        this.game.setTurn(Turn.PLAYER1);
        return false;
    }

    public boolean hitBottom() {
        int row = prevHit[0];
        int col = prevHit[1];
        boolean hit = this.game.getPlayer1Squares()[row + 1][col].hitSquare();

        if (hit && this.game.getPlayer1Squares()[row + 1][col].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHits++;
            this.prevHit[0] = row + 1;
            this.prevHitDir = ShipDirection.VERTICAL;
            return true;
        }
        this.game.setTurn(Turn.PLAYER1);
        return false;
    }

    public boolean canHitColumnEndRight() {
        int row = prevHit[0];
        int col = prevHit[1];
        if ((col + this.prevHits) < 10 && !this.game.getPlayer1Squares()[row][col + this.prevHits].getIsHit()) {
            return true;
        }
        return false;
    }

    public boolean canHitColumnEndLeft() {
        int row = prevHit[0];
        int col = prevHit[1];
        if ((col - this.prevHits) >= 0 && !this.game.getPlayer1Squares()[row][col - this.prevHits].getIsHit()) {
            return true;
        }
        return false;
    }

    public boolean canHitRowEndTop() {
        int row = prevHit[0];
        int col = prevHit[1];
        if ((row - this.prevHits) >= 0 && !this.game.getPlayer1Squares()[row - prevHits][col].getIsHit()) {
            return true;
        }
        return false;
    }

    public boolean canHitRowEndBottom() {
        int row = prevHit[0];
        int col = prevHit[1];
        if ((row + this.prevHits) < 10 && !this.game.getPlayer1Squares()[row + this.prevHits][col].getIsHit()) {
            return true;
        }
        return false;
    }

    public boolean hitRowEndBottom() {
        int row = prevHit[0];
        int col = prevHit[1];

        boolean hit = this.game.getPlayer1Squares()[row + prevHits][col].hitSquare();
        if (hit && this.game.getPlayer1Squares()[row + prevHits][col].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHits++;
            this.prevHit[0] = row + prevHits;
            this.prevHitDir = ShipDirection.VERTICAL;
            return true;
        }
        this.game.setTurn(Turn.PLAYER1);
        return false;
    }

    public boolean hitRowEndTop() {
        int row = prevHit[0];
        int col = prevHit[1];

        boolean hit = this.game.getPlayer1Squares()[row - prevHits][col].hitSquare();
        if (hit && this.game.getPlayer1Squares()[row - prevHits][col].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHits++;
            this.prevHit[0] = row - prevHits;
            this.prevHitDir = ShipDirection.VERTICAL;
            return true;
        }
        this.game.setTurn(Turn.PLAYER1);
        return false;
    }

    public boolean hitColumnEndRight() {
        int row = prevHit[0];
        int col = prevHit[1];

        boolean hit = this.game.getPlayer1Squares()[row][col + this.prevHits].hitSquare();

        if (hit && this.game.getPlayer1Squares()[row][col + this.prevHits].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHit[1] = col + this.prevHits;
            this.prevHits++;
            this.prevHitDir = ShipDirection.HORIZONTAL;
            return true;
        }

        this.game.setTurn(Turn.PLAYER1);
        return false;

    }

    public boolean hitColumnEndLeft() {
        int row = prevHit[0];
        int col = prevHit[1];

        boolean hit = this.game.getPlayer1Squares()[row][col - this.prevHits].hitSquare();

        if (hit && this.game.getPlayer1Squares()[row][col - this.prevHits].getShip().isDead()) {
            this.prevHits = 0;
            return true;
        }
        if (hit) {
            this.prevHit[1] = col - this.prevHits;
            this.prevHits++;
            this.prevHitDir = ShipDirection.HORIZONTAL;
            return true;
        } else {
            this.game.setTurn(Turn.PLAYER1);
            return false;
        }

    }

    public boolean computerHitRandom() {
        Random random = new Random();
        Square[][] squares = this.game.getPlayer1Squares();
        while (true) {
            int row = random.nextInt(10);
            int column = random.nextInt(10);
            Square square = squares[row][column];
            if (square.getIsHit()) {
                continue;
            }
            boolean hit = square.hitSquare();
            if (!hit) {
                this.game.setTurn(Turn.PLAYER1);
                return false;
            }
            if (square.getShip().isDead()) {
                this.prevHits = 0;
            } else {
                this.prevHits++;
                this.prevHit[0] = row;
                this.prevHit[1] = column;
            }
            return true;
        }
    }
}
