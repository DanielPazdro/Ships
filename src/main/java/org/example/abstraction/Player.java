package org.example.abstraction;

public interface Player {
    Board getBoard();
    boolean mark(int row, int col, boolean useSpecial);
    void reveal();
    int getRevealCount();
}
