import static org.junit.jupiter.api.Assertions.*;

import org.example.models.Square;
import org.junit.jupiter.api.Test;

public class SquareTest {

    @Test
    public void testConstructorAndGetters() {
        Square square = new Square(2, 3);
        assertEquals(2, square.getRow());
        assertEquals(3, square.getCol());
    }

    @Test
    public void testBuilder() {
        Square square = new Square.Builder(2, 3)
                .marked(true)
                .build();

        assertEquals(2, square.getRow());
        assertEquals(3, square.getCol());
        assertTrue(square.isMarked());
    }

    @Test
    public void testMarkedFlag() {
        Square square = new Square(2, 3);
        assertFalse(square.isMarked());

        square.setMarked(true);
        assertTrue(square.isMarked());

        square.setMarked(false);
        assertFalse(square.isMarked());
    }

    @Test
    public void testEqualsObject() {
        Square square1 = new Square(2, 3);
        Square square2 = new Square(2, 3);
        Square square3 = new Square(3, 2);

        assertEquals(square1, square2);
        assertNotEquals(square1, square3);
        assertNotEquals(square1, null);
        assertNotEquals(square1, new Object());
    }

    @Test
    public void testEqualsRowCol() {
        Square square = new Square(2, 3);

        assertTrue(square.equals(2, 3));
        assertFalse(square.equals(3, 2));
        assertFalse(square.equals(2, 4));
    }
}
