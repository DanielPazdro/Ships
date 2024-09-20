package org.example.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.abstraction.Game;

import java.io.File;


public class ShipsGameScene {
    private static final int GRID_SIZE = 10;
    private static final int CELL_SIZE = 40;

    private final BoardView playerBoardView;
    private final BoardView computerBoardView;

    private final Game game;

    public ShipsGameScene(Stage primaryStage, Game game) {
        this.game = game;

        playerBoardView = new BoardView(game.getBoard(0), GRID_SIZE, CELL_SIZE, false, "Your ships");
        playerBoardView.draw();

        computerBoardView = new BoardView(game.getBoard(1), GRID_SIZE, CELL_SIZE, false, "Enemy's Ships");
        computerBoardView.draw();

        HBox boardsContainer = new HBox(40, playerBoardView.getContainer(), computerBoardView.getContainer());
        boardsContainer.setAlignment(javafx.geometry.Pos.CENTER);

        Button saveGameButton = new Button("Save game");
        saveGameButton.setOnAction(e -> {
            File file = FileChooserBox.of("Choose place to save game", true).show(primaryStage);
            if(file != null) {
                game.saveGame(file.getAbsolutePath());
                MessageBox.of("Success", "Game saved!").show();
            }
        });

        Button revealButton = new Button("Reveal");
        revealButton.setOnAction(e -> {
            game.reveal(1);
            revealButton.setVisible(false);
            computerBoardView.draw();
        });

        VBox root = new VBox(20, boardsContainer, revealButton, saveGameButton);
        root.setAlignment(javafx.geometry.Pos.CENTER);

        Scene gameScene = new Scene(root, 900, 600);

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Battleship - Game");
        primaryStage.show();

        computerBoardView.getCanvas().addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleComputerBoardClick);

        if(!game.playerCanReveal(1)) {
            revealButton.setVisible(false);
        }
    }

    private void handleComputerBoardClick(MouseEvent e) {
        int col = (int) (e.getX() / CELL_SIZE);
        int row = (int) (e.getY() / CELL_SIZE);

        if (e.getButton() == MouseButton.PRIMARY) {
            game.mark(1, row, col);
        } else if (e.getButton() == MouseButton.SECONDARY) {
            game.markSpecial(1, row, col);
        }
        computerBoardView.draw();

        game.mark(0);
        playerBoardView.draw();

        int winner = game.getWinner();
        switch (winner) {
            case 0 -> MessageBox.of("Game over", "You win!").show();
            case 1 -> MessageBox.of("Game over", "You lose!").show();
        }
    }
}
