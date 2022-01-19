
/**
 *
 * @author Ian Rodovsky
 */
import java.util.LinkedList;

public class TwoProbeChainHT<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    private LinkedList<Node>[] HT;
    private int M;
    private int N;

    public TwoProbeChainHT() {
        this(997);
    }

    public TwoProbeChainHT(int M) {
        this.M = M;
        this.N = 0;
        HT = new LinkedList[this.M];
        for (int idx = 0; idx < this.M; idx++) {
            this.HT[idx] = new LinkedList<Node>();
        }
    }

    private class Node<Key, Value> {

        private final Key key;
        private Value val;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }

    }

    @Override
    public void put(Key key, Value val) {
        int firstHash = hash(key);
        int secHash = hash2(key);
        if (val == null) {
            delete(key);
            return;
        }
        for (int idx = 0; idx < HT[firstHash].size(); idx++) {
            if (HT[firstHash].get(idx).key.equals(key)) {
                HT[firstHash].get(idx).val = val;
                return;
            }
        }
        for (int idx = 0; idx < HT[secHash].size(); idx++) {
            if (HT[secHash].get(idx).key.equals(key)) {
                HT[secHash].get(idx).val = val;
                return;
            }
        }
        if (HT[firstHash].size() <= HT[secHash].size()) {
            HT[firstHash].add(new Node(key, val));
        } else {
            HT[secHash].add(new Node(key, val));
        }
        N++;
    }

    @Override
    public Value get(Key key) {
        int firstHash = hash(key);
        int secHash = hash2(key);
        for (int idx = 0; idx < HT[firstHash].size(); idx++) {
            if (HT[firstHash].get(idx).key.equals(key)) {
                return (Value) HT[firstHash].get(idx).val;
            }
        }
        for (int idx = 0; idx < HT[secHash].size(); idx++) {
            if (HT[secHash].get(idx).key.equals(key)) {
                return (Value) HT[secHash].get(idx).val;
            }
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        int firstHash = hash(key);
        int secHash = hash2(key);
        for (int idx = 0; idx < HT[firstHash].size(); idx++) {
            if (HT[firstHash].get(idx).key.equals(key)) {
                HT[firstHash].remove(idx);
                N--;
                return;
            }
        }
        for (int idx = 0; idx < HT[secHash].size(); idx++) {
            if (HT[secHash].get(idx).key.equals(key)) {
                HT[secHash].remove(idx);
                N--;
                return;
            }
        }
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<Key> keys() {
        LinkedList<Key> allKeys = new LinkedList();
        for (int outerIdx = 0; outerIdx < M; outerIdx++) {
            for (int innerIdx = 0; innerIdx < HT[outerIdx].size(); innerIdx++) {
                allKeys.add((Key) HT[outerIdx].get(innerIdx).key);
            }
        }
        return allKeys;
    }

    public int hash(Key key) {
        return (key.hashCode() & 0x7FFFFFFF) % M;
    }

    public int hash2(Key key) {
        return (((key.hashCode() & 0x7FFFFFFF) % M) * 31) % M;
    }
}
