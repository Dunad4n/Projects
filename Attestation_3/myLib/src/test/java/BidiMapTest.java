import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BidiMapTest {

    @Test
    public void testAdd() throws Exception {
        BidiMap<Integer, Integer> bidiMap = new BidiMap<>(10, 5);
        bidiMap.add(8, 10);
        bidiMap.add(13, 4);
        bidiMap.add(9, 6);
        BidiMap<Integer, Integer> inverseBidiMap = bidiMap.getInverseBidiMap();
        Assertions.assertEquals(bidiMap.getOriginalMap().getRoot().getKey(), 10);
        Assertions.assertEquals(bidiMap.getOriginalMap().getRoot().getLeftChild().getKey(), 8);
        Assertions.assertEquals(bidiMap.getOriginalMap().getRoot().getLeftChild().getRightChild().getKey(), 9);
        Assertions.assertEquals(bidiMap.getOriginalMap().getRoot().getRightChild().getKey(), 13);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getKey(), 5);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getRightChild().getKey(), 10);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getRightChild().getLeftChild().getKey(), 6);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getLeftChild().getKey(), 4);
    }

    @Test
    public void testAddException() throws Exception {
        BidiMap<Integer, Integer> bidiMap = new BidiMap<>(10, 5);
        bidiMap.add(8, 10);
        try {
            bidiMap.add(13, 10);
        } catch (Exception e) {
            assertNotEquals("", e.getMessage());
        }
    }

    @Test
    public void testRemoveWithKey() throws Exception {
        BidiMap<Integer, Integer> bidiMap = new BidiMap<>(10, 5);
        bidiMap.add(8, 10);
        bidiMap.add(13, 4);
        bidiMap.add(9, 6);
        bidiMap.removeWithKey(13);
        BidiMap<Integer, Integer> inverseBidiMap = bidiMap.getInverseBidiMap();
        Assertions.assertEquals(bidiMap.getOriginalMap().getRoot().getKey(), 9);
        Assertions.assertEquals(bidiMap.getOriginalMap().getRoot().getLeftChild().getKey(), 8);
        Assertions.assertEquals(bidiMap.getOriginalMap().getRoot().getRightChild().getKey(), 10);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getKey(), 6);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getRightChild().getKey(), 10);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getLeftChild().getKey(), 5);
    }

    @Test
    public void testRemoveWithValue() throws Exception {
        BidiMap<Integer, Integer> bidiMap = new BidiMap<>(10, 5);
        bidiMap.add(8, 10);
        bidiMap.add(13, 4);
        bidiMap.add(9, 6);
        bidiMap.removeWithValue(4);
        BidiMap<Integer, Integer> inverseBidiMap = bidiMap.getInverseBidiMap();
        Assertions.assertEquals(bidiMap.getOriginalMap().getRoot().getKey(), 9);
        Assertions.assertEquals(bidiMap.getOriginalMap().getRoot().getLeftChild().getKey(), 8);
        Assertions.assertEquals(bidiMap.getOriginalMap().getRoot().getRightChild().getKey(), 10);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getKey(), 6);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getRightChild().getKey(), 10);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getLeftChild().getKey(), 5);
    }

    @Test
    public void testContainsValue() throws Exception {
        BidiMap<Integer, Integer> bidiMap = new BidiMap<>(10, 5);
        bidiMap.add(8, 10);
        bidiMap.add(13, 4);
        bidiMap.add(9, 6);
        Assertions.assertTrue(bidiMap.containsValue(13));
        Assertions.assertFalse(bidiMap.containsValue(22));
    }

    @Test
    public void testContainsKey() throws Exception {
        BidiMap<Integer, Integer> bidiMap = new BidiMap<>(10, 5);
        bidiMap.add(8, 10);
        bidiMap.add(13, 4);
        bidiMap.add(9, 6);
        Assertions.assertTrue(bidiMap.containsKey(6));
        Assertions.assertFalse(bidiMap.containsKey(66));
    }

    @Test
    public void testGetInverseBidiMap() throws Exception {
        BidiMap<Integer, Integer> bidiMap = new BidiMap<>(10, 5);
        bidiMap.add(8, 10);
        bidiMap.add(13, 4);
        bidiMap.add(9, 6);
        BidiMap<Integer, Integer> inverseBidiMap = bidiMap.getInverseBidiMap();
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getKey(), 5);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getRightChild().getKey(), 10);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getRightChild().getLeftChild().getKey(), 6);
        Assertions.assertEquals(inverseBidiMap.getOriginalMap().getRoot().getLeftChild().getKey(), 4);
    }

}