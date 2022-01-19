
/**
 *
 * @author Ian Rodovsky
 */
public class QuadProbingHT<Key extends Comparable<Key>, Value> extends LinearProbingHT implements SymbolTable {

    private int M;

    public QuadProbingHT() {
        this(997);
    }

    public QuadProbingHT(int M) {
        super(M);
        this.M = M;
    }

    public int hash(Key key, int col) {
        return ((key.hashCode() & 0x7FFFFFFF) + col * col) % this.M;
    }
}
