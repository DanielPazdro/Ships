package org.example.impl;

import org.example.abstraction.Board;
import org.example.abstraction.SuperpowerStrategy;

import java.io.Serializable;

public class CrossAttack implements SuperpowerStrategy, Serializable {
    @Override
    public boolean use(Board board, int row, int col) {
        int[][] positions = {
                {row, col},
                {row + 1, col},
                {row - 1, col},
                {row, col + 1},
                {row, col - 1}
        };
        int sum = 0;
        for (int[] position : positions) {
            sum += board.mark(position[0], position[1]) ? 1 : 0;
        }
        return sum > 0;
    }
}
