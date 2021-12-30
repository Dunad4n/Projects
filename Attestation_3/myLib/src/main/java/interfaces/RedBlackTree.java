package interfaces;

import redBlackTree.Node;

public interface RedBlackTree<K extends Comparable<K>, V> {

    V add(K varKey, V varValue) throws Exception;

    V remove(K varKey) throws Exception;

    Node<K, V> getNode(K varKey);

    void rightRotate(Node<K, V> node);

    void leftRotate(Node<K, V> node);

}
