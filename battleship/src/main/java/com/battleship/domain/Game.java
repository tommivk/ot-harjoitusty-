package com.battleship.domain;

import java.util.Stack;

public class Game {

    private Turn turn;
    private boolean gameOver;
    private Stack<Ship> player1Ships;
    private Stack<Ship> player2Ships;
    private ShipDirection shipDirection;
    private Square[][] player1Squares;
    private Square[][] player2Squares;

    public Game() {
        this.turn = Turn.PLAYER1;
        this.gameOver = false;
        this.shipDirection = ShipDirection.VERTICAL;

        this.player1Squares = initializeBoard(10);
        this.player2Squares = initializeBoard(10);

        this.player1Ships = initializeShips();
        this.player2Ships = initializeShips();
    }

    public Square[][] initializeBoard(int size) {
        Square[][] squares = new Square[size][size];
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                squares[i][k] = new Square();
            }
        }
        return squares;
    }

    public Stack<Ship> initializeShips() {
        Stack<Ship> ships = new Stack<Ship>();

        ships.push(new Ship(1));
        ships.push(new Ship(2));
        ships.push(new Ship(3));
        ships.push(new Ship(3));
        ships.push(new Ship(4));
        ships.push(new Ship(5));

        return ships;
    }

    public void changeTurn() {
        this.turn = this.turn == Turn.PLAYER1 ? Turn.PLAYER2 : Turn.PLAYER1;
    }

    public Turn getTurn() {
        return this.turn;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public Square[][] getPlayer1Squares() {
        return this.player1Squares;
    }

    public Square[][] getPlayer2Squares() {
        return this.player2Squares;
    }

    public Stack<Ship> getPlayer1Ships() {
        return this.player1Ships;
    }

    public Ship getNewPlayer1Ship() {
        return this.player1Ships.pop();
    }

    public Ship peekNextPlayer1Ship() {
        return this.player1Ships.peek();
    }

    public boolean player1ShipsIsEmpty() {
        return this.player1Ships.isEmpty();
    }

    public Ship getNewPlayer2Ship() {
        return this.player2Ships.pop();
    }

    public Ship peekNextPlayer2Ship() {
        return this.player2Ships.peek();
    }

    public boolean player2ShipsIsEmpty() {
        return this.player2Ships.isEmpty();
    }

    public ShipDirection getShipDirection() {
        return this.shipDirection;
    }

    public void changeShipDirection() {
        this.shipDirection = this.shipDirection == ShipDirection.HORIZONTAL ? ShipDirection.VERTICAL
                : ShipDirection.HORIZONTAL;
    }
}
