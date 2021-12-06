package com.battleship.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        Label player1Label = new Label("Player 1");
        player1Label.setPadding(new Insets(10, 0, 10, 0));

        Label player2Label = new Label("Player 2");
        player2Label.setPadding(new Insets(10, 0, 10, 0));

        GridPane player1SetupGrid = new GridPane();
        GridPane player2SetupGrid = new GridPane();

        VBox player1Setup = new VBox(player1Label, player1SetupGrid);
        player1Setup.setAlignment(Pos.TOP_CENTER);

        VBox player2Setup = new VBox(player2Label, player2SetupGrid);
        player2Setup.setAlignment(Pos.TOP_CENTER);

        HBox setupHbox = new HBox(player1Setup, player2Setup);
        setupHbox.setAlignment(Pos.CENTER);
        setupHbox.setSpacing(30);

        Label tipLabel = new Label("Tip: click mouse 2 to change the ships direction");
        tipLabel.setPadding(new Insets(20, 0, 0, 0));

        VBox container = new VBox(setupHbox, tipLabel);
        container.setAlignment(Pos.TOP_CENTER);

        Scene setUpScene = new Scene(container, 800, 500);

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
                        game.highlightSquares(row, column, 1);
                    }

                });

                player1SetupGrid.add(player1Button, k, i);

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
                            game.highlightSquares(row, column, 2);
                        }
                    }
                });
                player2SetupGrid.add(player2Button, k, i);

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

        Label turnLabel = new Label("TURN: Player 1");
        turnLabel.setPadding(new Insets(20, 0, 0, 0));

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {

                Square square = player1Squares[i][k];
                Rectangle button = square.getButton();

                square.removeButtonColor();

                button.setOnMouseClicked(event -> {
                    if (!game.isGameOver() && game.getTurn() == Turn.PLAYER2 && !square.getIsHit()) {
                        boolean hasShip = square.hitSquare();
                        if (hasShip) {
                            square.setBlackButtonColor();
                            if (square.getShip().isDead()) {
                                square.getShip().setDeadShipColor();
                            }
                        } else {
                            square.setBlueButtonColor();
                            game.changeTurn();
                            turnLabel.setText("TURN: Player 1");
                        }
                    }
                    if (game.allPlayer1ShipsDead()) {
                        game.setGameOver();
                        turnLabel.setText("PLAYER 2 WINS!");
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
                    if (!game.isGameOver() && game.getTurn() == Turn.PLAYER1 && !square.getIsHit()) {
                        boolean hasShip = square.hitSquare();
                        if (hasShip) {
                            square.setBlackButtonColor();
                            if (square.getShip().isDead()) {
                                square.getShip().setDeadShipColor();
                            }
                        } else {
                            square.setBlueButtonColor();
                            game.changeTurn();
                            turnLabel.setText("TURN: Player 2");
                        }
                        if (game.allPlayer2ShipsDead()) {
                            game.setGameOver();
                            turnLabel.setText("PLAYER 1 WINS!");
                        }
                    }
                });
                gridpane2.add(button, k, i);
            }
        }

        Label player1Label = new Label("Player 1");
        player1Label.setPadding(new Insets(10, 0, 10, 0));

        Label player2Label = new Label("Player 2");
        player2Label.setPadding(new Insets(10, 0, 10, 0));

        VBox player1VBox = new VBox(player1Label, gridpane1);
        player1VBox.setAlignment(Pos.TOP_CENTER);

        VBox player2VBox = new VBox(player2Label, gridpane2);
        player2VBox.setAlignment(Pos.TOP_CENTER);

        HBox hbox = new HBox(player1VBox, player2VBox);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);

        VBox container = new VBox(hbox, turnLabel);
        container.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(container, 800, 500);

        return scene;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
