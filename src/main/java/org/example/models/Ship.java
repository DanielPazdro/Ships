package org.example.models;

import java.io.Serializable;
import java.util.Arrays;

public class Ship implements Serializable {
    private Square[] squares;

    private Ship() {

    }

    public Square[] getSquares() {
        return squares;
    }

    public boolean isSunken() {
        return Arrays.stream(squares).allMatch(Square::isMarked);
    }

    public int getSize() {
        return squares.length;
    }

    public static Ship of(int startRow, int startCol, int size, Orientation orientation) {
        Ship ship = new Ship();
        ship.squares = new Square[size];
        switch (orientation) {
            case VERTICAL -> {
                for(int i = 0; i < size; i++) {
                    ship.squares[i] = new Square
                            .Builder(startRow + i, startCol)
                            .marked(false)
                            .build();
                }
            }
            case HORIZONTAL -> {
                for(int i = 0; i < size; i++) {
                    ship.squares[i] = new Square
                            .Builder(startRow, startCol + i)
                            .marked(false)
                            .build();
                }
            }
        }
        return ship;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "squares=" + Arrays.toString(squares) +
                '}';
    }
}
