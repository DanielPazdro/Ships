import org.example.abstraction.Board;
import org.example.impl.CrossAttack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class CrossAttackTest {

    private CrossAttack crossAttack;
    private Board board;

    @BeforeEach
    void setUp() {
        crossAttack = new CrossAttack();
        board = mock(Board.class);
    }

    @Test
    void testUseCrossAttack() {
        when(board.mark(anyInt(), anyInt())).thenReturn(true);

        boolean result = crossAttack.use(board, 2, 2);

        verify(board, times(5)).mark(anyInt(), anyInt());
        assertTrue(result);
    }

    @Test
    void testUseCrossAttackWithBoundaryConditions() {
        when(board.mark(anyInt(), anyInt())).thenReturn(false);

        boolean result = crossAttack.use(board, 0, 0);

        verify(board, times(5)).mark(anyInt(), anyInt());
        assertFalse(result);

        result = crossAttack.use(board, -1, -1);
        verify(board, times(5 + 5)).mark(anyInt(), anyInt());
        assertFalse(result);
    }

    @Test
    void testUseCrossAttackNoMarks() {
        when(board.mark(anyInt(), anyInt())).thenReturn(false);

        boolean result = crossAttack.use(board, 5, 5);

        verify(board, times(5)).mark(anyInt(), anyInt());
        assertFalse(result);
    }
}
