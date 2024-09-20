package org.example;

import org.example.abstraction.Game;
import org.example.impl.GameImpl;
import org.example.impl.GameLoaderImpl;
import org.example.impl.GameSaverImpl;
import org.example.models.Orientation;

public class Main {
    static void show(int[][] board) {
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("[");
                if(board[i][j] == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print(board[i][j]);
                }
                System.out.print("]");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Game game = new GameImpl(new GameSaverImpl(), new GameLoaderImpl());
        //game.loadGame("test.bin");

        //dodanie statku dla gracza 1 (nalezy dac w konstruktorze Game wartosc false)
        //game.addShip(0, 5, 5, 3, Orientation.VERTICAL);

        System.out.println("Plansza gracza nr 1");
        show(game.boardToArray(0, false));

        System.out.println("Proba strzalu na graczu 1");
        game.mark(0, 5, 3);
        game.mark(0, 1, 2);
        game.mark(0, 1, 1);
        game.mark(0, 3, 8);
        game.mark(0, 2, 4);
        game.mark(0, 5, 7);
        show(game.boardToArray(0, false));

        System.out.println("Plansza gracza nr 2");
        show(game.boardToArray(1, false));

        System.out.println("Proba strzalu na graczu 2");
        game.mark(1, 5, 3);
        game.mark(1, 1, 2);
        game.mark(1, 1, 1);
        game.mark(1, 3, 8);
        game.mark(1, 2, 4);
        game.mark(1, 5, 7);
        game.markSpecial(1, 5, 7);
        show(game.boardToArray(1, false));

        System.out.println("Sprawdzenie czy ktos wygrał");
        System.out.println(game.getWinner());

        game.saveGame("test.bin");
    }
}