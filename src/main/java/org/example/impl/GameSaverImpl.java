package org.example.impl;

import org.example.abstraction.GameSaver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameSaverImpl implements GameSaver {
    public void saveGame(GameState gameState, String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
