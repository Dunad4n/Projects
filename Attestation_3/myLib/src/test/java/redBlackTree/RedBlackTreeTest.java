package redBlackTree;

import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RedBlackTreeTest {

    @Test
    public void testRemove() {
        RedBlackTree<Integer, Integer> treeRB = new RedBlackTree<>(10, 5);
        treeRB.add(5, 6);
        treeRB.add(7, 8);
        treeRB.add(11, 10);
        treeRB.remove(5);
        Assertions.assertEquals(treeRB.getRoot().getKey(), 10);
        Assertions.assertEquals(treeRB.getRoot().leftChild.getKey(), 7);
        Assertions.assertEquals(treeRB.getRoot().rightChild.getKey(), 11);
        Assertions.assertNull(treeRB.getRoot().leftChild.getLeftChild());
    }

    @Test
    public void testGetNode()  {
        RedBlackTree<Integer, Integer> treeRB = new RedBlackTree<>(10, 5);
        treeRB.add(8, 4);
        treeRB.add(15, 9);
        treeRB.add(13, 7);
        treeRB.add(14, 9);
        treeRB.add(5, 9);
        treeRB.add(1, 0);
        treeRB.add(4, 9);
        Assertions.assertEquals(treeRB.getNode(5).value, 9);
    }

    @Test
    public void testGetNullNode() {
        RedBlackTree<Integer, Integer> treeRB = new RedBlackTree<>(10, 5);
        treeRB.add(8, 4);
        treeRB.add(15, 9);
        treeRB.add(13, 7);
        treeRB.add(14, 9);
        treeRB.add(5, 9);
        treeRB.add(1, 0);
        treeRB.add(4, 9);
        Assertions.assertNull(treeRB.getNode(3));
    }

    @Test
    public void testLeftRotate() {
        RedBlackTree<Integer, Integer> treeRB = new RedBlackTree<>(10, 5);
        treeRB.getRoot().leftChild = new Node<>(7, 6);
        treeRB.getRoot().leftChild.setParent(treeRB.getRoot());
        treeRB.getRoot().leftChild.rightChild = new Node<>(8, 10);
        treeRB.getRoot().leftChild.rightChild.setParent(treeRB.getRoot().leftChild);
        treeRB.getRoot().rightChild = new Node<>(12, 11);
        treeRB.getRoot().rightChild.setParent(treeRB.getRoot());
        treeRB.leftRotate(treeRB.getRoot().leftChild);
        Assertions.assertEquals(treeRB.getRoot().getKey(), 10);
        Assertions.assertEquals(treeRB.getRoot().leftChild.getKey(), 8);
        Assertions.assertEquals(treeRB.getRoot().leftChild.leftChild.getKey(), 7);
        Assertions.assertEquals(treeRB.getRoot().rightChild.getKey(), 12);
    }

    @Test
    public void testRightRotate() {
        RedBlackTree<Integer, Integer> treeRB = new RedBlackTree<>(10, 5);
        treeRB.getRoot().leftChild = new Node<>(7, 6);
        treeRB.getRoot().leftChild.setParent(treeRB.getRoot());
        treeRB.getRoot().leftChild.leftChild = new Node<>(6, 10);
        treeRB.getRoot().leftChild.leftChild.setParent(treeRB.getRoot().leftChild);
        treeRB.getRoot().rightChild = new Node<>(12, 11);
        treeRB.getRoot().rightChild.setParent(treeRB.getRoot());
        treeRB.rightRotate(treeRB.getRoot());
        Assertions.assertEquals(treeRB.getRoot().getKey(), 7);
        Assertions.assertEquals(treeRB.getRoot().leftChild.getKey(), 6);
        Assertions.assertEquals(treeRB.getRoot().rightChild.getKey(), 10);
        Assertions.assertEquals(treeRB.getRoot().rightChild.rightChild.getKey(), 12);
    }

}