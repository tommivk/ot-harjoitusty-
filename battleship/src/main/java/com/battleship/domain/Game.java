package com.battleship.domain;

import java.util.Stack;

import com.battleship.enums.ShipDirection;
import com.battleship.enums.Player;

/**
 * Game state
 */
public class Game {

    private Player turn;
    private boolean gameOver;
    private boolean againstComputer;
    private Stack<Ship> playerOneShips;
    private Stack<Ship> playerTwoShips;
    private ShipDirection shipDirection;
    private Square[][] playerOneSquares;
    private Square[][] playerTwoSquares;
    private Computer computer;
    private User playerOne;
    private User playerTwo;
    private int gameId;

    public Game(int boardSize) {
        this.turn = Player.PLAYER1;
        this.gameOver = false;
        this.shipDirection = ShipDirection.VERTICAL;

        this.againstComputer = false;
        this.computer = new Computer(this);

        this.playerOneSquares = initializeBoard(boardSize);
        this.playerTwoSquares = initializeBoard(boardSize);

        this.playerOneShips = initializeShips();
        this.playerTwoShips = initializeShips();

        this.playerOne = new User("Player 1");
        this.playerTwo = new User("Player 2");
    }

    public Game(int boardSize, int id, User playerOne, User playerTwo) {
        this.gameId = id;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.turn = Player.PLAYER1;
        this.gameOver = false;
        this.shipDirection = ShipDirection.VERTICAL;

        this.againstComputer = false;
        this.computer = new Computer(this);

        this.playerOneSquares = initializeBoard(boardSize);
        this.playerTwoSquares = initializeBoard(boardSize);

        this.playerOneShips = initializeShips();
        this.playerTwoShips = initializeShips();
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

    /**
     * Initializes new Square object to all squares in the array
     * 
     * @param size size of the board
     * @return returns two dimentional array of Square objects
     */
    public Square[][] initializeBoard(int size) {
        Square[][] squares = new Square[size][size];
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                squares[i][k] = new Square();
            }
        }
        return squares;
    }

    /**
     * Creates new ships
     * 
     * @return stack containing all the players ships
     */
    public Stack<Ship> initializeShips() {
        Stack<Ship> ships = new Stack<Ship>();

        ships.push(new Ship(2));
        ships.push(new Ship(2));
        ships.push(new Ship(3));
        ships.push(new Ship(3));
        ships.push(new Ship(4));
        ships.push(new Ship(5));

        return ships;
    }

    /**
     * Highlights squares based on the next ships size and orientation
     * 
     * @param player which players squares will be highlighted
     * @param row    row of the square
     * @param column column of the square
     */
    public void highlightSquares(int row, int column, Player player) {
        if ((player == Player.PLAYER1 && !this.playerOneShipsIsEmpty())
                || (player == Player.PLAYER2 && !this.playerTwoShipsIsEmpty())) {

            Ship ship = player == Player.PLAYER1 ? this.peekNextPlayerOneShip() : this.peekNextPlayerTwoShip();
            int index = this.getShipDirection() == ShipDirection.HORIZONTAL ? column : row;
            Square[][] squares = player == Player.PLAYER1 ? this.playerOneSquares : this.playerTwoSquares;

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

    /**
     * Removes images from all the squares
     * 
     * @param player which players squares will be cleared
     */
    public void clearButtonColors(Player player) {
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                Square square = player == Player.PLAYER1 ? this.playerOneSquares[i][k] : this.playerTwoSquares[i][k];
                square.removeButtonImage();
            }
        }
    }

    /**
     * Removes images from all the squares that do not contain a ship
     * 
     * @param player which players squares will be cleared
     * @param row    row
     * @param column column
     */
    public void removeButtonImage(int row, int column, Player player) {
        if ((player == Player.PLAYER1 && !this.playerOneShipsIsEmpty())
                || (player == Player.PLAYER2 && !this.playerTwoShipsIsEmpty())) {
            Ship ship = player == Player.PLAYER1 ? this.peekNextPlayerOneShip() : this.peekNextPlayerTwoShip();

            int index = this.getShipDirection() == ShipDirection.HORIZONTAL ? column : row;
            Square[][] squares = player == Player.PLAYER1 ? this.playerOneSquares : this.playerTwoSquares;

            if (index + ship.getSize() <= 10) {
                for (int j = ship.getSize(); j > 0; j--) {
                    Square activeSquare = this.getShipDirection() == ShipDirection.HORIZONTAL
                            ? squares[row][(column - 1) + j]
                            : squares[(row - 1) + j][column];
                    if (!activeSquare.hasShip()) {
                        activeSquare.removeButtonImage();
                    }
                }
            }
        }
    }

    /**
     * Places new ship to the coordinates
     * 
     * @param row    row where the ship is placed
     * 
     * @param column column where the ship is placed
     * @param player player whose ship will be placed
     */
    public void placeShip(int row, int column, Player player) {
        if ((player == Player.PLAYER1 && !this.playerOneShipsIsEmpty())
                || (player == Player.PLAYER2 && !this.playerTwoShipsIsEmpty())) {
            int index = this.shipDirection == ShipDirection.HORIZONTAL ? column : row;
            Ship nextShip = player == Player.PLAYER1 ? this.peekNextPlayerOneShip() : this.peekNextPlayerTwoShip();
            Square[][] squares = player == Player.PLAYER1 ? this.playerOneSquares : this.playerTwoSquares;

            if ((index + nextShip.getSize()) <= 10 && canPlaceShip(nextShip, row, column, squares)) {
                Ship ship = player == Player.PLAYER1 ? this.getNewPlayerOneShip() : this.getNewPlayerTwoShip();
                for (int l = ship.getSize(); l > 0; l--) {
                    Square activeSquare = this.shipDirection == ShipDirection.HORIZONTAL
                            ? squares[row][(column - 1) + l]
                            : squares[(row - 1) + l][column];

                    activeSquare.addShip(ship);
                    ship.addRectangle(activeSquare.getRectangle());
                    ship.setShipDirection(this.shipDirection);
                    ship.setShipImage();
                }
            }
        }
    }

    /**
     * Checks if placing ship is allowed to the coordinates
     * 
     * @param ship    ship object
     * @param row     row where the ship would be placed
     * 
     * @param column  column where the ship would be placed
     * @param squares squares where the ship would be placed
     * @return returns true if placing ship is allowed
     */
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

    /**
     * Returns true if all player one ships are dead
     * 
     * @return returns true if they are
     */
    public boolean allPlayerOneShipsDead() {
        boolean allShipsDead = true;
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                if (this.playerOneSquares[i][k].hasShip() && !this.playerOneSquares[i][k].getShip().isDead()) {
                    allShipsDead = false;
                }
            }
        }
        return allShipsDead;
    }

    /**
     * Returns true if all player two ships are dead
     * 
     * @return returns true if they are
     */
    public boolean allPlayerTwoShipsDead() {
        boolean allShipsDead = true;
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                if (this.playerTwoSquares[i][k].hasShip() && !this.playerTwoSquares[i][k].getShip().isDead()) {
                    allShipsDead = false;
                }
            }
        }
        return allShipsDead;
    }

    public void changeTurn() {
        this.turn = this.turn == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }

    public Player getTurn() {
        return this.turn;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public Square[][] getPlayerOneSquares() {
        return this.playerOneSquares;
    }

    public Square[][] getPlayerTwoSquares() {
        return this.playerTwoSquares;
    }

    /**
     * Gets all the player one ships that haven't been placed on the board yet
     * 
     * @return returns stack of Ship objects
     */
    public Stack<Ship> getPlayerOneShips() {
        return this.playerOneShips;
    }

    /**
     * Removes one ship of the player one's ships and returns it
     * 
     * @return Ship
     */
    public Ship getNewPlayerOneShip() {
        return this.playerOneShips.pop();
    }

    /**
     * Peeks the next player one'sship that will be placed on the board
     * 
     * @return Ship
     */
    public Ship peekNextPlayerOneShip() {
        return this.playerOneShips.peek();
    }

    /**
     * Checks if all player two ships are placed on the board
     * 
     * @return returns true if they are
     */
    public boolean playerOneShipsIsEmpty() {
        return this.playerOneShips.isEmpty();
    }

    /**
     * Removes one ship of the player twos's ships and returns it
     * 
     * @return Ship
     */
    public Ship getNewPlayerTwoShip() {
        return this.playerTwoShips.pop();
    }

    /**
     * Peeks the next player two's ship that will be placed on the board
     * 
     * @return Ship
     */
    public Ship peekNextPlayerTwoShip() {
        return this.playerTwoShips.peek();
    }

    /**
     * Checks if all player two ships are placed on the board
     * 
     * @return returns true if they are
     */
    public boolean playerTwoShipsIsEmpty() {
        return this.playerTwoShips.isEmpty();
    }

    public ShipDirection getShipDirection() {
        return this.shipDirection;
    }

    /*
     * Changes the ships direction
     */
    public void changeShipDirection() {
        this.shipDirection = this.shipDirection == ShipDirection.HORIZONTAL ? ShipDirection.VERTICAL
                : ShipDirection.HORIZONTAL;
    }

    public void setIsAgainstComputer(boolean bool) {
        this.againstComputer = bool;
    }

    /**
     * 
     * @return true if the game is against computer
     */
    public boolean getIsAgainstComputer() {
        return this.againstComputer;
    }

}
