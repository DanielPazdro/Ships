package org.example.impl;

import org.example.abstraction.MarkStrategy;
import org.example.abstraction.Player;

import java.io.Serializable;

public class GameState implements Serializable {
    private final Player[] players;
    private final MarkStrategy markStrategy;


    public GameState(Player[] players, MarkStrategy markStrategy) {
        this.players = players;
        this.markStrategy = markStrategy;
    }

    public Player[] getPlayers() {
        return players;
    }

    public MarkStrategy getMarkStrategy() {
        return markStrategy;
    }
}
