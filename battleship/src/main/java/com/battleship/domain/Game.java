package com.battleship.domain;

import java.util.Stack;

public class Game {

    private Turn turn;
    private boolean gameOver;
    private boolean againstComputer;
    private Stack<Ship> player1Ships;
    private Stack<Ship> player2Ships;
    private ShipDirection shipDirection;
    private Square[][] player1Squares;
    private Square[][] player2Squares;
    private Computer computer;
    private User playerOne;
    private User playerTwo;
    private int gameId;

    public Game(int boardSize) {
        this.turn = Turn.PLAYER1;
        this.gameOver = false;
        this.shipDirection = ShipDirection.VERTICAL;

        this.againstComputer = false;
        this.computer = new Computer(this);

        this.player1Squares = initializeBoard(boardSize);
        this.player2Squares = initializeBoard(boardSize);

        this.player1Ships = initializeShips();
        this.player2Ships = initializeShips();

        this.playerOne = new User("Player 1");
        this.playerTwo = new User("Player 2");
    }

    public Game(int boardSize, int id, User playerOne, User playerTwo) {
        this.gameId = id;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.turn = Turn.PLAYER1;
        this.gameOver = false;
        this.shipDirection = ShipDirection.VERTICAL;

        this.againstComputer = false;
        this.computer = new Computer(this);

        this.player1Squares = initializeBoard(boardSize);
        this.player2Squares = initializeBoard(boardSize);

        this.player1Ships = initializeShips();
        this.player2Ships = initializeShips();
    }

    public int getGameId() {
        return this.gameId;
    }

    public void setPlayerOne(User user) {
        this.playerOne = user;
    }

    public void setPlayerTwo(User user) {
        this.playerTwo = user;
    }

    public User getPlayerOne() {
        return this.playerOne;
    }

    public User getPlayerTwo() {
        return this.playerTwo;
    }

    public Computer getComputer() {
        return this.computer;
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

    public void highlightSquares(int row, int column, int player) {
        if ((player == 1 && !this.player1ShipsIsEmpty()) || (player == 2 && !this.player2ShipsIsEmpty())) {

            Ship ship = player == 1 ? this.peekNextPlayer1Ship() : this.peekNextPlayer2Ship();
            int index = this.getShipDirection() == ShipDirection.HORIZONTAL ? column : row;
            Square[][] squares = player == 1 ? this.player1Squares : this.player2Squares;

            if (index + ship.getSize() <= 10) {
                for (int j = ship.getSize(); j > 0; j--) {
                    Square activeSquare = this.getShipDirection() == ShipDirection.HORIZONTAL
                            ? squares[row][(column - 1) + j]
                            : squares[(row - 1) + j][column];
                    if (!activeSquare.hasShip()) {
                        activeSquare.setGreyButtonColor();
                    }
                }
            }
        }
    }

    public void clearButtonColors(int player) {
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                Square square = player == 1 ? this.player1Squares[i][k] : this.player2Squares[i][k];
                square.removeButtonColor();
            }
        }
    }

    public void removeHighlight(int row, int column, int player) {
        if ((player == 1 && !this.player1ShipsIsEmpty()) || (player == 2 && !this.player2ShipsIsEmpty())) {
            Ship ship = player == 1 ? this.peekNextPlayer1Ship() : this.peekNextPlayer2Ship();

            int index = this.getShipDirection() == ShipDirection.HORIZONTAL ? column : row;
            Square[][] squares = player == 1 ? this.player1Squares : this.player2Squares;

            if (index + ship.getSize() <= 10) {
                for (int j = ship.getSize(); j > 0; j--) {
                    Square activeSquare = this.getShipDirection() == ShipDirection.HORIZONTAL
                            ? squares[row][(column - 1) + j]
                            : squares[(row - 1) + j][column];
                    if (!activeSquare.hasShip()) {
                        activeSquare.removeButtonColor();
                    }
                }
            }
        }
    }

    public void placeShip(int row, int column, int player) {
        if ((player == 1 && !this.player1ShipsIsEmpty()) || (player == 2 && !this.player2ShipsIsEmpty())) {
            int index = this.getShipDirection() == ShipDirection.HORIZONTAL ? column : row;

            Ship nextShip = player == 1 ? this.peekNextPlayer1Ship() : this.peekNextPlayer2Ship();
            Square[][] squares = player == 1 ? this.player1Squares : this.player2Squares;

            if ((index + nextShip.getSize()) <= 10) {

                if (canPlaceShip(nextShip, row, column, squares)) {
                    Ship ship = player == 1 ? this.getNewPlayer1Ship() : this.getNewPlayer2Ship();
                    for (int l = ship.getSize(); l > 0; l--) {
                        Square activeSquare = this.getShipDirection() == ShipDirection.HORIZONTAL
                                ? squares[row][(column - 1) + l]
                                : squares[(row - 1) + l][column];

                        activeSquare.addShip(ship);
                        ship.addButton(activeSquare.getButton());
                        activeSquare.setBlackButtonColor();
                    }
                }
            }

        }
    }

    public boolean canPlaceShip(Ship ship, int row, int column, Square[][] squares) {
        int shipSize = ship.getSize();
        if (this.shipDirection == ShipDirection.VERTICAL && row + shipSize > 10
                || this.shipDirection == ShipDirection.HORIZONTAL && column + shipSize > 10) {
            return false;
        }

        boolean isOk = true;
        for (int i = ship.getSize(); i > 0; i--) {
            Square activeSquare = this.shipDirection == ShipDirection.HORIZONTAL
                    ? squares[row][(column - 1) + i]
                    : squares[(row - 1) + i][column];

            if (activeSquare.hasShip()) {
                isOk = false;
            }
        }
        return isOk;
    }

    public boolean allPlayer1ShipsDead() {
        boolean allShipsDead = true;
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                if (this.player1Squares[i][k].hasShip() && !this.player1Squares[i][k].getShip().isDead()) {
                    allShipsDead = false;
                }
            }
        }
        return allShipsDead;
    }

    public boolean allPlayer2ShipsDead() {
        boolean allShipsDead = true;
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                if (this.player2Squares[i][k].hasShip() && !this.player2Squares[i][k].getShip().isDead()) {
                    allShipsDead = false;
                }
            }
        }
        return allShipsDead;
    }

    public void changeTurn() {
        this.turn = this.turn == Turn.PLAYER1 ? Turn.PLAYER2 : Turn.PLAYER1;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
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

    public void setIsAgainstComputer(boolean bool) {
        this.againstComputer = bool;
    }

    public boolean getIsAgainstComputer() {
        return this.againstComputer;
    }

}
