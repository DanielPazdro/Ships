import org.example.abstraction.Player;
import org.example.impl.BoardImpl;
import org.example.impl.PlayerImpl;
import org.example.models.Orientation;
import org.example.models.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardImplTest {

    private BoardImpl board;
    private Player player;

    @BeforeEach
    void setUp() {
        player = new PlayerImpl();
        board = new BoardImpl(player, BoardImpl.DEFAULT_SIZE);
    }

    @Test
    void testMark() {
        Ship ship = Ship.of(0, 0, 3, Orientation.HORIZONTAL);
        board.addShip(ship);

        assertTrue(board.mark(0, 0));
        assertFalse(board.mark(0, 0));
        assertTrue(ship.getSquares()[0].isMarked());
        assertFalse(board.mark(1, 1));
    }

    @Test
    void testToArray() {
        Ship ship = Ship.of(0, 0, 3, Orientation.HORIZONTAL);
        board.addShip(ship);
        board.mark(0, 0);
        board.mark(0, 1);

        int[][] expected = new int[BoardImpl.DEFAULT_SIZE][BoardImpl.DEFAULT_SIZE];
        expected[0][0] = 3;
        expected[0][1] = 3;

        assertArrayEquals(expected, board.toArray(false));
    }

    @Test
    void testGameOver() {
        Ship ship1 = Ship.of(0, 0, 2, Orientation.HORIZONTAL);
        Ship ship2 = Ship.of(2, 2, 3, Orientation.HORIZONTAL);
        board.addShip(ship1);
        board.addShip(ship2);

        board.mark(0, 0);
        board.mark(0, 1);
        assertFalse(board.gameOver());

        board.mark(2, 2);
        board.mark(2, 3);
        board.mark(2, 4);
        assertTrue(board.gameOver());
    }

    @Test
    void testAddShip() {
        Ship ship1 = Ship.of(0, 0, 3, Orientation.HORIZONTAL);
        Ship ship2 = Ship.of(0, 0, 2, Orientation.VERTICAL);

        assertTrue(board.addShip(ship1));
        assertFalse(board.addShip(ship2));
    }
}
