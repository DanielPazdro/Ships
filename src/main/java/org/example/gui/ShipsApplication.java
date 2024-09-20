package org.example.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.abstraction.Game;
import org.example.impl.GameImpl;
import org.example.impl.GameLoaderImpl;
import org.example.impl.GameSaverImpl;
import org.example.models.Orientation;

import java.io.File;


public class ShipsApplication extends Application {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 40;
    private boolean isVertical = true;

    private int currentRow = -1;
    private int currentCol = -1;

    private final int[] shipsPlacedSize = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    private int shipsPlaced = 0;
    private Button startGameButton;
    private Button placeButton;

    private Game game;
    private BoardView boardView;

    @Override
    public void start(Stage primaryStage) {

        game = new GameImpl(new GameSaverImpl(), new GameLoaderImpl());

        boardView = new BoardView(game.getBoard(0), GRID_SIZE, CELL_SIZE, true, "Place ships here, use right mouse button to rotate");
        boardView.draw();

        placeButton = new Button("Place ship");
        placeButton.setOnAction(e -> confirmShipPlacement());

        startGameButton = new Button("Start game");
        startGameButton.setVisible(false);
        startGameButton.setOnAction(e -> startGame(primaryStage));

        Button loadGameButton = new Button("Load game");
        loadGameButton.setOnAction(e -> loadGame(primaryStage));

        VBox root = new VBox(20, boardView.getContainer(), placeButton, loadGameButton, startGameButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        boardView.getCanvas().addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTemporaryShipPlacement);

        Scene scene = new Scene(root, 430, 600);
        primaryStage.setTitle("Ships Game - Ship Placement");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadGame(Stage primaryStage) {
        File file = FileChooserBox.of("Select saved game", false).show(primaryStage);
        if(file == null) {
            return;
        }

        boolean success = game.loadGame(file.getAbsolutePath());
        if(success) {
            startGame(primaryStage);
        } else {
            MessageBox.of("Error", "Invalid save file").show();
        }
    }

    private void handleTemporaryShipPlacement(MouseEvent e) {
        int col = (int) (e.getX() / CELL_SIZE);
        int row = (int) (e.getY() / CELL_SIZE);

        if (e.getButton() == MouseButton.SECONDARY) {
            isVertical = !isVertical;
        }

        boardView.draw();

        if (canPlaceShip(row, col)) {
            drawTemporaryShip(row, col);
            currentRow = row;
            currentCol = col;
        } else {
            MessageBox.of("Error", "Can't place ship.").show();
            currentRow = -1;
            currentCol = -1;
        }
    }

    private void drawTemporaryShip(int row, int col) {
        if(shipsPlaced >= shipsPlacedSize.length) {
            return;
        }

        var gc = boardView.getGc();
        gc.setFill(Color.LIGHTGREEN);
        for (int i = 0; i < shipsPlacedSize[shipsPlaced]; i++) {
            if (isVertical) {
                gc.fillRect(col * CELL_SIZE, (row + i) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            } else {
                gc.fillRect((col + i) * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private void confirmShipPlacement() {
        if (currentRow == -1 || currentCol == -1) {
            MessageBox.of("Error", "Can't place ship.").show();
            return;
        }

        boolean success = game.addShip(0, currentRow, currentCol, shipsPlacedSize[shipsPlaced],
                isVertical ? Orientation.VERTICAL : Orientation.HORIZONTAL);
        if(!success) {
            MessageBox.of("Error", "Can't place ship.").show();
            return;
        }

        boardView.draw();

        currentRow = -1;
        currentCol = -1;
        shipsPlaced++;

        if(shipsPlaced >= shipsPlacedSize.length)
        {
            MessageBox.of("Success", "All ships are placed.").show();
            startGameButton.setVisible(true);
            placeButton.setVisible(false);
        }
    }

    private boolean canPlaceShip(int row, int col) {
        if(shipsPlaced >= shipsPlacedSize.length) {
            return false;
        }

        if (isVertical) {
            return row + shipsPlacedSize[shipsPlaced] <= GRID_SIZE;
        } else {
            return col + shipsPlacedSize[shipsPlaced] <= GRID_SIZE;
        }
    }

    private void startGame(Stage primaryStage) {
        new ShipsGameScene(primaryStage, game);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
