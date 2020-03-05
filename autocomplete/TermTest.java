package autocomplete;

import org.junit.Test;

import static org.junit.Assert.*;

public class TermTest {
    @Test
    public void testSimpleCompareTo() {
        Term a = new SimpleTerm("autocomplete", 0);
        SimpleTerm b = new SimpleTerm("me", 0);
        assertTrue(a.compareTo(b) < 0); // "autocomplete" < "me"
    }

    // Write more unit tests below.
    @Test(expected = IllegalArgumentException.class)
    public void testPrefix() {
        Term a = new SimpleTerm("abcde", 0);
        assertEquals("abc", a.queryPrefix(3));
        assertEquals("abcde", a.queryPrefix(10));
        a.queryPrefix(-1);
    }
}
