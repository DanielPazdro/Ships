package org.example.models;

import java.io.Serializable;

public class Position implements Serializable {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Position getLeft() {
        return Position.of(row, col- 1);
    }

    public Position getRight() {
        return Position.of(row, col + 1);
    }

    public Position getUp() {
        return Position.of(row-1, col);
    }

    public Position getDown() {
        return Position.of(row+1, col);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position p) {
            return p.col == col && p.row == row;
        }
        return super.equals(obj);
    }

    public static Position of(int row, int col) {
        return new Position(row, col);
    }
}
