import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import redBlackTree.Node;

import static org.junit.jupiter.api.Assertions.*;

class TreeMapTest {

    @Test
    public void testAddElement() throws Exception {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.addElement(10, 5);
        treeMap.addElement(11, 3);
        treeMap.addElement(5, 9);
        treeMap.addElement(8, 7);
        Assertions.assertEquals(treeMap.getRoot().getKey(), 10);
        Assertions.assertEquals(treeMap.getRoot().getRightChild().getKey(), 11);
        Assertions.assertEquals(treeMap.getRoot().getLeftChild().getKey(), 5);
        Assertions.assertEquals(treeMap.getRoot().getLeftChild().getRightChild().getKey(), 8);
    }

    @Test
    public void testRemoveElement() throws Exception {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>(10, 5);
        treeMap.addElement(11, 3);
        treeMap.addElement(5, 9);
        treeMap.addElement(8, 7);
        treeMap.removeElement(5);
        Assertions.assertEquals(treeMap.getRoot().getKey(), 10);
        Assertions.assertEquals(treeMap.getRoot().getRightChild().getKey(), 11);
        Assertions.assertEquals(treeMap.getRoot().getLeftChild().getKey(), 8);
    }

    @Test
    public void testContainsKey() throws Exception {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.addElement(10, 5);
        treeMap.addElement(11, 3);
        treeMap.addElement(5, 9);
        treeMap.addElement(8, 7);
        assertTrue(treeMap.containsKey(11));
        Assertions.assertFalse(treeMap.containsKey(22));
    }

    @Test
    public void testGetValue() throws Exception {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.addElement(10, 5);
        treeMap.addElement(11, 3);
        treeMap.addElement(5, 9);
        treeMap.addElement(8, 7);
        Assertions.assertEquals(treeMap.getValue(8), 7);
    }

    @Test
    public void testContainsValue() throws Exception {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.addElement(10, "horse");
        treeMap.addElement(11, "cat");
        treeMap.addElement(5, "dog");
        treeMap.addElement(8, "snake");
        Assertions.assertTrue(treeMap.containsValue("dog"));
    }

    @Test
    public void testContainsValueFalse() throws Exception {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.addElement(10, 5);
        treeMap.addElement(11, 3);
        treeMap.addElement(5, 9);
        treeMap.addElement(8, 7);
        Assertions.assertFalse(treeMap.containsValue(2));
    }

    @Test
    public void testClone() throws Exception {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.addElement(10, 5);
        treeMap.addElement(11, 3);
        treeMap.addElement(5, 9);
        treeMap.addElement(8, 7);
        TreeMap<Integer, Integer> cloneTreeMap = treeMap.clone();
        Assertions.assertEquals(cloneTreeMap.getRoot().getKey(), 10);
        Assertions.assertEquals(cloneTreeMap.getRoot().getRightChild().getKey(), 11);
        Assertions.assertEquals(cloneTreeMap.getRoot().getLeftChild().getKey(), 5);
        Assertions.assertEquals(cloneTreeMap.getRoot().getLeftChild().getRightChild().getKey(), 8);
    }

    @Test
    public void testClear() throws Exception {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>(10, 5);
        treeMap.addElement(11, 3);
        treeMap.addElement(5, 9);
        treeMap.addElement(8, 7);
        treeMap.clear();
        Assertions.assertNull(treeMap.getRoot());
        try {
            treeMap.getRoot().getLeftChild();
        } catch (Exception e) {
            assertNotEquals("", e.getMessage());
        }
        try {
            treeMap.getRoot().getRightChild();
        } catch (Exception e) {
            assertNotEquals("", e.getMessage());
        }
    }
}