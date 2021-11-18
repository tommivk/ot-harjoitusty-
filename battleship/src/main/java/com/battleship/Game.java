package com.battleship;

public class Game {

    private int turn;
    private boolean gameOver;

    public Game() {
        this.turn = 1;
        this.gameOver = false;
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

}
