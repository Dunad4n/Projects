package redBlackTree;

import java.util.Comparator;

public class Node<K, V> {

    public K key = null;
    public V value = null;
    protected Node<K, V> leftChild = null;
    protected Node<K, V> rightChild = null;
    protected Node<K, V> parent = null;
    protected NodeColor color = NodeColor.RED;

    public Node() {}

    public Node(K key) {
        this.key = key;
    }

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Node(K key, V value, Node<K, V> parent) {
        this.key = key;
        this.value = value;
        this.parent = parent;
    }

    public Node(K key, V value, NodeColor color) {
        this.key = key;
        this.value = value;
        this.color = color;
    }

    public Node(K key, V value, Node<K, V> leftChild, Node<K, V> rightChild, Node<K, V> parent, NodeColor color) {
        this.key = key;
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.parent = parent;
        this.color = color;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node<K, V> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<K, V> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<K, V> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<K, V> rightChild) {
        this.rightChild = rightChild;
    }

    public Node<K, V> getParent() {
        return parent;
    }

    public void setParent(Node<K, V> parent) {
        this.parent = parent;
    }

    public NodeColor getColor() {
        return color;
    }

    public void setColor(NodeColor color) {
        this.color = color;
    }

}
