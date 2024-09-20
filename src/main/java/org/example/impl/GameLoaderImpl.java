package org.example.impl;

import org.example.abstraction.GameLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class GameLoaderImpl implements GameLoader {
    public GameState loadGame(String filename) {
        File file = new File(filename);
        if(!file.exists()) {
            return null;
        }

        GameState gameState = null;
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            gameState = (GameState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameState;
    }
}