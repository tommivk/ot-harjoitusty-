package com.battleship.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.input.MouseButton;

import java.text.DecimalFormat;

import com.battleship.dao.DBGameDao;
import com.battleship.dao.DBUserDao;
import com.battleship.domain.Game;
import com.battleship.domain.GameService;
import com.battleship.domain.Square;
import com.battleship.domain.Turn;
import com.battleship.domain.User;
import com.battleship.domain.UserService;

public class BattleshipUi extends Application {
    private UserService userService;
    private GameService gameService;
    private Game game;

    @Override
    public void init() throws Exception {
        DBUserDao dbUserDao = new DBUserDao();
        userService = new UserService(dbUserDao, "jdbc:sqlite:data.db");
        DBGameDao dbGameDao = new DBGameDao();
        gameService = new GameService(dbGameDao, "jdbc:sqlite:data.db");
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Battleship");
        stage.setHeight(500);
        stage.setWidth(800);

        stage.setScene(startScene(stage));
        stage.show();
    }

    public Scene startScene(Stage stage) {
        StackPane stackpane = new StackPane();
        StackPane stackpane2 = new StackPane();

        Rectangle loginRect = new Rectangle(200, 200);
        Rectangle signUpRect = new Rectangle(200, 200);

        loginRect.setFill(Color.DIMGRAY);
        signUpRect.setFill(Color.DIMGRAY);

        loginRect.setOnMouseClicked(event -> {
            stage.setScene(loginScene(stage));
            stage.show();
        });

        signUpRect.setOnMouseClicked(event -> {
            stage.setScene(signUpScene(stage));
        });

        Text loginText = new Text("Login");
        loginText.setFill(Color.WHITESMOKE);
        loginText.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        Text newUserText = new Text("New user");
        newUserText.setFill(Color.WHITESMOKE);
        newUserText.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        stackpane.getChildren().addAll(loginRect, loginText);
        stackpane2.getChildren().addAll(signUpRect, newUserText);

        HBox hbox = new HBox(stackpane, stackpane2);
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);
        return new Scene(hbox);
    }

    public Scene statisticsScene(Stage stage) {
        Text headerText = new Text(10, 20, "Stats");
        Text loggedInText = new Text("Logged in as " + userService.getLoggedPlayerOne().getName());
        headerText.setStyle("-fx-font-size: 24; -fx-font-weight: bolder;");
        User player = userService.getLoggedPlayerOne();
        Text name = new Text(player.getName());
        int totalShots = gameService.getPlayerShotCount(player.getId());
        int totalHits = gameService.getPlayerHitCount(player.getId());

        Text totalShotsText = new Text("Total shots: " + Integer.toString(totalShots));
        Text totalHitsText = new Text("Total hits: " + Integer.toString(totalHits));

        float hitPercentage = ((float) totalHits / totalShots) * 100;
        System.out.println(hitPercentage);
        DecimalFormat df = new DecimalFormat("#.##");
        Text hitPercentageText = new Text("Hit %: " + df.format(hitPercentage));
        Button goBackButton = new Button("Go back");
        goBackButton.setOnMouseClicked(event -> {
            stage.setScene(gameSelectionScene(stage));
            stage.show();
        });
        BorderPane pane = new BorderPane();
        VBox vbox = new VBox(name, totalShotsText, totalHitsText, hitPercentageText, goBackButton);
        pane.setCenter(vbox);
        pane.setBottom(goBackButton);
        BorderPane.setMargin(goBackButton, new Insets(0, 0, 10, 10));

        Region spacer = new Region();

        HBox loggedInContainer = new HBox(loggedInText, spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox topContent = new VBox(loggedInContainer, headerText);
        pane.setTop(topContent);
        topContent.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(topContent, Pos.CENTER);

        return new Scene(pane);
    }

    public Scene signUpScene(Stage stage) {
        Text headerText = new Text(10, 20, "Add new user");
        headerText.setStyle("-fx-font-size: 24; -fx-font-weight: bolder;");
        Label label = new Label("Name:");
        TextField textfield = new TextField();
        Button button = new Button("Add");
        button.setOnMouseClicked(event -> {
            boolean success = userService.createUser(textfield.getText());
            userService.getAllUsers();
            if (success) {
                if (userService.getLoggedPlayerOne() == null) {
                    stage.setScene(loginScene(stage));
                    stage.show();
                } else {
                    stage.setScene(gameSelectionScene(stage));
                    stage.show();
                }
            }
        });
        Button goBackButton = new Button("Go back");
        goBackButton.setOnMouseClicked(event -> {
            if (userService.getLoggedPlayerOne() == null) {
                stage.setScene(startScene(stage));
                stage.show();
            } else {
                stage.setScene(gameSelectionScene(stage));
                stage.show();
            }
        });
        HBox hbox = new HBox(label, textfield, button);
        hbox.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setTop(headerText);
        pane.setCenter(hbox);
        BorderPane.setAlignment(headerText, Pos.CENTER);
        BorderPane.setMargin(headerText, new Insets(20, 0, 0, 0));
        BorderPane.setMargin(hbox, new Insets(0, 0, 50, 0));

        pane.setBottom(goBackButton);
        BorderPane.setMargin(goBackButton, new Insets(0, 0, 10, 10));
        return new Scene(pane);
    }

    public Scene loginScene(Stage stage) {
        Text headerText = new Text(10, 20, "Login");
        headerText.setStyle("-fx-font-size: 24; -fx-font-weight: bolder;");
        Label label = new Label("Name:");
        TextField textfield = new TextField();
        Button button = new Button("OK");
        button.setOnMouseClicked(event -> {
            System.out.println(textfield.getText());
            boolean success = userService.playerOneLogin(textfield.getText());
            if (success) {
                stage.setScene(gameSelectionScene(stage));
            }
        });
        Button goBackButton = new Button("Go back");
        goBackButton.setOnMouseClicked(event -> {
            stage.setScene(startScene(stage));
            stage.show();
        });
        HBox hbox = new HBox(label, textfield, button);
        hbox.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setTop(headerText);
        pane.setCenter(hbox);
        BorderPane.setAlignment(headerText, Pos.CENTER);
        BorderPane.setMargin(headerText, new Insets(20, 0, 0, 0));
        BorderPane.setMargin(hbox, new Insets(0, 0, 50, 0));
        pane.setBottom(goBackButton);
        BorderPane.setMargin(goBackButton, new Insets(0, 0, 10, 10));
        return new Scene(pane);
    }

    public Scene playerTwoLoginScene(Stage stage) {
        Text headerText = new Text(10, 20, "Player 2 Login");
        headerText.setStyle("-fx-font-size: 24; -fx-font-weight: bolder;");
        Label label = new Label("Name:");
        TextField textfield = new TextField();
        Button button = new Button("Login");
        button.setOnMouseClicked(event -> {
            boolean success = userService.playerTwoLogin(textfield.getText());
            if (success) {
                User playerOne = userService.getLoggedPlayerOne();
                User playerTwo = userService.getLoggedPlayerTwo();

                Boolean gameCreated = gameService.createGame(playerOne, playerTwo);

                if (gameCreated) {
                    game = gameService.getGame();
                    game.setIsAgainstComputer(false);
                    Scene setUpScene = setUpScene(stage);
                    stage.setScene(setUpScene);
                    stage.show();
                }
            }
        });
        Button goBackButton = new Button("Go back");
        goBackButton.setOnMouseClicked(event -> {
            stage.setScene(gameSelectionScene(stage));
            stage.show();
        });

        Button newUserButton = new Button("Create new user");
        newUserButton.setOnMouseClicked(event -> {
            stage.setScene(signUpScene(stage));
            stage.show();
        });
        HBox hbox = new HBox(label, textfield, button);
        hbox.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.setTop(headerText);
        VBox centerContent = new VBox(hbox, newUserButton);
        centerContent.setAlignment(Pos.CENTER);
        VBox.setMargin(newUserButton, new Insets(120, 0, 0, 0));
        pane.setCenter(centerContent);
        BorderPane.setAlignment(headerText, Pos.CENTER);
        BorderPane.setMargin(headerText, new Insets(20, 0, 0, 0));
        BorderPane.setMargin(centerContent, new Insets(140, 0, 0, 0));
        pane.setBottom(goBackButton);
        BorderPane.setMargin(goBackButton, new Insets(0, 0, 10, 10));
        return new Scene(pane);
    }

    public Scene gameSelectionScene(Stage stage) {
        Text headerText = new Text(10, 20, "Play Battleships");
        headerText.setStyle("-fx-font-size: 24; -fx-font-weight: bolder;");
        Text loggedInText = new Text("Logged in as " + userService.getLoggedPlayerOne().getName());
        StackPane stackpane = new StackPane();
        StackPane stackpane2 = new StackPane();

        Rectangle rect = new Rectangle(200, 200);
        Rectangle rect2 = new Rectangle(200, 200);

        rect.setFill(Color.DARKGREY);
        rect2.setFill(Color.DARKGREY);

        rect.setOnMouseClicked(event -> {
            stage.setScene(playerTwoLoginScene(stage));
            stage.show();
        });

        rect2.setOnMouseClicked(event -> {

            Boolean gameCreated = gameService.createGame(userService.getLoggedPlayerOne(), new User("Computer", 1));
            if (gameCreated) {
                game = gameService.getGame();
                game.setIsAgainstComputer(true);
                Scene setUpScene = setUpScene(stage);
                stage.setScene(setUpScene);
                stage.show();
            }
        });

        Text vsComputer = new Text("VS Computer");
        Text vsPlayer = new Text("VS another Player");
        stackpane.getChildren().addAll(rect, vsPlayer);
        stackpane2.getChildren().addAll(rect2, vsComputer);

        Button showStats = new Button("Stats");
        showStats.setOnMouseClicked(event -> {
            stage.setScene(statisticsScene(stage));
            stage.show();
        });
        HBox hbox = new HBox(stackpane, stackpane2);
        VBox vbox = new VBox(hbox, showStats);
        VBox.setMargin(showStats, new Insets(50, 0, 0, 0));
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(vbox);

        Region spacer = new Region();

        HBox loggedInContainer = new HBox(loggedInText, spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        VBox topContent = new VBox(loggedInContainer, headerText);
        pane.setTop(topContent);
        topContent.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(topContent, Pos.CENTER);
        BorderPane.setMargin(headerText, new Insets(20, 0, 0, 0));

        return new Scene(pane);
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
                            stage.setScene(playScene(stage));
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
                                    stage.setScene(playScene(stage));
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

        Button quitButton = new Button("Quit");
        quitButton.setOnMouseClicked(event -> {
            stage.setScene(gameSelectionScene(stage));
            stage.show();
        });

        BorderPane pane = new BorderPane();
        pane.setCenter(container);
        pane.setBottom(quitButton);

        BorderPane.setMargin(quitButton, new Insets(0, 0, 10, 10));

        return new Scene(pane, 800, 500);
    }

    public Scene playScene(Stage stage) {
        GridPane gridpane1 = new GridPane();
        GridPane gridpane2 = new GridPane();

        Square[][] player1Squares = game.getPlayer1Squares();
        Square[][] player2Squares = game.getPlayer2Squares();

        Label turnLabel = new Label("TURN: " + (game.getIsAgainstComputer() ? "You" : game.getPlayerOne().getName()));
        turnLabel.setPadding(new Insets(20, 0, 0, 0));

        Button quitButton = new Button("Quit");
        quitButton.setOnMouseClicked(event -> {
            stage.setScene(gameSelectionScene(stage));
            stage.show();
        });

        Button newGame = new Button("New game");
        newGame.setOnMouseClicked(event -> {
            boolean success = gameService.createGame(game.getPlayerOne(), game.getPlayerTwo());
            boolean isAgainstComputer = game.getIsAgainstComputer();
            if (success) {
                game = gameService.getGame();
                if (isAgainstComputer) {
                    game.setIsAgainstComputer(true);
                }
                stage.setScene(setUpScene(stage));
                stage.show();
            }

        });
        newGame.setVisible(false);

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
                            gameService.addPlayerTwoShot();
                            if (!hasShip) {
                                game.changeTurn();
                                turnLabel.setText("TURN: " + game.getPlayerOne().getName());
                            } else {
                                gameService.addPlayerTwoHit();
                            }
                        }
                        if (game.allPlayer1ShipsDead()) {
                            game.setGameOver();
                            turnLabel.setText(game.getPlayerTwo().getName() + " WINS!");
                            newGame.setVisible(true);
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
                        gameService.addPlayerOneShot();

                        if (!hasShip) {
                            game.changeTurn();
                            if (!game.getIsAgainstComputer()) {
                                turnLabel.setText("TURN: " + game.getPlayerTwo().getName());
                            } else {
                                game.getComputer().computersTurn();
                                turnLabel.setText("TURN: You");

                            }
                        } else {
                            gameService.addPlayerOneHit();
                        }
                        if (game.getIsAgainstComputer() && game.allPlayer1ShipsDead()) {
                            game.setGameOver();
                            turnLabel.setText("COMPUTER WINS!");
                            newGame.setVisible(true);
                        }
                        if (game.allPlayer2ShipsDead()) {
                            game.setGameOver();
                            turnLabel.setText(game.getIsAgainstComputer() ? "YOU WIN!"
                                    : game.getPlayerOne().getName() + " WINS!");
                            newGame.setVisible(true);
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

        VBox container = new VBox(hbox, turnLabel, newGame);
        container.setAlignment(Pos.TOP_CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(container);
        pane.setBottom(quitButton);

        BorderPane.setMargin(quitButton, new Insets(0, 0, 10, 10));

        Scene scene = new Scene(pane, 800, 500);

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
