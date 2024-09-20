package org.example.impl;

import org.example.abstraction.MarkStrategy;
import org.example.abstraction.Player;
import org.example.abstraction.SuperpowerStrategy;
import org.example.models.Position;
import org.example.models.Square;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerImpl implements MarkStrategy, Serializable {
    private final List<Position> markedPositions;
    private final Random random;
    private final SuperpowerStrategy superpowerStrategy;
    private final List<Position> targetPositions;
    private int superpowerCount;

    public ComputerImpl() {
        markedPositions = new ArrayList<>();
        targetPositions = new ArrayList<>();
        random = new Random();
        superpowerStrategy = new CrossAttack();
        superpowerCount = 1;
    }

    @Override
    public boolean mark(Player other) {
        Position pos = targetPosition(other);

        markedPositions.add(pos);
        if(canUseSuperpower()) {
            superpowerCount--;
            return superpowerStrategy.use(other.getBoard(), pos.getRow(), pos.getCol());
        }
        boolean success = other.getBoard().mark(pos.getRow(), pos.getCol());
        if(success) {
            List<Position> positions = List.of(pos.getDown(), pos.getUp(), pos.getLeft(), pos.getRight());
            targetPositions.addAll(positions);
        }
        return success;
    }

    private boolean isAlreadyMarked(int row, int col) {
        return markedPositions.stream()
                .anyMatch(s -> s.getCol() == col && s.getRow() == row);
    }

    private boolean canUseSuperpower() {
        return random.nextDouble() < 0.1 && superpowerCount > 0;
    }

    private Position randomPosition(Player other) {
        int row, col;
        do {
            row = random.nextInt(0, other.getBoard().getSize());
            col = random.nextInt(0, other.getBoard().getSize());
        } while(isAlreadyMarked(row, col));
        return Position.of(row, col);
    }

    private Position targetPosition(Player other) {
        Position targetPosition;
        do {
            if(targetPositions.isEmpty()) {
                return randomPosition(other);
            }
            targetPosition = targetPositions.get(0);
            targetPositions.remove(0);
        } while(isAlreadyMarked(targetPosition.getRow(), targetPosition.getCol()));

        return Position.of(targetPosition.getRow(), targetPosition.getCol());
    }
}
