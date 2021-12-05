package com.battleship.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseButton;

import com.battleship.domain.Game;
import com.battleship.domain.Square;
import com.battleship.domain.Turn;

public class BattleshipUi extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Battleship");
        stage.setHeight(500);
        stage.setWidth(800);

        int boardSize = 10;
        Game game = new Game(boardSize);

        GridPane player1Setup = new GridPane();
        GridPane player2Setup = new GridPane();
        HBox setupHbox = new HBox(player1Setup, player2Setup);
        setupHbox.setSpacing(30);

        Scene setUpScene = new Scene(setupHbox, 800, 500);

        Square[][] player1Squares = game.getPlayer1Squares();
        Square[][] player2Squares = game.getPlayer2Squares();

        for (int i = 0; i < boardSize; i++) {
            for (int k = 0; k < boardSize; k++) {
                int row = i;
                int column = k;

                Rectangle player1Button = player1Squares[i][k].getButton();
                Rectangle player2Button = player2Squares[i][k].getButton();

                player1Button.setOnMouseEntered(event -> {
                    game.highlightSquares(row, column, 1);
                });

                player1Button.setOnMouseExited(event -> {
                    game.removeHighlight(row, column, 1);
                });

                player1Button.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        game.placeShip(row, column, 1);
                    }

                    if (event.getButton() == MouseButton.SECONDARY) {
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

                player1Setup.add(player1Button, k, i);

                player2Button.setOnMouseEntered(event -> {
                    if (game.player1ShipsIsEmpty()) {
                        game.highlightSquares(row, column, 2);
                    }
                });

                player2Button.setOnMouseExited(event -> {
                    game.removeHighlight(row, column, 2);
                });

                player2Button.setOnMouseClicked(event -> {
                    if (game.player1ShipsIsEmpty()) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            game.placeShip(row, column, 2);

                            if (game.player2ShipsIsEmpty()) {
                                stage.setScene(playScene(game));
                                stage.show();
                            }
                        }

                        if (event.getButton() == MouseButton.SECONDARY) {
                            for (int m = 0; m < 10; m++) {
                                for (int n = 0; n < 10; n++) {
                                    if (!player2Squares[n][m].hasShip()) {
                                        player2Squares[n][m].removeButtonColor();
                                    }
                                }
                            }
                            game.changeShipDirection();
                        }
                    }
                });
                player2Setup.add(player2Button, k, i);

                stage.setScene(setUpScene);
                stage.show();
            }
        }
    }

    public Scene playScene(Game game) {
        GridPane gridpane1 = new GridPane();
        GridPane gridpane2 = new GridPane();

        Square[][] player1Squares = game.getPlayer1Squares();
        Square[][] player2Squares = game.getPlayer2Squares();

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {

                Square square = player1Squares[i][k];
                Rectangle button = square.getButton();

                square.removeButtonColor();

                button.setOnMouseClicked(event -> {
                    if (game.getTurn() == Turn.PLAYER2) {
                        boolean isHit = square.hitSquare();
                        if (isHit) {
                            square.setBlackButtonColor();
                            if (square.getShip().isDead()) {
                                square.getShip().setButtonsDisabled();
                            }
                        } else {
                            square.setBlueButtonColor();
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
                Rectangle button = square.getButton();

                square.removeButtonColor();

                button.setOnMouseClicked(event -> {
                    if (game.getTurn() == Turn.PLAYER1) {
                        boolean isHit = square.hitSquare();
                        if (isHit) {
                            square.setBlackButtonColor();
                            if (square.getShip().isDead()) {
                                square.getShip().setButtonsDisabled();
                            }
                        } else {
                            square.setBlueButtonColor();
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
