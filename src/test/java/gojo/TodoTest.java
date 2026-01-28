package gojo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void dummyTest() {
        assertEquals(2, 2);
    }

    @Test
    public void testStringConversion() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void testFileFormat() {
        Todo todo = new Todo("read book");
        assertEquals("T | 0 | read book", todo.toFileFormat());
    }
}
