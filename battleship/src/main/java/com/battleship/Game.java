package com.battleship;

import java.util.Stack;

enum ShipDirection {
    HORIZONTAL, VERTICAL
}

public class Game {

    private int turn;
    private boolean gameOver;
    private Stack<Ship> player1Ships;
    private Stack<Ship> player2Ships;
    private ShipDirection player1Direction;

    public Game() {
        this.turn = 1;
        this.gameOver = false;
        this.player1Direction = ShipDirection.VERTICAL;
        this.player1Ships = new Stack<Ship>();

        Ship ship1 = new Ship(1);
        Ship ship2 = new Ship(2);
        Ship ship3 = new Ship(3);
        Ship ship4 = new Ship(3);
        Ship ship5 = new Ship(4);
        Ship ship6 = new Ship(5);

        this.player1Ships.push(ship1);
        this.player1Ships.push(ship2);
        this.player1Ships.push(ship3);
        this.player1Ships.push(ship4);
        this.player1Ships.push(ship5);
        this.player1Ships.push(ship6);

    }

    public void changeTurn() {
        this.turn = this.turn == 1 ? 2 : 1;
    }

    public int getTurn() {
        return this.turn;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    public boolean isGameOver() {
        return this.gameOver;
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

    public ShipDirection getPlayer1Direction() {
        return this.player1Direction;
    }

    public void changePlayer1Direction() {
        this.player1Direction = this.player1Direction == ShipDirection.HORIZONTAL ? ShipDirection.VERTICAL
                : ShipDirection.HORIZONTAL;
    }
}
