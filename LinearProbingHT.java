
/**
 *
 * @author Ian Rodovsky
 */
import java.util.LinkedList;

public class LinearProbingHT<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    private Node[] HT;
    private int M;
    private int N;

    public LinearProbingHT() {
        this(997);
    }

    public LinearProbingHT(int M) {
        this.M = M;
        this.N = 0;
        HT = new Node[this.M];
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
        if (val == null) {
            delete(key);
            return;
        }
        int hash, col = 0;
        if (this instanceof QuadProbingHT) {
            for (hash = ((QuadProbingHT) this).hash(key, col); HT[hash] != null; hash = (hash + 1) % M) {
                if (HT[hash].key.equals(key)) {
                    HT[hash].val = val;
                    return;
                }
                col++;
            }
        } else {
            for (hash = hash(key); HT[hash] != null; hash = (hash + 1) % M) {
                if (HT[hash].key.equals(key)) {
                    HT[hash].val = val;
                    return;
                }
                col++;
            }
        }
        HT[hash] = new Node(key, val);
        N++;
    }

    @Override
    public Value get(Key key) {
        if (this instanceof QuadProbingHT) {
            int col = 0;
            for (int hash = ((QuadProbingHT) this).hash(key, col); HT[hash] != null; hash = (hash + 1) % M) {
                if (HT[hash].key.equals(key)) {
                    return (Value) HT[hash].val;
                }
                col++;
            }
        } else {
            for (int hash = hash(key); HT[hash] != null; hash = (hash + 1) % M) {
                if (HT[hash].key.equals(key)) {
                    return (Value) HT[hash].val;
                }
            }
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        if (this instanceof QuadProbingHT) {
            int col = 0;
            for (int hash = ((QuadProbingHT) this).hash(key, col); HT[hash] != null; hash = (hash + 1) % M) {
                if (HT[hash].key.equals(key)) {
                    for (int nextHash = hash + 1; HT[nextHash] != null && nextHash > hash((Key) HT[nextHash].key); nextHash = (nextHash + 1) % M) {
                        HT[hash] = HT[nextHash];
                        hash = (hash + 1) % M;
                    }
                    HT[hash] = null;
                    N--;
                }
                col++;
            }
        } else {
            for (int hash = hash(key); HT[hash] != null; hash = (hash + 1) % M) {
                if (HT[hash].key.equals(key)) {
                    for (int nextHash = hash + 1; HT[nextHash] != null && nextHash > hash((Key) HT[nextHash].key); nextHash = (nextHash + 1) % M) {
                        HT[hash] = HT[nextHash];
                        hash = (hash + 1) % M;
                    }
                    HT[hash] = null;
                    N--;
                }
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
        for (int idx = 0; idx < M; idx++) {
            if (HT[idx] != null) {
                allKeys.add((Key) HT[idx].key);
            }
        }
        return allKeys;
    }

    public int hash(Key key) {
        return (key.hashCode() & 0x7FFFFFFF) % M;
    }
}
