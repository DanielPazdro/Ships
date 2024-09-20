package org.example.impl;

import org.example.abstraction.*;
import org.example.models.Orientation;
import org.example.models.Ship;

public class GameImpl implements Game {
    private GameState gameState;
    private final GameSaver gameSaver;
    private final GameLoader gameLoader;

    public GameImpl(GameSaver gameSaver, GameLoader gameLoader) {
        this.gameSaver = gameSaver;
        this.gameLoader = gameLoader;
        gameState = new GameState(new Player[]{
                new PlayerImpl(),
                new PlayerImpl()
        }, new ComputerImpl());

        gameState.getPlayers()[1].getBoard().setShipsRandomly();
    }

    @Override
    public boolean addShip(int playerNo, int row, int col, int size, Orientation orientation) {
        if(playerNo < 0 || playerNo >= gameState.getPlayers().length) {
            throw new IllegalArgumentException("Invalid player number");
        }
        return gameState.getPlayers()[playerNo]
                .getBoard()
                .addShip(Ship.of(row, col, size, orientation));
    }

    @Override
    public boolean mark(int playerNo, Integer row, Integer col) {
        if(playerNo < 0 || playerNo >= gameState.getPlayers().length) {
            throw new IllegalArgumentException("Invalid player number");
        }
        return gameState.getPlayers()[playerNo].mark(row, col, false);
    }

    @Override
    public boolean markSpecial(int playerNo, Integer row, Integer col) {
        if(playerNo < 0 || playerNo >= gameState.getPlayers().length) {
            throw new IllegalArgumentException("Invalid player number");
        }
        return gameState.getPlayers()[playerNo].mark(row, col, true);
    }

    @Override
    public boolean mark(int playerNo) {
        if(playerNo < 0 || playerNo >= gameState.getPlayers().length) {
            throw new IllegalArgumentException("Invalid player number");
        }
        return gameState.getMarkStrategy().mark(gameState.getPlayers()[playerNo]);
    }

    @Override
    public int getWinner() {
        int winner = -1;
        int winnerCount = 0;
        for(int i = 0; i < gameState.getPlayers().length; i++) {
            if(!gameState.getPlayers()[i].getBoard().gameOver()) {
                winner = i;
                winnerCount++;
            }
        }
        if(winnerCount == 1) {
            return winner;
        } else {
            return -1;
        }
    }

    @Override
    public int[][] boardToArray(int playerNo, boolean ships) {
        if(playerNo < 0 || playerNo >= gameState.getPlayers().length) {
            throw new IllegalArgumentException("Invalid player number");
        }
        return gameState.getPlayers()[playerNo]
                .getBoard()
                .toArray(ships);
    }

    @Override
    public boolean loadGame(String filename) {
        GameState gameStateTmp = gameLoader.loadGame(filename);
        if(gameStateTmp == null) {
            return false;
        }
        gameState = gameStateTmp;
        return true;
    }

    @Override
    public void saveGame(String filename) {
        gameSaver.saveGame(gameState, filename);
    }

    @Override
    public void reveal(int playerNo) {
        if(playerNo < 0 || playerNo >= gameState.getPlayers().length) {
            throw new IllegalArgumentException("Invalid player number");
        }

        gameState.getPlayers()[playerNo].reveal();
    }

    @Override
    public Board getBoard(int playerNo) {
        if(playerNo < 0 || playerNo >= gameState.getPlayers().length) {
            throw new IllegalArgumentException("Invalid player number");
        }

        return gameState.getPlayers()[playerNo].getBoard();
    }

    @Override
    public boolean playerCanReveal(int playerNo) {
        if(playerNo < 0 || playerNo >= gameState.getPlayers().length) {
            throw new IllegalArgumentException("Invalid player number");
        }

        return gameState.getPlayers()[playerNo].getRevealCount() > 0;
    }
}
