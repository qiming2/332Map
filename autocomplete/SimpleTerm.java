package autocomplete;

import java.util.Objects;

public class SimpleTerm implements Term {
    private final String query;
    private final long weight;

    public SimpleTerm(String query, long weight) {
        if (query == null || weight < 0) {
            throw new IllegalArgumentException();
        }
        this.query = query;
        this.weight = weight;
    }

    public String query() {
        return query;
    }

    public long weight() {
        return weight;
    }

    public String queryPrefix(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("Negative query prefix");
        }
        int queryLength = query.length();
        int prefixSize = r > queryLength ? queryLength : r;
        return query.substring(0, prefixSize);
    }

    @Override
    public String toString() {
        return "SimpleTerm{" +
                "query='" + query + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true; }
        if (o == null || getClass() != o.getClass()) {return false; }
        SimpleTerm that = (SimpleTerm) o;
        return weight == that.weight &&
                Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, weight);
    }
}
