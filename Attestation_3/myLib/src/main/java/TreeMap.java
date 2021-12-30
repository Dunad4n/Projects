import redBlackTree.Node;
import redBlackTree.RedBlackTree;

public class TreeMap<K extends Comparable<K>, V> extends RedBlackTree<K, V> {

    public TreeMap() {}

    public TreeMap(K key, V value) {
        super(key, value);
    }

    public TreeMap(Node<K, V> root) {
        super(root);
    }

    public V addElement(K key, V value) {
        return add(key, value);
    }

    public V removeElement(K key) {
        return remove(key);
    }

    public V getValue(K key) {
        return getNode(key).getValue();
    }

    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    public boolean containsValue(V value) throws Exception {
        if (getRoot() == null) {
            throw new Exception("this tree have no root");
        }
        return getNodeValue(getRoot(), value) != null;
    }

    private V getNodeValue(Node<K, V> node, V value) {
        if (node.getValue().equals(value)) {
            return node.getValue();
        }
        if (node.getLeftChild() != null) {
            V leftNodeValue = getNodeValue(node.getLeftChild(), value);
            if (value.equals(leftNodeValue)) {
                return leftNodeValue;
            }
        }
        if (node.getRightChild() != null) {
            V rightNodeValue = getNodeValue(node.getRightChild(), value);
            if (value.equals(rightNodeValue)) {
                return rightNodeValue;
            }
        }
        return null;
    }

    public TreeMap<K, V> clone() {
        if (this.getRoot() == null) {
            try {
                throw new Exception("this tree have no root");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Node<K, V> root = this.clone(this.getRoot(), null);
        return new TreeMap<>(root);
    }

    private Node<K, V> clone(Node<K, V> currNode, Node<K, V> parent) {
        Node<K, V> cloneNode = new Node<>(currNode.getKey(), currNode.getValue(), currNode.getColor());
        cloneNode.setParent(parent);
        Node<K, V> leftChild = new Node<>();
        Node<K, V> rightChild = new Node<>();
        if (currNode.getLeftChild() != null) {
            Node<K, V> leftNode = clone(currNode.getLeftChild(), cloneNode);
            leftChild.setKey(leftNode.getKey());
            leftChild.setValue(leftNode.getValue());
            leftChild.setColor(leftNode.getColor());
            leftChild.setParent(cloneNode);
            leftChild.setLeftChild(leftNode.getLeftChild());
            leftChild.setRightChild(leftNode.getRightChild());
            cloneNode.setLeftChild(leftChild);
        }
        if (currNode.getRightChild() != null) {
            Node<K, V> rightNode = clone(currNode.getRightChild(), cloneNode);
            rightChild.setKey(rightNode.getKey());
            rightChild.setValue(rightNode.getValue());
            rightChild.setColor(rightNode.getColor());
            rightChild.setParent(cloneNode);
            rightChild.setLeftChild(rightNode.getLeftChild());
            rightChild.setRightChild(rightNode.getRightChild());
            cloneNode.setRightChild(rightChild);
        }
        return cloneNode;
    }

    public void clear() {
        setRoot(null);
    }

    private Node<K, V> leftOf(Node<K, V> node) {
        return node == null ? null : node.getLeftChild();
    }

    private Node<K, V> rightOf(Node<K, V> node) {
        return node == null ? null : node.getRightChild();
    }
}
