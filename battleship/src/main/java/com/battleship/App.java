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
        Square[][] player1Squares = game.getPlayer1Squares();

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                int row = i;
                int column = k;

                Button button = player1Squares[i][k].getButton();

                button.setOnMouseEntered(event -> {
                    if (!game.player1ShipsIsEmpty()) {
                        Ship ship = game.peekNextPlayer1Ship();

                        if (game.getShipDirection() == ShipDirection.HORIZONTAL) {
                            if (column + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = player1Squares[row][(column - 1) + j];
                                    if (!activeSquare.hasShip()) {
                                        activeSquare.setGreyButtonColor();
                                    }
                                }
                            }
                        }
                        if (game.getShipDirection() == ShipDirection.VERTICAL) {
                            if (row + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = player1Squares[(row - 1) + j][column];
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
                        if (game.getShipDirection() == ShipDirection.HORIZONTAL) {
                            if (column + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = player1Squares[row][(column - 1) + j];
                                    if (!activeSquare.hasShip()) {
                                        activeSquare.removeButtonColor();
                                    }
                                }
                            }
                        }
                        if (game.getShipDirection() == ShipDirection.VERTICAL) {
                            if (row + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = player1Squares[(row - 1) + j][column];
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
                            int index = game.getShipDirection() == ShipDirection.HORIZONTAL ? column : row;
                            if ((index + game.peekNextPlayer1Ship().getSize()) <= 10) {

                                Ship ship = game.getNewPlayer1Ship();

                                for (int l = ship.getSize(); l > 0; l--) {
                                    Square activeSquare = game.getShipDirection() == ShipDirection.HORIZONTAL
                                            ? player1Squares[row][(column - 1) + l]
                                            : player1Squares[(row - 1) + l][column];

                                    activeSquare.addShip(ship);
                                    ship.addButton(activeSquare.getButton());
                                    activeSquare.setBlackButtonColor();
                                }

                                if (game.player1ShipsIsEmpty()) {
                                    // stage.setScene(playScene(player1Squares, game));
                                    // stage.show();
                                }
                            }

                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        for (int m = 0; m < 10; m++) {
                            for (int n = 0; n < 10; n++) {
                                if (!player1Squares[n][m].hasShip()) {
                                    player1Squares[n][m].removeButtonColor();
                                }
                            }
                        }
                        game.changeShipDirection();

                    }

                });
                player1Setup.add(button, k, i);
            }
        }

        ////////////////////////////////////

        Square[][] player2Squares = game.getPlayer2Squares();

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                int row = i;
                int column = k;

                Button button = player2Squares[i][k].getButton();

                button.setOnMouseEntered(event -> {
                    if (!game.player2ShipsIsEmpty()) {
                        Ship ship = game.peekNextPlayer2Ship();

                        if (game.getShipDirection() == ShipDirection.HORIZONTAL) {
                            if (column + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = player2Squares[row][(column - 1) + j];
                                    if (!activeSquare.hasShip()) {
                                        activeSquare.setGreyButtonColor();
                                    }
                                }
                            }
                        }
                        if (game.getShipDirection() == ShipDirection.VERTICAL) {
                            if (row + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = player2Squares[(row - 1) + j][column];
                                    if (!activeSquare.hasShip()) {
                                        activeSquare.setGreyButtonColor();
                                    }
                                }
                            }
                        }
                    }
                });

                button.setOnMouseExited(event -> {
                    if (!game.player2ShipsIsEmpty()) {
                        Ship ship = game.peekNextPlayer2Ship();
                        if (game.getShipDirection() == ShipDirection.HORIZONTAL) {
                            if (column + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = player2Squares[row][(column - 1) + j];
                                    if (!activeSquare.hasShip()) {
                                        activeSquare.removeButtonColor();
                                    }
                                }
                            }
                        }
                        if (game.getShipDirection() == ShipDirection.VERTICAL) {
                            if (row + ship.getSize() <= 10) {
                                for (int j = ship.getSize(); j > 0; j--) {
                                    Square activeSquare = player2Squares[(row - 1) + j][column];
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
                        if (!game.player2ShipsIsEmpty()) {
                            int index = game.getShipDirection() == ShipDirection.HORIZONTAL ? column : row;
                            if ((index + game.peekNextPlayer2Ship().getSize()) <= 10) {

                                Ship ship = game.getNewPlayer2Ship();

                                for (int l = ship.getSize(); l > 0; l--) {
                                    Square activeSquare = game.getShipDirection() == ShipDirection.HORIZONTAL
                                            ? player2Squares[row][(column - 1) + l]
                                            : player2Squares[(row - 1) + l][column];

                                    activeSquare.addShip(ship);
                                    ship.addButton(activeSquare.getButton());
                                    activeSquare.setBlackButtonColor();
                                }

                                if (game.player2ShipsIsEmpty()) {
                                    stage.setScene(playScene(game));
                                    stage.show();
                                }
                            }

                        }
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        for (int m = 0; m < 10; m++) {
                            for (int n = 0; n < 10; n++) {
                                if (!player2Squares[n][m].hasShip()) {
                                    player2Squares[n][m].removeButtonColor();
                                }
                            }
                        }
                        game.changeShipDirection();
                    }
                });
                player2Setup.add(button, k, i);
            }
            stage.setScene(setUpScene);
            stage.show();
        }

    };

    public Scene playScene(Game game) {
        GridPane gridpane1 = new GridPane();
        GridPane gridpane2 = new GridPane();

        Square[][] player1Squares = game.getPlayer1Squares();
        Square[][] player2Squares = game.getPlayer2Squares();

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {

                Square square = player1Squares[i][k];
                Button button = square.getButton();

                square.removeButtonColor();

                button.setOnMouseClicked(event -> {
                    if (game.getTurn() == Turn.PLAYER2) {
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
                    }
                });

                gridpane1.add(button, k, i);

            }
        }

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                Square square = player2Squares[i][k];
                Button button = square.getButton();

                square.removeButtonColor();

                button.setOnMouseClicked(event -> {
                    if (game.getTurn() == Turn.PLAYER1) {
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
                    }
                });
                gridpane2.add(button, k, i);
            }
        }
        HBox hbox = new HBox(gridpane1, gridpane2);
        hbox.setSpacing(30);
        Scene scene = new Scene(hbox, 500, 800);

        return scene;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
