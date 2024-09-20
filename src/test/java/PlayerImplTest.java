import org.example.abstraction.Board;
import org.example.abstraction.SuperpowerStrategy;
import org.example.impl.PlayerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.powermock.reflect.Whitebox.setInternalState;

class PlayerImplTest {

    private PlayerImpl player;
    private Board board;
    private SuperpowerStrategy superpowerStrategy;

    @BeforeEach
    void setUp() {
        player = new PlayerImpl();
        board = mock(Board.class);
        superpowerStrategy = mock(SuperpowerStrategy.class);

        setInternalState(player, "board", board);
        setInternalState(player, "superpowerStrategy", superpowerStrategy);
    }

    @Test
    void testGetBoard() {
        Board resultBoard = player.getBoard();
        assertNotNull(resultBoard);
        assertEquals(board, resultBoard);
    }

    @Test
    void testMark() {
        when(board.mark(0, 0)).thenReturn(true);

        boolean result = player.mark(0, 0, false);

        verify(board, times(1)).mark(0, 0);
        assertTrue(result);
    }

    @Test
    void testMarkWithSuperpower() {
        when(superpowerStrategy.use(board, 0, 0)).thenReturn(true);

        boolean result = player.mark(0, 0, true);

        verify(superpowerStrategy, times(1)).use(board, 0, 0);
        assertTrue(result);
    }

    @Test
    void testMarkWithSuperpowerFails() {
        when(superpowerStrategy.use(board, 0, 0)).thenReturn(false);

        boolean result = player.mark(0, 0, true);

        verify(superpowerStrategy, times(1)).use(board, 0, 0);
        assertFalse(result);
    }
}
