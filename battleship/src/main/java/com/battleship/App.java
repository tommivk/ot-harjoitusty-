package com.battleship;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Battleship");
        stage.setHeight(500);
        stage.setWidth(800);

        Game game = new Game();

        GridPane gridpane1 = new GridPane();
        GridPane gridpane2 = new GridPane();

        HBox hbox = new HBox(gridpane1, gridpane2);
        hbox.setSpacing(30);
        Scene scene = new Scene(hbox, 500, 800);

        Square[] squares = new Square[5];

        for (int i = 0; i < 5; i++) {
            Square square = new Square();
            squares[i] = square;
        }

        Ship ship = new Ship(3);
        squares[0].addShip(ship);
        squares[1].addShip(ship);
        squares[2].addShip(ship);

        for (int i = 0; i < 5; i++) {
            Square square = squares[i];
            Button button = new Button(" ");
            button.setOnMouseClicked(event -> {
                boolean hit = square.hitSquare();
                if (hit) {
                    button.setText("X");
                    if (square.getShip().isDead()) {
                        square.getShip().setButtonsDisabled();
                    }
                } else {
                    button.setText("O");
                    game.changeTurn();
                }
            });
            if (square.hasShip()) {
                square.getShip().addButton(button);
            }

            gridpane1.add(button, i, 0);
        }

        stage.setScene(scene);
        stage.show();
    };

    public static void main(String[] args) {
        Application.launch(args);
    }
}
