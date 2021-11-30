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

        this.player1Ships = new Stack<Ship>();
        this.player2Ships = new Stack<Ship>();

        Ship player1Ship1 = new Ship(1);
        Ship player1Ship2 = new Ship(2);
        Ship player1Ship3 = new Ship(3);
        Ship player1Ship4 = new Ship(3);
        Ship player1Ship5 = new Ship(4);
        Ship player1Ship6 = new Ship(5);

        this.player1Ships.push(player1Ship1);
        this.player1Ships.push(player1Ship2);
        this.player1Ships.push(player1Ship3);
        this.player1Ships.push(player1Ship4);
        this.player1Ships.push(player1Ship5);
        this.player1Ships.push(player1Ship6);

        Ship player2Ship1 = new Ship(1);
        Ship player2Ship2 = new Ship(2);
        Ship player2Ship3 = new Ship(3);
        Ship player2Ship4 = new Ship(3);
        Ship player2Ship5 = new Ship(4);
        Ship player2Ship6 = new Ship(5);

        this.player2Ships.push(player2Ship1);
        this.player2Ships.push(player2Ship2);
        this.player2Ships.push(player2Ship3);
        this.player2Ships.push(player2Ship4);
        this.player2Ships.push(player2Ship5);
        this.player2Ships.push(player2Ship6);

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
