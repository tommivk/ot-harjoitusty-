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
import com.github.javafaker.Faker;

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
        stackpane.setPickOnBounds(false);
        stackpane.setOnMouseClicked(event -> {
            stage.setScene(loginScene(stage));
            stage.show();
        });

        stackpane2.setPickOnBounds(false);
        stackpane2.setOnMouseClicked(event -> {
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
        Text yourStatsText = new Text("Your stats");
        yourStatsText.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Button goBackButton = new Button("Go back");
        goBackButton.setOnMouseClicked(event -> {
            stage.setScene(gameSelectionScene(stage));
            stage.show();
        });
        VBox usersStats = getPlayersStats(userService.getLoggedPlayerOne().getId());
        BorderPane pane = new BorderPane();

        VBox userStatsContainer = new VBox(yourStatsText, usersStats, goBackButton);
        VBox.setMargin(yourStatsText, new Insets(0, 0, 10, 0));

        Text computerText = new Text("Computer");
        computerText.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        VBox computersStats = getPlayersStats(1);
        VBox computerStatsContainer = new VBox(computerText, computersStats);
        VBox.setMargin(computerText, new Insets(0, 0, 10, 0));

        HBox statisticsTable = new HBox(userStatsContainer, computerStatsContainer);
        statisticsTable.setAlignment(Pos.TOP_CENTER);
        statisticsTable.setSpacing(50);

        TextField userSearch = new TextField();
        userSearch.setPromptText("Search by username");
        userSearch.setFocusTraversable(false);

        Button searchButton = new Button("Search");

        Text searchUserText = new Text("");
        searchUserText.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        VBox searchResult = new VBox();
        searchResult.setAlignment(Pos.CENTER);
        VBox.setMargin(searchResult, new Insets(10, 0, 0, 0));

        searchButton.setOnMouseClicked(event -> {
            User user = userService.getUser(userSearch.getText());
            if (user != null) {
                searchResult.getChildren().clear();
                VBox data = getPlayersStats(user.getId());

                data.setAlignment(Pos.CENTER);
                searchResult.getChildren().addAll(data);
                searchUserText.setText(user.getName());
            } else {
                Text errorMessage = new Text("User not found");
                searchUserText.setText("");
                searchResult.getChildren().clear();
                searchResult.getChildren().addAll(errorMessage);
            }
        });

        HBox searchField = new HBox(userSearch, searchButton);
        searchField.setAlignment(Pos.CENTER);

        VBox centerContent = new VBox(statisticsTable, searchField, searchUserText, searchResult);
        centerContent.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(statisticsTable, new Insets(20, 0, 0, 0));
        VBox.setMargin(searchField, new Insets(20, 20, 20, 20));
        pane.setCenter(centerContent);
        pane.setBottom(goBackButton);
        BorderPane.setMargin(statisticsTable, new Insets(30, 0, 0, 0));
        BorderPane.setMargin(goBackButton, new Insets(0, 0, 10, 10));

        HBox topContent = getTopContainer(stage, "Stats");
        pane.setTop(topContent);
        topContent.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(topContent, Pos.CENTER);

        return new Scene(pane);
    }

    public VBox getPlayersStats(int playerId) {
        int totalShots = gameService.getPlayerShotCount(playerId);
        int totalHits = gameService.getPlayerHitCount(playerId);
        int wins = gameService.getPlayerWinCount(playerId);
        int totalGames = gameService.getPlayerGameCount(playerId);
        Text totalShotsText = new Text("Total shots: " + Integer.toString(totalShots));
        Text totalHitsText = new Text("Total hits: " + Integer.toString(totalHits));
        Text winsText = new Text("Wins: " + Integer.toString(wins));
        Text gamesPlayedText = new Text("Games played: " + Integer.toString(totalGames));

        float hitPercentage;
        if (totalHits == 0 || totalShots == 0) {
            hitPercentage = 0;
        } else {
            hitPercentage = ((float) totalHits / totalShots) * 100;
        }

        float winPercentage;
        if (wins == 0 || totalGames == 0) {
            winPercentage = 0;
        } else {
            winPercentage = ((float) wins / totalGames) * 100;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        Text hitPercentageText = new Text("Hit percentage: " + df.format(hitPercentage) + "%");
        Text winPercentageText = new Text("Win percentage: " + df.format(winPercentage) + "%");
        return new VBox(gamesPlayedText, winsText, winPercentageText, totalShotsText, totalHitsText, hitPercentageText);
    }

    public Scene signUpScene(Stage stage) {
        Faker faker = new Faker();
        Text headerText = new Text(10, 20, "Add new user");
        headerText.setStyle("-fx-font-size: 24; -fx-font-weight: bolder;");
        Label label = new Label("Name:");
        TextField textfield = new TextField();
        Button button = new Button("Add");
        Text errorMessage = new Text("");
        button.setOnMouseClicked(event -> {
            boolean success = userService.createUser(textfield.getText());
            userService.getAllUsers();
            if (success) {
                errorMessage.setText("");
                if (userService.getLoggedPlayerOne() == null) {
                    stage.setScene(loginScene(stage));
                    stage.show();
                } else {
                    stage.setScene(gameSelectionScene(stage));
                    stage.show();
                }
            } else {
                errorMessage.setText("Username is taken");
            }
        });

        Button randomButton = new Button("Generate random name");
        randomButton.setOnMouseClicked(event -> {
            textfield.setText("");
            String randomName = faker.name().firstName();
            textfield.setText(randomName);
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

        VBox vbox = new VBox(hbox, errorMessage, randomButton);
        vbox.setSpacing(20.0);
        vbox.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.setTop(headerText);
        pane.setCenter(vbox);
        BorderPane.setAlignment(headerText, Pos.CENTER);
        BorderPane.setMargin(headerText, new Insets(20, 0, 0, 0));
        BorderPane.setMargin(vbox, new Insets(0, 0, 50, 0));

        pane.setBottom(goBackButton);
        BorderPane.setMargin(goBackButton, new Insets(0, 0, 10, 10));
        return new Scene(pane);
    }

    public Scene loginScene(Stage stage) {
        Text headerText = new Text(10, 20, "Login");
        headerText.setStyle("-fx-font-size: 24; -fx-font-weight: bolder;");
        Label label = new Label("Name:");
        TextField textfield = new TextField();
        Button button = new Button("Login");
        Text errorMessage = new Text("");
        button.setOnMouseClicked(event -> {
            boolean success = userService.playerOneLogin(textfield.getText());
            if (success) {
                errorMessage.setText("");
                stage.setScene(gameSelectionScene(stage));
            } else {
                errorMessage.setText("User not found");
            }
        });
        Button goBackButton = new Button("Go back");
        goBackButton.setOnMouseClicked(event -> {
            stage.setScene(startScene(stage));
            stage.show();
        });
        HBox hbox = new HBox(label, textfield, button);
        hbox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(hbox, errorMessage);
        vbox.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.setTop(headerText);
        pane.setCenter(vbox);
        BorderPane.setAlignment(headerText, Pos.CENTER);
        BorderPane.setMargin(headerText, new Insets(20, 0, 0, 0));
        BorderPane.setMargin(vbox, new Insets(0, 0, 50, 0));
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
        Text errorMessage = new Text("");
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
            } else {
                errorMessage.setText("User not found");
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
        VBox vbox = new VBox(hbox, errorMessage);
        vbox.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.setTop(headerText);
        VBox centerContent = new VBox(vbox, newUserButton);
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

        StackPane stackpane = new StackPane();
        StackPane stackpane2 = new StackPane();
        StackPane statsStackpane = new StackPane();
        Rectangle rect = new Rectangle(200, 200);
        Rectangle rect2 = new Rectangle(200, 200);
        Rectangle statsRect = new Rectangle(430, 100);

        rect.setFill(Color.DIMGRAY);
        rect2.setFill(Color.DIMGRAY);
        statsRect.setFill(Color.DIMGRAY);

        stackpane.setPickOnBounds(false);
        stackpane.setOnMouseClicked(event -> {
            stage.setScene(playerTwoLoginScene(stage));
            stage.show();
        });

        stackpane2.setPickOnBounds(false);
        stackpane2.setOnMouseClicked(event -> {

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
        vsComputer.setFill(Color.WHITESMOKE);
        vsComputer.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Text vsPlayer = new Text("VS Another Player");
        vsPlayer.setFill(Color.WHITESMOKE);
        vsPlayer.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Text showStatistics = new Text("Show Statistics");
        showStatistics.setFill(Color.WHITESMOKE);
        showStatistics.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        stackpane.getChildren().addAll(rect, vsPlayer);
        stackpane2.getChildren().addAll(rect2, vsComputer);
        statsStackpane.getChildren().addAll(statsRect, showStatistics);

        statsStackpane.setPickOnBounds(false);
        statsStackpane.setOnMouseClicked(event -> {
            stage.setScene(statisticsScene(stage));
            stage.show();
        });
        HBox hbox = new HBox(stackpane, stackpane2);
        VBox vbox = new VBox(hbox, statsStackpane);
        VBox.setMargin(statsStackpane, new Insets(30, 0, 0, 0));
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(vbox);

        HBox loggedInContainer = getTopContainer(stage, "Play Battleships");

        VBox topContent = new VBox(loggedInContainer);
        pane.setTop(topContent);
        topContent.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(topContent, Pos.CENTER);

        return new Scene(pane);
    }

    public HBox getTopContainer(Stage stage, String headerText) {
        Text title = new Text(headerText);
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bolder;");

        Text loggedInText = new Text("Logged in as " + userService.getLoggedPlayerOne().getName());
        Button logOutButton = new Button("logout");
        logOutButton.setOnMouseClicked(event -> {
            userService.logout();
            stage.setScene(startScene(stage));
            stage.show();
        });
        Region spacer = new Region();
        Region spacerTwo = new Region();
        StackPane leftBox = new StackPane();
        leftBox.setPrefWidth(200);
        leftBox.getChildren().addAll(loggedInText);
        StackPane.setAlignment(loggedInText, Pos.TOP_LEFT);
        StackPane rightBox = new StackPane();
        rightBox.setPrefWidth(200);
        rightBox.getChildren().addAll(logOutButton);
        StackPane.setAlignment(logOutButton, Pos.TOP_RIGHT);

        HBox hbox = new HBox(leftBox, spacer, title, spacerTwo, rightBox);
        HBox.setMargin(leftBox, new Insets(10, 10, 0, 10));
        HBox.setMargin(rightBox, new Insets(10, 10, 0, 10));
        HBox.setMargin(title, new Insets(10, 10, 0, 10));
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacerTwo, Priority.ALWAYS);

        return hbox;
    }

    public Scene setUpScene(Stage stage) {
        GridPane playerOneGrid = new GridPane();
        GridPane playerTwoGrid = new GridPane();

        Square[][] playerOneSquares = game.getPlayerOneSquares();
        Square[][] playerTwoSquares = game.getPlayerTwoSquares();

        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                int row = i;
                int column = k;

                Rectangle playerOneButton = playerOneSquares[i][k].getButton();
                Rectangle playerTwoButton = playerTwoSquares[i][k].getButton();

                playerOneButton.setOnMouseEntered(event -> {
                    game.highlightSquares(row, column, 1);
                });

                playerOneButton.setOnMouseExited(event -> {
                    game.removeButtonImage(row, column, 1);
                });

                playerOneButton.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        game.placeShip(row, column, 1);

                        if (game.playerOneShipsIsEmpty()) {
                            game.clearButtonColors(1);
                        }
                        if (game.getIsAgainstComputer() && game.playerOneShipsIsEmpty()) {
                            stage.setScene(playScene(stage));
                            stage.show();
                        }
                    }

                    if (event.getButton() == MouseButton.SECONDARY) {
                        for (int m = 0; m < 10; m++) {
                            for (int n = 0; n < 10; n++) {
                                if (!playerOneSquares[n][m].hasShip()) {
                                    playerOneSquares[n][m].removeButtonImage();
                                }
                            }
                        }
                        game.changeShipDirection();
                        game.highlightSquares(row, column, 1);
                    }

                });

                playerOneGrid.add(playerOneButton, k, i);

                if (!game.getIsAgainstComputer()) {

                    playerTwoButton.setOnMouseEntered(event -> {
                        if (game.playerOneShipsIsEmpty()) {
                            game.highlightSquares(row, column, 2);
                        }
                    });

                    playerTwoButton.setOnMouseExited(event -> {
                        game.removeButtonImage(row, column, 2);
                    });

                    playerTwoButton.setOnMouseClicked(event -> {
                        if (game.playerOneShipsIsEmpty()) {
                            if (event.getButton() == MouseButton.PRIMARY) {
                                game.placeShip(row, column, 2);

                                if (game.playerTwoShipsIsEmpty()) {
                                    stage.setScene(playScene(stage));
                                    stage.show();
                                }
                            }

                            if (event.getButton() == MouseButton.SECONDARY) {
                                for (int m = 0; m < 10; m++) {
                                    for (int n = 0; n < 10; n++) {
                                        if (!playerTwoSquares[n][m].hasShip()) {
                                            playerTwoSquares[n][m].removeButtonImage();
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
                playerTwoGrid.add(playerTwoButton, k, i);
            }
        }
        Label player1Label = new Label(game.getPlayerOne().getName());
        player1Label.setPadding(new Insets(10, 0, 10, 0));

        Label player2Label = new Label(game.getPlayerTwo().getName());
        player2Label.setPadding(new Insets(10, 0, 10, 0));

        HBox player1Setup = getBoard(playerOneGrid);
        HBox player2Setup = getBoard(playerTwoGrid);

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

        Square[][] playerOneSquares = game.getPlayerOneSquares();
        Square[][] playerTwoSquares = game.getPlayerTwoSquares();

        Label turnLabel = new Label(
                game.getIsAgainstComputer() ? "It's your turn" : "TURN: " + game.getPlayerOne().getName());
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

                Square square = playerOneSquares[i][k];
                Rectangle button = square.getButton();
                button.setOnMouseClicked(null);
                square.removeButtonImage();
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
                        if (game.allPlayerOneShipsDead()) {
                            game.setGameOver();
                            gameService.setWinner(game.getPlayerTwo().getId());
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
                Square square = playerTwoSquares[i][k];
                Rectangle button = square.getButton();

                square.removeButtonImage();
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
                                game.getComputer().computersTurn(gameService);
                            }
                        } else {
                            gameService.addPlayerOneHit();
                        }
                        if (game.getIsAgainstComputer() && game.allPlayerOneShipsDead()) {
                            game.setGameOver();
                            gameService.setWinner(1);
                            turnLabel.setText("COMPUTER WINS!");
                            newGame.setVisible(true);
                        }
                        if (game.allPlayerTwoShipsDead()) {
                            game.setGameOver();
                            gameService.setWinner(game.getPlayerOne().getId());
                            turnLabel.setText(game.getIsAgainstComputer() ? "YOU WIN!"
                                    : game.getPlayerOne().getName() + " WINS!");
                            newGame.setVisible(true);
                        }
                    }
                });
                gridpane2.add(button, k, i);
            }
        }

        Label player1Label = new Label(game.getPlayerOne().getName());
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
