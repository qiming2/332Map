package deque;

public class LinkedDeque<T> implements Deque<T> {
    private int size;
    private Node first;
    private Node last;

    public LinkedDeque() {
        size = 0;
        first = new Node(null);
        last = new Node(null);
    }

    private class Node {
        private T value;
        private Node next;
        private Node prev;

        Node(T value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    public void addFirst(T item) {
        Node newNode = new Node(item);
        newNode.next = first.next;
        if (first.next != null) {
            first.next.prev = newNode;
        } else {
            last.prev = newNode;
        }
        first.next = newNode;
        size += 1;
    }

    public void addLast(T item) {
        Node newNode = new Node(item);
        newNode.prev = last.prev;
        if (last.prev != null) {
            last.prev.next = newNode;
        } else {
            first.next = newNode;
        }
        last.prev = newNode;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node retNode = first.next;
        Node nextNode = first.next.next;
        if (nextNode != null) {
            nextNode.prev = null;
        } else {
            last.prev = null;
        }
        first.next = nextNode;
        size -= 1;
        return retNode.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node retNode = last.prev;
        Node prevNode = last.prev.prev;
        if (prevNode != null) {
            prevNode.next = null;
        } else {
            first.next = null;
        }
        last.prev = prevNode;
        size -= 1;
        return retNode.value;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node cur = first.next;
        while (index != 0) {
            cur = cur.next;
            index--;
        }
        return cur.value;
    }

    public int size() {
        return size;
    }
}
