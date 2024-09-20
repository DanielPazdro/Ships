package org.example.impl;

import org.example.abstraction.Board;
import org.example.abstraction.Player;
import org.example.models.Orientation;
import org.example.models.Ship;
import org.example.models.Square;

import java.io.Serializable;
import java.util.*;

public class BoardImpl implements Board, Serializable {
    public static final int DEFAULT_SIZE = 10;

    private final List<Ship> ships;
    private final Set<Square> missed;
    private final Player player;
    private final int boardSize;
    private final Random random;

    public BoardImpl(Player player, int boardSize) {
        this.player = player;
        ships = new ArrayList<>();
        this.boardSize = boardSize;
        random = new Random();
        missed = new HashSet<>();
    }

    @Override
    public boolean mark(int row, int col) {
        for(var ship : ships) {
            Optional<Square> square = Arrays.stream(ship.getSquares())
                    .filter(s -> s.equals(row, col))
                    .findFirst();
            if(square.isPresent() && !square.get().isMarked()) {
                square.get().setMarked(true);
                return true;
            }
        }

        if(row >= 0 && row < boardSize && col >= 0 && col < boardSize) {
            missed.add(new Square.Builder(row, col).build());
        }
        return false;
    }

    @Override
    public int[][] toArray(boolean shipsOnly) {
        int[][] intBoard = new int[boardSize][boardSize];
        for(var square : missed) {
            intBoard[square.getRow()][square.getCol()] = -1;
        }

        for(var ship : ships) {
            for(Square square : ship.getSquares()) {
                if(square.isMarked() || shipsOnly) {
                    intBoard[square.getRow()][square.getCol()] = ship.getSize();
                }
            }
        }
        return intBoard;
    }

    @Override
    public boolean gameOver() {
        return ships.stream()
                .allMatch(Ship::isSunken);
    }

    @Override
    public void setShipsRandomly() {
        ships.clear();
        int[] shipSizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        for(int size : shipSizes) {
            Ship ship;
            do {
                int row = random.nextInt(0, boardSize);
                int col = random.nextInt(0, boardSize);
                Orientation orientation = (random.nextInt(0, 2) == 0 ? Orientation.HORIZONTAL : Orientation.VERTICAL);
                ship = Ship.of(row, col, size, orientation);
            } while(!addShip(ship));
        }
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean addShip(Ship ship) {
        if(canAddShip(ship)) {
            System.out.println(ship);
            ships.add(ship);
            return true;
        }
        return false;
    }

    @Override
    public int getSize() {
        return boardSize;
    }

    @Override
    public void randomReveal() {
        List<Square> availableShipSquares = new ArrayList<>();
        for(Ship s : ships) {
            for(Square sq : s.getSquares()) {
                if(!sq.isMarked()) {
                    availableShipSquares.add(sq);
                }
            }
        }

        if(availableShipSquares.isEmpty()) {
            return;
        }

        int squareIdx = random.nextInt(0, availableShipSquares.size());
        Square targetSquare = availableShipSquares.get(squareIdx);
        mark(targetSquare.getRow(), targetSquare.getCol());
    }

    private boolean canAddShip(Ship ship) {
        for(Square square : ship.getSquares()) {
            if(square.getCol() < 0 || square.getCol() >= boardSize || square.getRow() < 0 || square.getRow() >= boardSize){
                return false;
            }

            for(Ship existShip : ships) {
                for(Square existSquare : existShip.getSquares()) {
                    if(square.equals(existSquare)) {
                        return false;
                    }

                    int rowDiff = Math.abs(square.getRow() - existSquare.getRow());
                    int colDiff = Math.abs(square.getCol() - existSquare.getCol());

                    if(rowDiff <= 1 && colDiff <= 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
