package deque.palindrome;

import org.junit.Test;

import static org.junit.Assert.*;

public class OffByOneTest {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('0', '1'));
        assertTrue(offByOne.equalChars('z', 'y'));
        assertTrue(offByOne.equalChars('y', 'z'));
        assertTrue(offByOne.equalChars('h', 'g'));
        assertTrue(offByOne.equalChars(' ', '!'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('1', '3'));
        assertFalse(offByOne.equalChars('3', '1'));
        assertFalse(offByOne.equalChars('H', 'h'));
        assertFalse(offByOne.equalChars('h', 'H'));
        assertFalse(offByOne.equalChars('a', 'B'));
        assertFalse(offByOne.equalChars('B', 'a'));
        assertTrue(offByOne.equalChars('B', 'A'));
    }
}
