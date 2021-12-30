package redBlackTree;

public class RedBlackTree<K extends Comparable<K>, V > implements interfaces.RedBlackTree<K, V> {

    private Node<K, V> root = null;

    public RedBlackTree() {}

    public RedBlackTree(K varKey, V varValue) {
        root = new Node<>(varKey, varValue);
        root.color = NodeColor.BLACK;
    }

    public RedBlackTree(Node<K, V> root) {
        this.root = root;
    }

    public Node<K, V> getRoot() {
        return root;
    }

    public void setRoot(Node<K, V> root) {
        this.root = root;
    }

    @Override
    public V add(K varKey, V varValue) {
        if (root == null) {
            root = new Node<>(varKey, varValue, NodeColor.BLACK);
            return null;
        }
        Node<K, V> node = root;
        Node<K, V> addNode = new Node<>(varKey, varValue);
        while(true) {
            int cmp = addNode.key.compareTo(node.key);
            if (cmp == 0) {
                V oldValue = node.value;
                node.value = varValue;
                return oldValue;
            } else if (cmp < 0) {
                if (node.leftChild == null) {
                    node.setLeftChild(new Node<>(varKey, varValue));
                    node.leftChild.setParent(node);
                    balanceAfterAdd(node.leftChild);
                    return null;
                } else {
                    node = node.leftChild;
                }
            } else {
                if (node.rightChild == null) {
                    node.setRightChild(new Node<>(varKey, varValue));
                    node.rightChild.setParent(node);
                    balanceAfterAdd(node.rightChild);
                    return null;
                } else {
                    node = node.rightChild;
                }
            }
        }
    }

    @Override
    public V remove(K varKey) {
        Node<K, V> node = getNode(varKey);
        if (node == null) {
            return null;
        }
        V value = node.value;
        if (node.leftChild != null && node.rightChild != null) {
            Node<K, V> nextNode = getMinNode(node.rightChild);
            node.key = nextNode.key;
            node.value = nextNode.value;
            node = nextNode;
        }
        Node<K, V> child = (node.leftChild != null) ? node.leftChild : node.rightChild;
        if (child != null) {
            if (node == root) {
                setRoot(child);
                child.parent = null;
                root.setColor(NodeColor.BLACK);
            } else if (node.parent.leftChild== node) {
                setLeftLink(node.parent, child);
            } else {
                setRightLink(node.parent, child);
            }
            if (node.color == NodeColor.BLACK) {
                balanceAfterRemove(child);
            }
        } else if (node == root) {
            root = null;
        } else {
            if (node.color == NodeColor.BLACK) {
                balanceAfterRemove(node);
            }
            removeFromParent(node);
        }
        return value;
    }

    @Override
    public void rightRotate(Node<K, V> node) {
        if (node.leftChild == null) {
            return;
        }
        Node<K, V> left = node.leftChild;
        setLeftLink(node, left.rightChild);
        if (node.parent == null) {
            setRoot(left);
            left.parent = null;
        } else if (node.parent.leftChild == node) {
            setLeftLink(node.parent, left);
        } else {
            setRightLink(node.parent, left);
        }
        setRightLink(left, node);
    }

    @Override
    public void leftRotate(Node<K, V> node) {
        if (rightOf(node) == null) {
            return;
        }
        Node<K, V> right = node.rightChild;
        setRightLink(node, right.leftChild);
        if (node.parent == null) {
            setRoot(right);
            root.parent = null;
        } else if (node.parent.leftChild == node) {
            setLeftLink(node.parent, right);
        } else {
            setRightLink(node.parent, right);
        }
        setLeftLink(right, node);
    }

    @Override
    public Node<K, V> getNode(K varKey) {
        return getNode(root, varKey);
    }

    private void balanceAfterAdd(Node<K, V> node) {
        if (node != null && node != root && node.parent.color == NodeColor.RED) {
            if (siblingOf(node.parent) != null && siblingOf(node.parent).color == NodeColor.RED) {
                node.parent.setColor(NodeColor.BLACK);
                siblingOf(node.parent).setColor(NodeColor.BLACK);
                grandfatherOf(node).setColor(NodeColor.RED);
                balanceAfterAdd(grandfatherOf(node));
            } else if (node.parent == leftOf(grandfatherOf(node))) {
                if (node == node.parent.rightChild) {
                    leftRotate(node = node.parent);
                }
                node.parent.setColor(NodeColor.BLACK);
                grandfatherOf(node).setColor(NodeColor.RED);
                rightRotate(grandfatherOf(node));
            } else if (node.parent == rightOf(grandfatherOf(node))) {
                if (node == node.parent.leftChild) {
                    rightRotate(node = node.parent);
                }
                node.parent.color = NodeColor.BLACK;
                grandfatherOf(node).color = NodeColor.RED;
                leftRotate(grandfatherOf(node));
            }
        }
        root.color = NodeColor.BLACK;
    }

