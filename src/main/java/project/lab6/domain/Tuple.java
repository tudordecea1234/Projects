package project.lab6.domain;

import java.util.Objects;

/**
 * Define a Tuple o generic type entities
 *
 * @param <E1> - tuple first entity type
 * @param <E2> - tuple second entity type
 */
public class Tuple<E1, E2> {
    private E1 e1;
    private E2 e2;

    public Tuple(E1 e1, E2 e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    /**
     * @return the left part of the tuple
     */
    public E1 getLeft() {
        return e1;
    }

    public void setLeft(E1 e1) {
        this.e1 = e1;
    }

    /**
     * @return the right part of the tuple
     */
    public E2 getRight() {
        return e2;
    }

    public void setRight(E2 e2) {
        this.e2 = e2;
    }

    @Override
    public String toString() {
        return "" + e1 +
                "," + e2;
    }

    @Override
    public boolean equals(Object o) {
        //Am modificat incat sa conteze ordinea
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return e1.equals(tuple.e1) && e2.equals(tuple.e2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e1, e2);
    }
}
