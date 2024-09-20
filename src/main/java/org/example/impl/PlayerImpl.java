package org.example.impl;

import org.example.abstraction.Board;
import org.example.abstraction.MarkStrategy;
import org.example.abstraction.Player;
import org.example.abstraction.SuperpowerStrategy;

import java.io.Serializable;

public class PlayerImpl implements Player, Serializable {
    private final Board board;
    private final SuperpowerStrategy superpowerStrategy;
    private int superpowerCount;
    private int revealCount;

    public PlayerImpl() {
        board = new BoardImpl(this, BoardImpl.DEFAULT_SIZE);
        superpowerStrategy = new CrossAttack();
        superpowerCount = 1;
        revealCount = 1;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public boolean mark(int row, int col, boolean useSpecial) {
        if(useSpecial && superpowerCount > 0) {
            superpowerCount--;
            return superpowerStrategy.use(board, row, col);
        }
        return board.mark(row, col);
    }

    @Override
    public void reveal() {
        if(revealCount == 0) {
            return;
        }
        revealCount--;
        board.randomReveal();
    }

    public int getRevealCount() {
        return revealCount;
    }
}
