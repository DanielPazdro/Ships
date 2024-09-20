package org.example.abstraction;

import org.example.models.Ship;
import org.example.models.Square;

public interface Board {
    boolean mark(int row, int col);
    int[][] toArray(boolean ships);
    boolean gameOver();
    void setShipsRandomly();
    Player getPlayer();
    boolean addShip(Ship ship);
    int getSize();
    void randomReveal();
}
