import org.example.abstraction.Player;
import org.example.impl.*;
import org.example.models.Orientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.powermock.reflect.Whitebox.setInternalState;

class GameImplTest {

    private GameImpl game;

    @BeforeEach
    void setUp() {
        game = new GameImpl(new GameSaverImpl(), new GameLoaderImpl());
    }

    @Test
    void testAddShip() {
        assertTrue(game.addShip(0, 0, 0, 3, Orientation.HORIZONTAL));
        assertThrows(IllegalArgumentException.class, () -> game.addShip(2, 0, 0, 3, Orientation.HORIZONTAL));
    }

    @Test
    void testMark() {
        game.addShip(1, 0, 0, 3, Orientation.HORIZONTAL);
        assertTrue(game.mark(1, 0, 0));
        assertFalse(game.mark(1, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> game.mark(2, 0, 0));
    }

    @Test
    void testMarkSpecial() {
        game.addShip(0, 0, 0, 3, Orientation.HORIZONTAL);
        assertTrue(game.markSpecial(0, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> game.markSpecial(2, 0, 0));
    }

    @Test
    void testMarkWithStrategy() {
        GameImpl game = new GameImpl(new GameSaverImpl(), new GameLoaderImpl());
        PlayerImpl player = mock(PlayerImpl.class);
        BoardImpl board = mock(BoardImpl.class);

        when(player.getBoard()).thenReturn(board);

        when(board.mark(anyInt(), anyInt())).thenReturn(true);
        when(board.getSize()).thenReturn(10);

        Player[] players = {player};
        GameState gameState = new GameState(players, new ComputerImpl());
        setInternalState(game, "gameState", gameState);

        boolean result = game.mark(0);
        assertTrue(result);

        when(board.mark(anyInt(), anyInt())).thenReturn(false);

        result = game.mark(0);
        assertFalse(result);
    }

    @Test
    void testGetWinner() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        BoardImpl board1 = mock(BoardImpl.class);
        BoardImpl board2 = mock(BoardImpl.class);
        when(player1.getBoard()).thenReturn(board1);
        when(player2.getBoard()).thenReturn(board2);
        when(board1.gameOver()).thenReturn(false);
        when(board2.gameOver()).thenReturn(true);

        game = new GameImpl(new GameSaverImpl(), new GameLoaderImpl());

        Player[] players = {player1, player2};
        GameState gameState = new GameState(players, new ComputerImpl());
        setInternalState(game, "gameState", gameState);

        assertEquals(0, game.getWinner());
    }

    @Test
    void testBoardToArray() {
        int[][] expectedArray = new int[10][10];
        Player player = mock(Player.class);
        BoardImpl board = mock(BoardImpl.class);
        when(player.getBoard()).thenReturn(board);
        when(board.toArray(false)).thenReturn(expectedArray);

        int[][] resultArray = game.boardToArray(0, false);

        assertArrayEquals(expectedArray, resultArray);
        assertThrows(IllegalArgumentException.class, () -> game.boardToArray(2, false));
    }
}
