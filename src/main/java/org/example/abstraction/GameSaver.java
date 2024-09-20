package org.example.abstraction;

import org.example.impl.GameState;

public interface GameSaver {
    void saveGame(GameState gameState, String filename);
}
