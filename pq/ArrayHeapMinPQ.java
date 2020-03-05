package pq;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private List<PriorityNode> minHeap;
    private Map<T, PriorityNode> itemToNode;
    private int size;
    private int capacity;

    /**
     * A small class for storing meta data for item and priority.
     * Also, it holds a compareTo method.
     */
    private class PriorityNode {
        public T item;
        public double priority;
        public int index;

        public PriorityNode(T item, double p, int index) {
            if (item == null) {throw new IllegalArgumentException(); }
            this.item = item;
            this.priority = p;
            this.index = index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        // Return back the original priority
        public double setPriority(double p) {
            double ret = this.priority;
            this.priority = p;
            return ret;
        }

        public int compareTo(PriorityNode that) {
            return Double.compare(this.priority, that.priority);
        }
    }

    public ArrayHeapMinPQ() {
        capacity = 17;
        minHeap = new ArrayList<>(capacity);
        minHeap.add(size, null);
        itemToNode = new HashMap<>();
        size = 0;
    }

    /*
    Here's a helper method and a method stub that may be useful. Feel free to change or remove
    them, if you wish.
     */

    /**
     * Resizes the ArrayList to our desired capacity size
     */
    private void resize(int newCapacity) {
        capacity = newCapacity;
        List<PriorityNode> temp = new ArrayList<>(newCapacity);
        for (int i = 0; i <= size; i++) {
            temp.add(i, minHeap.get(i));
        }
        minHeap = temp;
    }

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        minHeap.get(a).setIndex(b);
        minHeap.get(b).setIndex(a);
        Collections.swap(minHeap, a, b);
    }

    /**
     * Sinks the node downwards if greater than one of its child
     */
    private void sink(int k) {
        if (k <= 0 || k > size / 2) {return; }
        PriorityNode temp = minHeap.get(k);
        while (leftChild(k) <= size) {
            int j = leftChild(k);
            if (j < size && minHeap.get(j).compareTo(minHeap.get(j + 1)) > 0) {j++; }
            if (temp.compareTo(minHeap.get(j)) <= 0) {break; }
            swap(k, j);
            k = j;
        }
    }

    /**
     * Swims the node upwards if lower than its parent
     */
    private void swim(int k) {
        if (k <= 1 || k > size) {return; }
        PriorityNode temp = minHeap.get(k);
        while (k > 1 && temp.compareTo(minHeap.get(parent(k))) < 0) {
            swap(k, parent(k));
            k = parent(k);
        }
    }

    /**
     * Returns the parent of the given node
     */
    private int parent(int k) {
        return k / 2;
    }

    /**
     * Returns the child of the node
     */
    private int leftChild(int k) {
        return 2 * k;
    }

    /**
     * Adds an item with the given priority value.
     * Assumes that item is never null.
     * Runs in O(log N) time (except when resizing).
     * @throws IllegalArgumentException if item is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {throw new IllegalArgumentException(); }
        if (size == capacity - 1) {
            resize(2 * size);
        }
        size++;
        PriorityNode added = new PriorityNode(item, priority, size);
        minHeap.add(size, added);
        itemToNode.put(item, added);
        swim(size);
    }

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        return itemToNode.containsKey(item);
    }

    /**
     * Returns the item with the smallest priority.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T getSmallest() {
        if (size == 0) {throw new NoSuchElementException(); }
        return minHeap.get(1).item;
    }

    /**
     * Removes and returns the item with the smallest priority.
     * Runs in O(log N) time (except when resizing).
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        swap(1, size);
        T removed = minHeap.remove(size).item;
        size--;
        itemToNode.remove(removed);
        sink(1);
        // I read over when should be a good time to cut half of the capacity from
        // textbook implementation of minHeap
        if (size > 0 && size <= (capacity - 1) / 4) {resize(capacity / 2); }
        return removed;
    }

    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {throw new NoSuchElementException(); }
        PriorityNode itemNode = itemToNode.get(item);
        double originP = itemNode.setPriority(priority);
        int compare = Double.compare(priority, originP);
        // If the priority changes, we would change its position
        // based on the changed priority value
        if (compare < 0) {
            swim(itemNode.index);
        } else if (compare > 0) {
            sink(itemNode.index);
        }
    }

    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        return size;
    }
}
