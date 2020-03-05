package deque.palindrome;

import deque.Deque;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class PalindromeTest {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("A"));
        assertFalse(palindrome.isPalindrome("tesatset"));
        assertTrue(palindrome.isPalindrome("asdfghjkllkjhgfdsa"));
        assertFalse(palindrome.isPalindrome("aabbaac"));
        assertFalse(palindrome.isPalindrome("ABba"));
        assertTrue(palindrome.isPalindrome("ABcBA"));
        assertFalse(palindrome.isPalindrome("ABab"));
}

    @Test
    public void testSecondIsPalindrome() {
        CharacterComparator offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertFalse(palindrome.isPalindrome("baab", offByOne));
        assertTrue(palindrome.isPalindrome("acegfdb", offByOne));
        assertTrue(palindrome.isPalindrome("acegrtvwushfdb", offByOne));
        assertFalse(palindrome.isPalindrome("asdfghjkllkjhgfdsa", offByOne));
        assertTrue(palindrome.isPalindrome("ACefDB", offByOne));
        assertFalse(palindrome.isPalindrome("ACdb"));
    }

    @Test
    public void testDNABasePair() {
        DNABasePair dnaBasePair = new DNABasePair();
        int testSize = 100;
        int testMinLength = 1;
        int testMaxLength = 10;
        int numSubF = 3;
        Random random = new Random();
        for (int i = 0; i < testSize; i++) {
            int length = random.nextInt(testMaxLength - testMinLength + 1) + testMinLength;
            String palin = dnaBasePair.randomPalindrome(length);
            assertTrue(palindrome.isPalindrome(palin, dnaBasePair));

            // Below tests have tested that randomNearPalindrome sometimes fails to
            // return a non-palindrome of DNA sequences

            /* int numSubs = random.nextInt(length * numSubF);
            String nonPalin = dnaBasePair.randomNearPalindrome(length, numSubs);
            System.out.println(nonPalin);
            assertFalse(palindrome.isPalindrome(nonPalin, dnaBasePair)); */
        }
    }
}
