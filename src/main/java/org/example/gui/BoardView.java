package org.example.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.example.abstraction.Board;


public class BoardView {
    private final GraphicsContext gc;
    private final boolean showShips;
    private final Board board;
    private final int gridSize;
    private final int cellSize;
    private final Canvas canvas;
    private final VBox container;
    private final Label titleLabel;

    public BoardView(Board board, int gridSize, int cellSize, boolean showShips, String title) {
        this.board = board;
        this.gridSize = gridSize;
        this.cellSize = cellSize;
        this.showShips = showShips;
        canvas = new Canvas(gridSize * cellSize, gridSize * cellSize);
        gc = canvas.getGraphicsContext2D();

        titleLabel = new Label(title);
        container = new VBox(10);
        container.getChildren().addAll(titleLabel, canvas);
    }

    public VBox getContainer() {
        return container;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void draw() {
        gc.clearRect(0, 0, gridSize * cellSize, gridSize * cellSize);
        gc.setStroke(Color.BLACK);

        int[][] playerBoard = board.toArray(showShips);
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                gc.strokeRect(col * cellSize, row * cellSize, cellSize, cellSize);
                if (playerBoard[row][col] > 0) {
                    gc.setFill(showShips ? Color.BLUE : Color.BLACK);
                    gc.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                }
                else if (playerBoard[row][col] == -1) {
                    drawX(row, col);
                }
            }
        }
    }

    private void drawX(int row, int col) {
        Paint lastStroke = gc.getStroke();
        gc.setStroke(Color.RED);
        gc.strokeLine(col * cellSize, row * cellSize, (col + 1) * cellSize, (row + 1) * cellSize);
        gc.strokeLine((col + 1) * cellSize, row * cellSize, col * cellSize, (row + 1) * cellSize);
        gc.setStroke(lastStroke);
    }
}
