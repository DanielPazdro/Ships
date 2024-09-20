

import static org.junit.jupiter.api.Assertions.*;

import org.example.models.Orientation;
import org.example.models.Ship;
import org.example.models.Square;
import org.junit.jupiter.api.Test;

public class ShipTest {

    @Test
    public void testShipCreationHorizontal() {
        Ship ship = Ship.of(0, 0, 3, Orientation.HORIZONTAL);
        Square[] squares = ship.getSquares();

        assertEquals(3, squares.length);
        assertEquals(new Square(0, 0), squares[0]);
        assertEquals(new Square(0, 1), squares[1]);
        assertEquals(new Square(0, 2), squares[2]);
    }

    @Test
    public void testShipCreationVertical() {
        Ship ship = Ship.of(0, 0, 3, Orientation.VERTICAL);
        Square[] squares = ship.getSquares();

        assertEquals(3, squares.length);
        assertEquals(new Square(0, 0), squares[0]);
        assertEquals(new Square(1, 0), squares[1]);
        assertEquals(new Square(2, 0), squares[2]);
    }

    @Test
    public void testGetSize() {
        Ship ship = Ship.of(0, 0, 4, Orientation.HORIZONTAL);
        assertEquals(4, ship.getSize());
    }

    @Test
    public void testIsSunken() {
        Ship ship = Ship.of(0, 0, 2, Orientation.HORIZONTAL);
        assertFalse(ship.isSunken());

        ship.getSquares()[0].setMarked(true);
        assertFalse(ship.isSunken());

        ship.getSquares()[1].setMarked(true);
        assertTrue(ship.isSunken());
    }
}
