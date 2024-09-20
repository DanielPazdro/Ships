package org.example.abstraction;

import org.example.impl.GameState;

public interface GameLoader {
    GameState loadGame(String filename);
}
