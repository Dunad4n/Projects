import redBlackTree.Node;

public class TreePainter {

    public TreePainter() {}

    public void drawTree(Node root) {
        drawTree(root, "");
    }

    private void drawTree(Node root, String space) {
        if (root == null) {
            return;
        }
        if (root.getParent() != null) {
            if (root.equals(root.getParent().getLeftChild())) {
                System.out.println(space + "L: " + root.getKey() + " " + root.getValue());
            } else {
                System.out.println(space + "R: " + root.getKey() + " " + root.getValue());
            }
        } else {
            System.out.println(space + " " + root.getKey() + " " + root.getValue());
        }
        space += "   ";
        if (root.getLeftChild() != null) {
            drawTree(root.getLeftChild(), space);
        }
        if (root.getRightChild() != null) {
            drawTree(root.getRightChild(), space);
        }
    }
}
