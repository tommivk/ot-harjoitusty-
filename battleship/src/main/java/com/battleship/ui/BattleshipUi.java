package com.battleship.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.input.MouseButton;

import com.battleship.dao.DBUserDao;
import com.battleship.domain.Game;
import com.battleship.domain.Square;
import com.battleship.domain.Turn;
import com.battleship.domain.User;
import com.battleship.domain.UserService;

public class BattleshipUi extends Application {
    private UserService userService;
    private Game game;

    @Override
    public void init() throws Exception {
        DBUserDao dbUserDao = new DBUserDao();
        userService = new UserService(dbUserDao);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Battleship");
        stage.setHeight(500);
        stage.setWidth(800);

        game = new Game(10);

        stage.setScene(startScene(stage));
        stage.show();
    }

    public Scene startScene(Stage stage) {
        StackPane stackpane = new StackPane();
        StackPane stackpane2 = new StackPane();

        Rectangle loginRect = new Rectangle(200, 200);
        Rectangle signUpRect = new Rectangle(200, 200);

        loginRect.setFill(Color.DARKGREY);
        signUpRect.setFill(Color.DARKGREY);

        loginRect.setOnMouseClicked(event -> {
            stage.setScene(loginScene(stage));
            stage.show();
        });

        signUpRect.setOnMouseClicked(event -> {
            stage.setScene(signUpScene(stage));
        });

        Text loginText = new Text("Login");
        Text newUserText = new Text("New user");
        stackpane.getChildren().addAll(loginRect, loginText);
        stackpane2.getChildren().addAll(signUpRect, newUserText);

        Button skip = new Button("Continue without logging in");
        skip.setOnMouseClicked(event -> {
            stage.setScene(gameSelectionScene(stage));
            stage.show();
        });

        HBox hbox = new HBox(stackpane, stackpane2, skip);
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);
        return new Scene(hbox);
    }

    public Scene signUpScene(Stage stage) {
        Label label = new Label("Name:");
        TextField textfield = new TextField();
        Button button = new Button("Add new user");
        button.setOnMouseClicked(event -> {
            boolean success = userService.createUser(textfield.getText());
            userService.getAllUsers();
            if (success) {
                stage.setScene(gameSelectionScene(stage));
                stage.show();
            }
        });
        HBox hbox = new HBox(label, textfield, button);
        return new Scene(hbox);
    }

    public Scene loginScene(Stage stage) {
        Label label = new Label("Name:");
        TextField textfield = new TextField();
        Button button = new Button("OK");
        button.setOnMouseClicked(event -> {
            System.out.println(textfield.getText());
            boolean success = userService.login(textfield.getText());
            if (success) {
                game.setPlayerOne(userService.getLoggedUser());
                stage.setScene(gameSelectionScene(stage));
            }
        });
        HBox hbox = new HBox(label, textfield, button);
        return new Scene(hbox);
    }

    public Scene gameSelectionScene(Stage stage) {
        StackPane stackpane = new StackPane();
        StackPane stackpane2 = new StackPane();

        Rectangle rect = new Rectangle(200, 200);
        Rectangle rect2 = new Rectangle(200, 200);

        rect.setFill(Color.DARKGREY);
        rect2.setFill(Color.DARKGREY);

        rect.setOnMouseClicked(event -> {
            game.setIsAgainstComputer(false);
            Scene setUpScene = setUpScene(stage);
            stage.setScene(setUpScene);
            stage.show();
        });

        rect2.setOnMouseClicked(event -> {
            game.setPlayerTwo(new User("Computer"));
            game.setIsAgainstComputer(true);
            Scene setUpScene = setUpScene(stage);
            stage.setScene(setUpScene);
            stage.show();
        });

        Text vsComputer = new Text("VS Computer");
        Text vsPlayer = new Text("VS another Player");
        stackpane.getChildren().addAll(rect, vsPlayer);
        stackpane2.getChildren().addAll(rect2, vsComputer);

        HBox hbox = new HBox(stackpane, stackpane2);
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);
        return new Scene(hbox);
    }

    public Scene setUpScene(Stage stage) {
        GridPane player1Grid = new GridPane();
        GridPane player2Grid = new GridPane();

        Square[][] player1Squares = game.getPlayer1Squares();
        Square[][] player2Squares = game.getPlayer2Squares();

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
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

                        if (game.player1ShipsIsEmpty()) {
                            game.clearButtonColors(1);
                        }
                        if (game.getIsAgainstComputer() && game.player1ShipsIsEmpty()) {
                            stage.setScene(playScene());
                            stage.show();
                        }
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

                player1Grid.add(player1Button, k, i);

                if (!game.getIsAgainstComputer()) {

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
                                    stage.setScene(playScene());
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
                } else {
                    game.getComputer().placeComputerShips();
                    game.clearButtonColors(2);
                }
                player2Grid.add(player2Button, k, i);
            }
        }
        Label player1Label = new Label(game.getPlayerOne().getName());
        player1Label.setPadding(new Insets(10, 0, 10, 0));

        Label player2Label = new Label(game.getPlayerTwo().getName());
        player2Label.setPadding(new Insets(10, 0, 10, 0));

        HBox player1Setup = getBoard(player1Grid);
        HBox player2Setup = getBoard(player2Grid);

        VBox player1Board = new VBox(player1Label, player1Setup);
        player1Board.setAlignment(Pos.CENTER);

        VBox player2Board = new VBox(player2Label, player2Setup);
        player2Board.setAlignment(Pos.CENTER);

        HBox setupHbox = new HBox(player1Board, player2Board);
        setupHbox.setAlignment(Pos.CENTER);
        setupHbox.setSpacing(30);

        Label tipLabel = new Label("Tip: click mouse 2 to change the ships direction");
        tipLabel.setPadding(new Insets(20, 0, 0, 0));

        VBox container = new VBox(setupHbox, tipLabel);
        container.setAlignment(Pos.TOP_CENTER);

        return new Scene(container, 800, 500);
    }

    public Scene playScene() {
        GridPane gridpane1 = new GridPane();
        GridPane gridpane2 = new GridPane();

        Square[][] player1Squares = game.getPlayer1Squares();
        Square[][] player2Squares = game.getPlayer2Squares();

        Label turnLabel = new Label(game.getPlayerOne().getName());
        turnLabel.setPadding(new Insets(20, 0, 0, 0));

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {

                Square square = player1Squares[i][k];
                Rectangle button = square.getButton();
                button.setOnMouseClicked(null);
                square.removeButtonColor();
                if (!game.getIsAgainstComputer()) {
                    button.setOnMouseClicked(event -> {
                        if (!game.isGameOver() && game.getTurn() == Turn.PLAYER2 && !square.getIsHit()) {
                            boolean hasShip = square.hitSquare();
                            if (!hasShip) {
                                game.changeTurn();
                                turnLabel.setText("TURN: " + game.getPlayerOne().getName());
                            }
                        }
                        if (game.allPlayer1ShipsDead()) {
                            game.setGameOver();
                            turnLabel.setText(game.getPlayerTwo().getName() + " WINS!");
                        }
                    });
                }

                gridpane1.add(button, k, i);

            }
        }

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                Square square = player2Squares[i][k];
                Rectangle button = square.getButton();

                square.removeButtonColor();
                button.setOnMouseClicked(null);

                button.setOnMouseClicked(event -> {
                    if (!game.isGameOver() && game.getTurn() == Turn.PLAYER1 && !square.getIsHit()) {
                        boolean hasShip = square.hitSquare();
                        if (!hasShip) {
                            game.changeTurn();
                            if (!game.getIsAgainstComputer()) {
                                turnLabel.setText("TURN: " + game.getPlayerTwo().getName());
                            } else {
                                game.getComputer().computersTurn();
                                turnLabel.setText("TURN: You");

                            }
                        }
                        if (game.getIsAgainstComputer() && game.allPlayer1ShipsDead()) {
                            game.setGameOver();
                            turnLabel.setText("COMPUTER WINS!");
                        }
                        if (game.allPlayer2ShipsDead()) {
                            game.setGameOver();
                            turnLabel.setText(game.getIsAgainstComputer() ? "YOU WIN!"
                                    : game.getPlayerOne().getName() + " WINS!");
                        }
                    }
                });
                gridpane2.add(button, k, i);
            }
        }

        Label player1Label = new Label(game.getIsAgainstComputer() ? "You" : game.getPlayerOne().getName());
        player1Label.setPadding(new Insets(10, 0, 10, 0));

        Label player2Label = new Label(game.getPlayerTwo().getName());
        player2Label.setPadding(new Insets(10, 0, 10, 0));

        HBox player1Setup = getBoard(gridpane1);
        HBox player2Setup = getBoard(gridpane2);

        VBox player1Board = new VBox(player1Label, player1Setup);
        player1Board.setAlignment(Pos.CENTER);

        VBox player2Board = new VBox(player2Label, player2Setup);
        player2Board.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(player1Board, player2Board);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);

        VBox container = new VBox(hbox, turnLabel);
        container.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(container, 800, 500);

        return scene;
    }

    public HBox getBoard(GridPane setupGrid) {
        GridPane p1XCoordinates = getXCoordinates();
        GridPane p1YCoordinates = getYCoordinates();
        p1YCoordinates.setAlignment(Pos.BOTTOM_RIGHT);

        VBox player1BoardWithX = new VBox(p1XCoordinates, setupGrid);
        player1BoardWithX.setAlignment(Pos.TOP_CENTER);

        return new HBox(p1YCoordinates, player1BoardWithX);
    }

    public GridPane getXCoordinates() {
        GridPane gridpane = new GridPane();
        String letters = "ABCDEFGHIJ";

        for (int i = 0; i < 10; i++) {
            Text text = new Text(Character.toString(letters.charAt(i)));
            text.setFill(Color.BLACK);
            Rectangle rect = new Rectangle(30, 30);
            rect.setStyle("-fx-stroke: whitesmoke; -fx-stroke-width: 1;");
            rect.setFill(Color.WHITESMOKE);
            StackPane pane = new StackPane();
            pane.getChildren().addAll(rect, text);
            gridpane.add(pane, i, 0);
        }

        return gridpane;
    }

    public GridPane getYCoordinates() {
        GridPane gridpane = new GridPane();

        for (int i = 1; i <= 10; i++) {
            Text text = new Text(Integer.toString(i));
            text.setFill(Color.BLACK);
            Rectangle rect = new Rectangle(30, 30);
            rect.setStyle("-fx-stroke: whitesmoke; -fx-stroke-width: 1;");
            rect.setFill(Color.WHITESMOKE);
            StackPane pane2 = new StackPane();
            pane2.getChildren().addAll(rect, text);
            gridpane.add(pane2, 0, i);
        }

        return gridpane;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
