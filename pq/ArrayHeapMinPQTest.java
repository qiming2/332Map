package pq;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    /* Be sure to write randomized tests that can handle millions of items. To
     * test for runtime, compare the runtime of NaiveMinPQ vs ArrayHeapMinPQ on
     * a large input of millions of items. */
    private double D_MAX = 2.0;
    private double D_MIN = 0.0;
    private int I_MAX = 10000;

    @Test
    public void heapConstructorTest() {
        ExtrinsicMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        assertEquals(0, a.size());

    }

    @Test
    public void addElementTest() {
        ExtrinsicMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.add(10, 1.1);
        a.add(2, 0.2);
        a.add(3, 0.5);
        a.add(4, 0.1);
        a.add(5, 0.01);
        a.add(6, 0.02);
        a.add(7, 0.03);

        assertTrue(5 == a.removeSmallest());
        assertTrue(6 == a.removeSmallest());
        assertTrue(7 == a.removeSmallest());
        assertFalse(a.contains(1));
        assertFalse(a.contains(5));
    }

    @Test
    public void removeElementTest() {
        ExtrinsicMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.add(1, 1.1);
        a.add(2, 0.2);
        a.add(3, 0.5);
        a.add(4, 0.1);
        a.add(5, 0.01);
        a.add(6, 0.02);
        a.add(7, 0.03);
        a.changePriority(5, 4.0);
        a.changePriority(6, 5);

        assertTrue(7 == a.removeSmallest());
        assertTrue(4 == a.removeSmallest());
        assertTrue(2 == a.removeSmallest());
        assertTrue(a.contains(1));
        assertTrue(a.contains(5));
    }

    @Test
    public void containsAndSizeTest() {
        ExtrinsicMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.add(19, 1.1);
        a.add(12, 0.2);
        a.add(3, 0.5);
        a.add(4, 0.1);
        a.add(5, 0.01);
        a.add(6, 0.02);
        a.add(7, 0.03);
        a.changePriority(19, 4.0);
        a.changePriority(12, 5);

        assertTrue(5 == a.removeSmallest());
        assertTrue(6 == a.removeSmallest());
        assertTrue(7 == a.removeSmallest());
        assertFalse(a.contains(1));
        assertFalse(a.contains(5));
        assertEquals(4, a.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addThrowTest() {
        ExtrinsicMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.add(1, 1.1);
        a.add(1, 1.2);
    }

    @Test(expected = NoSuchElementException.class)
    public void getSmallestThrowTest() {
        ExtrinsicMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.getSmallest();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeThrowTest() {
        ExtrinsicMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.removeSmallest();
    }

    @Test(expected = NoSuchElementException.class)
    public void changePriorityThrowTest() {
        ExtrinsicMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.changePriority(1, 1.2);
    }

    @Test
    public void randomTestGenerator() {
        ExtrinsicMinPQ<IntDouble> arrHeap = new ArrayHeapMinPQ<>();
        ExtrinsicMinPQ<IntDouble> naiHeap = new NaiveMinPQ<>();
        Map<Integer, IntDouble> stored = new HashMap<>();
        Random rand = new Random();
        double priority = D_MIN + (D_MAX - D_MIN) * rand.nextDouble();
        IntDouble add = new IntDouble(-1, priority);
        stored.put(-1, add);
        arrHeap.add(add, priority);
        naiHeap.add(add, priority);

        // Test adding elements and size
        for (int i = 0; i < I_MAX; i++) {
            priority = D_MIN + (D_MAX - D_MIN) * rand.nextDouble();
            add = new IntDouble(i, priority);
            stored.put(i, add);
            arrHeap.add(add, priority);
            naiHeap.add(add, priority);
            assertTrue(arrHeap.getSmallest().priority == naiHeap.getSmallest().priority);
            assertEquals(arrHeap.size(), naiHeap.size());
            assertEquals(arrHeap.size(), i + 2);
        }

        // Test changePriority
        int randNode = 0;
        for (int i = 0; i <= I_MAX; i++) {
            randNode = rand.nextInt(I_MAX);
            IntDouble cur = stored.get(randNode);
            priority = D_MIN + (D_MAX - D_MIN) * rand.nextDouble();
            cur.setPriority(priority);
            arrHeap.changePriority(cur, priority);
            naiHeap.changePriority(cur, priority);
            assertTrue(arrHeap.getSmallest().priority == naiHeap.getSmallest().priority);
        }

        // Test contains
        for (int i = 0; i < I_MAX; i++) {
            arrHeap.contains(stored.get(i));
        }

        // Test removing elements and size
        for (int i = 0; i < I_MAX; i++) {
            assertTrue(arrHeap.removeSmallest().priority == naiHeap.removeSmallest().priority);
            assertEquals(arrHeap.size(), naiHeap.size());
            assertEquals(arrHeap.size(), I_MAX - i);
        }

        assertEquals(arrHeap.size(), 1);


    }

    private class IntDouble {
        public int value;
        public double priority;
        public IntDouble(int val, double p) {
            this.value = val;
            this.priority = p;
        }

        public void setPriority(double p) {
            this.priority = p;
        }
    }


}
