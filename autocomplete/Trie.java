package autocomplete;

import java.util.*;

/**
 * Tries are tree data structures that efficiently store strings. Each node
 * represents a letter and traversal of the tree starting at the root and
 * ending at a specially marked terminal node determines a word.
 */
public class Trie implements Autocomplete {
    private Node root = new Node();

    private static class Node {
        Term value;
        Map<Character, Node> next = new TreeMap<>();
    }

    /**
     * Creates a trie containing all of the given terms.
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public Trie(Collection<Term> terms) {
        if (terms == null) {throw new IllegalArgumentException(); }
        for (Term cur: terms) {
            if (cur == null) {
                throw new IllegalArgumentException();
            }
            root = put(root, cur, cur.query(), 0);
        }
    }

    private Node put(Node cur, Term value, String key, int d) {
        if (cur == null) {cur = new Node(); }
        if (d == key.length()) {
            cur.value = value;
            return cur;
        }
        char nextChar = key.charAt(d);
        Map<Character, Node> curMap = cur.next;
        curMap.put(nextChar, put(curMap.get(nextChar), value, key, d + 1));
        return cur;
    }

    @Override
    public List<Term> allMatches(String prefix) {
        if (prefix == null) {throw new IllegalArgumentException(); }
        List<Term> ret = new ArrayList<>();
        Node prefixNode = findPrefixNode(root, prefix, 0);
        addToList(prefixNode, ret);
        ret.sort(Term.byReverseWeightOrder());
        return ret;
    }

    private void addToList(Node cur, List<Term> ret) {
        if (cur == null) {return; }
        Term value = cur.value;
        if (value != null) {
            ret.add(value);
        }
        Map<Character, Node> curMap = cur.next;
        for (Character c: curMap.keySet()) {
            addToList(curMap.get(c), ret);
        }
    }

    private Node findPrefixNode(Node cur, String prefix, int d) {
        if (cur == null) {return null; }
        if (d == prefix.length()) {return cur; }
        char nextChar = prefix.charAt(d);
        Node next = cur.next.get(nextChar);
        return findPrefixNode(next, prefix, d + 1);
    }
}
