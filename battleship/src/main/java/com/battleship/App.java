package com.battleship;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseButton;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Battleship");
        stage.setHeight(500);
        stage.setWidth(800);

        Game game = new Game();

        GridPane player1Setup = new GridPane();
        GridPane player2Setup = new GridPane();
        HBox setupHbox = new HBox(player1Setup, player2Setup);
        setupHbox.setSpacing(30);

        Scene setUpScene = new Scene(setupHbox, 500, 800);
        Square[][] squares = new Square[10][10];

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                int row = i;
                int column = k;

                Square square = new Square();
                Button button = square.getButton();
                squares[i][k] = square;

                button.setOnMouseEntered(event -> {
                    if (!game.player1ShipsIsEmpty()) {
                        Ship ship = game.peekNextPlayer1Ship();

                        if (game.getPlayer1Direction() == ShipDirection.HORIZONTAL) {
                            if (column + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = squares[row][(column - 1) + j];
                                    if (!activeSquare.hasShip()) {
                                        activeSquare.setGreyButtonColor();
                                    }
                                }
                            }
                        }
                        if (game.getPlayer1Direction() == ShipDirection.VERTICAL) {
                            if (row + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = squares[(row - 1) + j][column];
                                    if (!activeSquare.hasShip()) {
                                        activeSquare.setGreyButtonColor();
                                    }
                                }
                            }
                        }
                    }
                });

                button.setOnMouseExited(event -> {
                    if (!game.player1ShipsIsEmpty()) {
                        Ship ship = game.peekNextPlayer1Ship();
                        if (game.getPlayer1Direction() == ShipDirection.HORIZONTAL) {
                            if (column + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = squares[row][(column - 1) + j];
                                    if (!activeSquare.hasShip()) {
                                        activeSquare.removeButtonColor();
                                    }
                                }
                            }
                        }
                        if (game.getPlayer1Direction() == ShipDirection.VERTICAL) {
                            if (row + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = squares[(row - 1) + j][column];
                                    if (!activeSquare.hasShip()) {
                                        activeSquare.removeButtonColor();
                                    }
                                }
                            }
                        }
                    }
                });

                button.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (!game.player1ShipsIsEmpty()) {
                            if (game.getPlayer1Direction() == ShipDirection.HORIZONTAL) {
                                if ((column + game.peekNextPlayer1Ship().getSize()) <= 10) {

                                    Ship ship = game.getNewPlayer1Ship();

                                    for (int l = ship.getSize(); l > 0; l--) {
                                        squares[row][(column - 1) + l].addShip(ship);
                                        ship.addButton(squares[row][(column - 1) + l].getButton());
                                        squares[row][(column - 1) + l].setBlackButtonColor();
                                    }

                                    if (game.player1ShipsIsEmpty()) {
                                        stage.setScene(playScene(squares, game));
                                        stage.show();
                                    }
                                }
                            } else if (game.getPlayer1Direction() == ShipDirection.VERTICAL) {
                                if ((row + game.peekNextPlayer1Ship().getSize()) <= 10) {

                                    Ship ship = game.getNewPlayer1Ship();

                                    for (int l = ship.getSize(); l > 0; l--) {
                                        squares[(row - 1) + l][column].addShip(ship);
                                        ship.addButton(squares[(row - 1) + l][column].getButton());
                                        squares[(row - 1) + l][column].setBlackButtonColor();
                                    }

                                    if (game.player1ShipsIsEmpty()) {
                                        stage.setScene(playScene(squares, game));
                                        stage.show();
                                    }
                                }
                            }
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        for (int m = 0; m < 10; m++) {
                            for (int n = 0; n < 10; n++) {
                                if (!squares[n][m].hasShip()) {
                                    squares[n][m].removeButtonColor();
                                }
                            }
                        }
                        game.changePlayer1Direction();

                    }

                });
                player1Setup.add(button, k, i);
            }
            stage.setScene(setUpScene);
            stage.show();
        }

    };

    public Scene playScene(Square[][] squares, Game game) {
        GridPane gridpane = new GridPane();

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {

                Square square = squares[i][k];
                Button button = square.getButton();

                square.removeButtonColor();

                button.setOnMouseClicked(event -> {
                    boolean isHit = square.hitSquare();
                    if (isHit) {
                        button.setText("X");
                        if (square.getShip().isDead()) {
                            square.getShip().setButtonsDisabled();
                        }
                    } else {
                        button.setText("O");
                        game.changeTurn();
                    }
                });

                gridpane.add(button, k, i);

            }
        }
        Scene scene = new Scene(gridpane, 500, 800);

        return scene;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
