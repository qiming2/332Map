package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/** Performs some basic linked deque tests. */
public class LinkedDequeTest {

    /** Adds a few strings to the deque, checking isEmpty() and size() are correct. */
    @Test
    public void addIsEmptySizeTest() {
        LinkedDeque<String> lld = new LinkedDeque<>();
        assertTrue(lld.isEmpty());

        lld.addFirst("front");
        assertEquals(1, lld.size());
        assertFalse(lld.isEmpty());

        lld.addLast("middle");
        assertEquals(2, lld.size());

        lld.addLast("back");
        assertEquals(3, lld.size());
    }

    /** Adds an item, then removes an item, and ensures that the deque is empty afterwards. */
    @Test
    public void addRemoveTest() {
        LinkedDeque<Integer> lld = new LinkedDeque<>();
        assertTrue(lld.isEmpty());

        lld.addFirst(10);
        assertFalse(lld.isEmpty());

        lld.removeFirst();
        assertTrue(lld.isEmpty());
    }

    @Test
    public void testTricky() {
        LinkedDeque<Integer> deque = new LinkedDeque<>();
        deque.addFirst(1);
        assertEquals(1, (int) deque.get(0));

        deque.addLast(2);
        assertEquals(2, (int) deque.get(1));

        deque.addFirst(-11);
        deque.addLast(2);
        assertEquals(2, (int) deque.get(3));

        deque.addLast(3);
        deque.addLast(4);

        // Test that removing and adding back is okay
        assertEquals(-11, (int) deque.removeFirst());
        deque.addFirst(-111);
        assertEquals(-111, (int) deque.get(0));

        deque.addLast(15);
        deque.addFirst(-2);
        deque.addFirst(-5);

        // Test a tricky sequence of removes
        assertEquals(-5, (int) deque.removeFirst());
        assertEquals(15, (int) deque.removeLast());
        assertEquals(4, (int) deque.removeLast());
        assertEquals(3, (int) deque.removeLast());
        assertEquals(2, (int) deque.removeLast());
        // Failing test
        int actual = deque.removeLast();
        assertEquals(2, actual);
    }

    @Test
    public void addLastRemoveFirstTest() {
        LinkedDeque<String> deque = new LinkedDeque<>();
        deque.addLast("P");
        deque.addLast("A");
        deque.addLast("O");
        deque.addLast("M");
        deque.addLast("O");
        assertEquals("P", deque.removeFirst());
        assertEquals("A", deque.removeFirst());
        assertSame(deque.size(), 3);
    }

    @Test
    public void addFirstRemoveLastTest() {
        LinkedDeque<String> deque = new LinkedDeque<>();
        deque.addFirst("P");
        deque.addFirst("A");
        deque.addFirst("O");
        deque.addFirst("M");
        deque.addFirst("O");
        assertEquals("P", deque.removeLast());
        assertEquals("A", deque.removeLast());
        assertSame(deque.size(), 3);
    }

    @Test
    public void removeEmptyQueue() {
        LinkedDeque<Integer> deque = new LinkedDeque<>();
        assertNull(deque.removeFirst());
        assertNull(deque.removeLast());
    }
}
