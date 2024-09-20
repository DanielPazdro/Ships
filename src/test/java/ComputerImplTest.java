import org.example.abstraction.Player;
import org.example.impl.BoardImpl;
import org.example.impl.ComputerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComputerImplTest {

    private ComputerImpl computer;
    private Player opponent;
    private BoardImpl board;

    @BeforeEach
    void setUp() {
        computer = new ComputerImpl();
        opponent = mock(Player.class);
        board = mock(BoardImpl.class);
        when(opponent.getBoard()).thenReturn(board);
        when(board.getSize()).thenReturn(10);
    }

    @Test
    void testMark() {
        when(board.mark(anyInt(), anyInt())).thenReturn(true);

        boolean result = computer.mark(opponent);

        verify(board, atMost(5)).mark(anyInt(), anyInt());
        assertTrue(result);
    }

    @Test
    void testSuperpowerUsage() {
        when(board.mark(anyInt(), anyInt())).thenReturn(true);
        when(board.getSize()).thenReturn(10);

        boolean superpowerUsed = false;
        for (int i = 0; i < 100; i++) {
            if (computer.mark(opponent)) {
                superpowerUsed = true;
                break;
            }
        }

        assertTrue(superpowerUsed);
    }
}
