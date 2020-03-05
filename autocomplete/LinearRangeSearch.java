package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LinearRangeSearch implements Autocomplete {
    private List<Term> terms;

    /**
     * Validates and stores the given terms.
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public LinearRangeSearch(Collection<Term> terms) {
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
        for (Term curTerm : terms) {
            if (curTerm.query().startsWith(prefix)) {
                ret.add(curTerm);
            }
        }
        if (ret.size() != 0) {
            ret.sort(Term.byReverseWeightOrder());
        }
        return ret;
    }
}
