import redBlackTree.Node;

public class BidiMap<K extends Comparable<K>, V extends Comparable<V>> {

    private TreeMap<K, V> originalMap = new TreeMap<>();
    private TreeMap<V, K> inverseMap = new TreeMap<>();

    public BidiMap() {}

    public BidiMap(K key, V value) {
        originalMap.setRoot(new Node<>(key, value));
        inverseMap.setRoot(new Node<>(value, key));
    }

    public BidiMap(TreeMap<K, V> originalMap, TreeMap<V, K> inverseMap) {
        this.originalMap = originalMap;
        this.inverseMap = inverseMap;
    }

    public TreeMap<K, V> getOriginalMap() {
        return originalMap;
    }

    public void setOriginalMap(TreeMap<K, V> originalMap) {
        this.originalMap = originalMap;
    }

    public TreeMap<V, K> getInverseMap() {
        return inverseMap;
    }

    public void setInverseMap(TreeMap<V, K> inverseMap) {
        this.inverseMap = inverseMap;
    }

    public void add(K key, V value) throws Exception {
        if (originalMap.containsValue(value) || inverseMap.containsValue(key)) {
            throw new Exception("this value is already exists");
        }
        originalMap.addElement(key, value);
        inverseMap.addElement(value, key);
    }

    public void removeWithKey(K key) {
        inverseMap.removeElement(originalMap.removeElement(key));
    }

    public void removeWithValue(V value) {
        originalMap.removeElement(inverseMap.removeElement(value));
    }

    public boolean containsValue(K key) {
        return originalMap.getNode(key) != null;
    }

    public boolean containsKey(V value) {
        return inverseMap.getNode(value) != null;
    }

    public BidiMap<V, K> getInverseBidiMap() {
        return new BidiMap<>(inverseMap, originalMap);
    }
}
