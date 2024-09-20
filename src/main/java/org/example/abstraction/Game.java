package org.example.abstraction;

import org.example.models.Orientation;

public interface Game {
    boolean addShip(int playerNo, int row, int col, int size, Orientation orientation);
    boolean mark(int playerNo, Integer row, Integer col);
    boolean mark(int playerNo);
    boolean markSpecial(int playerNo, Integer row, Integer col);
    int getWinner();
    int[][] boardToArray(int playerNo, boolean ships);
    boolean loadGame(String filename);
    void saveGame(String filename);
    void reveal(int playerNo);
    Board getBoard(int playerNo);
    boolean playerCanReveal(int playerNo);
}
