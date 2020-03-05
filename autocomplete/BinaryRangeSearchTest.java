package autocomplete;

import edu.princeton.cs.algs4.In;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class BinaryRangeSearchTest {

    private static Autocomplete linearAuto;
    private static Autocomplete binaryAuto;
    private static List<Term> terms;
    private static Autocomplete trieAuto;

    private static final String INPUT_FILENAME = "data/cities.txt";

    /**
     * Creates LinearRangeSearch and BinaryRangeSearch instances based on the data from cities.txt
     * so that they can easily be used in tests.
     */
    @Before
    public void setUp() {
        if (terms != null) {
            return;
        }

        In in = new In(INPUT_FILENAME);
        int n = in.readInt();
        terms = new ArrayList<>(n);
        for (int i = 0; i < n; i += 1) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms.add(new SimpleTerm(query, weight));
        }

        trieAuto = new Trie(terms);
        linearAuto = new LinearRangeSearch(terms);
        binaryAuto = new BinaryRangeSearch(terms);
    }

    @Test
    public void testSimpleExample() {
        Collection<Term> moreTerms = List.of(
            new SimpleTerm("hello", 0),
            new SimpleTerm("world", 0),
            new SimpleTerm("welcome", 0),
            new SimpleTerm("to", 0),
            new SimpleTerm("autocomplete", 0),
            new SimpleTerm("me", 0),
            new SimpleTerm("wa", 0)
        );
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        LinearRangeSearch lrs = new LinearRangeSearch(moreTerms);
        List<Term> expected = List.of(new SimpleTerm("autocomplete", 0));
        assertEquals(expected, brs.allMatches("auto"));
        List<Term> expected2 = List.of(
                new SimpleTerm("wa", 0),
                new SimpleTerm("welcome", 0),
                new SimpleTerm("world", 0)
        );
        List<Term> expected3 = new ArrayList<>();
        assertEquals(expected2, lrs.allMatches("w"));
        assertEquals(lrs.allMatches("h"), brs.allMatches("h"));
        assertEquals(expected2, brs.allMatches("w"));
        assertEquals(expected3, lrs.allMatches("b"));
    }

    // Write more unit tests below.
    @Test(expected = IllegalArgumentException.class)
    public void testThrowException() {
        Collection<Term> moreTerms = List.of(
                new SimpleTerm("hello", 0),
                new SimpleTerm("world", 0),
                new SimpleTerm("welcome", 0),
                new SimpleTerm("to", 0),
                new SimpleTerm("autocomplete", 0),
                new SimpleTerm("me", 0),
                new SimpleTerm("wa", 0)
        );
        Autocomplete brs = new BinaryRangeSearch(moreTerms);
        brs.allMatches(null);
    }

    @Test
    public void randomTestGenerator() {
        Random rand = new Random();
        int termsSize = terms.size();
        for (int i = 0; i < 1000; i++) {
            int randomWordIndex = rand.nextInt(termsSize);
            Term randWord = terms.get(randomWordIndex);
            int wordSize = randWord.query().length();
            int randPrefixSize = rand.nextInt(wordSize);
            String randPrefix = randWord.queryPrefix(randPrefixSize);
            List<Term> linear = linearAuto.allMatches(randPrefix);
            List<Term> trie = trieAuto.allMatches(randPrefix);
            assertEquals(linear, binaryAuto.allMatches(randPrefix));
            assertEquals(linear, trie);
        }
    }

    @Test
    public void testTrie() {
        Collection<Term> moreTerms = List.of(
                new SimpleTerm("e", 0),
                new SimpleTerm("hello", 0),
                new SimpleTerm("world", 0),
                new SimpleTerm("welcome", 0),
                new SimpleTerm("to", 0),
                new SimpleTerm("utocomplete", 0),
                new SimpleTerm("me", 0),
                new SimpleTerm("wa", 0)
        );
        Trie trie = new Trie(moreTerms);
        List<Term> expected = List.of();
        assertEquals(expected, trie.allMatches("a"));
        List<Term> expected2 = List.of(
                new SimpleTerm("wa", 0),
                new SimpleTerm("welcome", 0),
                new SimpleTerm("world", 0)
        );
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        LinearRangeSearch lrs = new LinearRangeSearch(moreTerms);
        assertEquals(expected, trie.allMatches("a"));
        List<Term> expected3 = new ArrayList<>();
        assertEquals(expected2, trie.allMatches("w"));
        assertEquals(lrs.allMatches("h"), trie.allMatches("h"));
        assertEquals(expected3, trie.allMatches("b"));
    }
}
