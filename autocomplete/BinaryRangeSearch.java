package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BinaryRangeSearch implements Autocomplete {
    private List<Term> terms;

    /**
     * Validates and stores the given terms.
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public BinaryRangeSearch(Collection<Term> terms) {
        if (terms == null) {throw new IllegalArgumentException(); }
        this.terms = new ArrayList<>();
        for (Term cur: terms) {
            if (cur == null) {
                throw new IllegalArgumentException();
            }
            this.terms.add(cur);
        }
        Collections.sort(this.terms);
    }

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public List<Term> allMatches(String prefix) {
        if (prefix == null) {throw new IllegalArgumentException(); }
        List<Term> ret = new ArrayList<>();
        Term prefixT = new SimpleTerm(prefix, prefix.length());
        int termsLength = terms.size();
        int first = binarySearchFirst(prefixT, prefix, 0, termsLength - 1);
        int last = binarySearchLast(prefixT, prefix, 0, termsLength - 1);

        // Only when found at least one Term, we add it to the ret list
        if (first != -1) {
            for (int i = first; i <= last; i++) {
                ret.add(terms.get(i));
            }
            ret.sort(Term.byReverseWeightOrder());
        }
        return ret;
    }

    //
    private int binarySearchFirst(Term prefix, String prefixS, int low, int high) {
        if (low > high) {return -1; }
        int mid = low + (high - low) / 2;
        Term curT = terms.get(mid);
        if (mid == 0) {
            if (curT.query().startsWith(prefixS)) {
                return mid;
            } else {
                return -1;
            }
        }
        Term prevT = terms.get(mid - 1);
        if (prevT.compareToByPrefixOrder(prefix, prefixS.length()) < 0
                && curT.query().startsWith(prefixS)) {
            return mid;
        } else if (curT.compareToByPrefixOrder(prefix, prefixS.length()) < 0) {
            return binarySearchFirst(prefix, prefixS, mid + 1, high);
        } else {
            return binarySearchFirst(prefix, prefixS, low, mid - 1);
        }
    }

    private int binarySearchLast(Term prefix, String prefixS, int low, int high) {
        if (low > high) {return -1; }
        int mid = low + (high - low) / 2;
        Term curT = terms.get(mid);
        if (mid == terms.size() - 1) {
            if (curT.query().startsWith(prefixS)) {
                return mid;
            } else {
                return -1;
            }
        }
        Term nextT = terms.get(mid + 1);
        if (nextT.compareToByPrefixOrder(prefix, prefixS.length()) > 0
                && curT.query().startsWith(prefixS)) {
            return mid;
        } else if (curT.compareToByPrefixOrder(prefix, prefixS.length()) > 0) {
            return binarySearchLast(prefix, prefixS, low, mid - 1);
        } else {
            return binarySearchLast(prefix, prefixS, mid+ 1, high);
        }
    }
}