    private void balanceAfterRemove(Node<K, V> node) {
        while(node != root && isBlack(node)) {
            if (node == leftOf(node.parent)) {
                Node<K, V> sibling = rightOf(node.parent);
                if (isRed(sibling)) {
                    sibling.color = NodeColor.BLACK;
                    node.parent.color = NodeColor.RED;
                    leftRotate(node.parent);
                    sibling = rightOf(node.parent);
                }
                if (isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
                    sibling.color = NodeColor.RED;
                    node = node.parent;
                } else {
                    if (isBlack(rightOf(sibling))) {
                        leftOf(sibling).color = NodeColor.BLACK;
                        sibling.color = NodeColor.RED;
                        rightRotate(sibling);
                        sibling = rightOf(node.parent);
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = NodeColor.BLACK;
                    rightOf(sibling).color= NodeColor.BLACK;
                    leftRotate(node.parent);
                    node = root;
                }
            } else {
                Node<K, V> sibling = leftOf(node.parent);
                if (isRed(sibling)) {
                    sibling.color = NodeColor.BLACK;
                    node.parent.color = NodeColor.RED;
                    rightRotate(node.parent);
                    sibling = leftOf(node.parent);
                }
                if (isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
                    sibling.color = NodeColor.RED;
                    node = node.parent;
                } else {
                    if (isBlack(leftOf(sibling))) {
                        rightOf(sibling).color = NodeColor.BLACK;
                        sibling.color = NodeColor.RED;
                        leftRotate(sibling);
                        sibling = leftOf(node.parent);
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = NodeColor.BLACK;
                    leftOf(sibling).color = NodeColor.BLACK;
                    rightRotate(node.parent);
                    node = root;
                }
            }
        }
        node.color = NodeColor.BLACK;
    }

    private Node<K, V> getMinNode(Node<K, V> node) {
        while(node.leftChild != null) {
            node = node.leftChild;
        }
        return node;
    }

    private Node<K, V> getNode(Node<K, V> node, K varKey) {
        if (node == null) {
            return null;
        }
        Node<K, V> addNode = new Node<>(varKey);
        int cmp = addNode.key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        }
        if (cmp < 0) {
            return getNode(node.leftChild, varKey);
        } else {
            return getNode(node.rightChild, varKey);
        }
    }

    private void removeFromParent(Node<K, V> node) {
        if (node.parent != null) {
            if ( node.parent.leftChild == node) {
                node.parent.leftChild = null;
            } else if (node.parent.rightChild == node) {
                node.parent.rightChild = null;
            }
            node.parent = null;
        }
    }

    private Node<K, V> getMinNodeParent(Node<K, V> rootNode) {
        if (rootNode == null) {
            return null;
        }
        Node<K, V> parent = null;
        for (; rootNode.leftChild != null; rootNode = rootNode.leftChild) {
            parent = rootNode;
        }
        return parent;
    }

    private Node<K, V> grandfatherOf(Node<K, V> node) {
        return (node == null || node.parent == null) ? null : node.parent.parent;
    }

    private Node<K, V> siblingOf(Node<K, V> node) {
        return (node == null || node.parent == null) ? null : (node == node.parent.leftChild)
                ? node.parent.rightChild : node.parent.leftChild;
    }

    private void setLeftLink(Node<K, V> node, Node<K, V> left) {
        if (node != null) {
            node.leftChild = left;
            if (left != null) {
                left.parent = node;
            }
        }
    }

    private void setRightLink(Node<K, V> node, Node<K, V> right) {
        if (node != null) {
            node.rightChild = right;
            if (right != null) {
                right.parent = node;
            }
        }
    }

    private Node<K, V> leftOf(Node<K, V> node) {
        return node == null ? null : node.leftChild;
    }

    private Node<K, V> rightOf(Node<K, V> node) {
        return node == null ? null : node.rightChild;
    }

    private Node<K, V> parentOf(Node<K, V> node) {
        return node == null ? null : node.parent;
    }

    private NodeColor colorOf(Node<K, V> node) {
        return node == null ? NodeColor.BLACK : node.color;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == NodeColor.RED;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == NodeColor.BLACK;
    }
}
