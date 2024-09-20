package org.example.models;

import java.io.Serializable;
import java.util.Objects;

public class Square implements Serializable {
    private final int row;
    private final int col;
    private boolean marked;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    private Square(Builder builder) {
        this.row = builder.row;
        this.col = builder.col;
        this.marked = builder.marked;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isMarked() {
        return marked;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Square s) {
            return s.col == col && s.row == row;
        }
        return false;
    }

    public boolean equals(int row, int col) {
        return this.col == col && this.row == row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Square{" +
                "row=" + row +
                ", col=" + col +
                ", marked=" + marked +
                '}';
    }

    public static class Builder {
        private final int row;
        private final int col;
        private boolean marked = false;

        public Builder(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Builder marked(boolean marked) {
            this.marked = marked;
            return this;
        }

        public Square build() {
            return new Square(this);
        }
    }
}
