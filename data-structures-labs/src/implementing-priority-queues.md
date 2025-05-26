# Implementing Priority Queues

For this lab, you will complete a basic priority queue implementation.
Copy-and-paste the following file into `PriorityQueue.java`, complete the definitions, and submit it to Gradescope when you are done!

~~~java
/**
 * A priority queue is a queue that maintains efficient access to the maximum
 * element of the queue.
 */
public class PriorityQueue<T extends Comparable<? super T>> {
    private static final int INITIAL_CAPACITY = 16;
    private T[] data;
    private int size;
  
    /**
     * Creates a new, empty priority queue.
     * @param capacity
     */
    @SuppressWarnings("unchecked")
    public PriorityQueue() {
        data = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            @SuppressWarnings("unchecked")
            T[] newData = (T[]) new Comparable[data.length * 2];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    // Part 1: Implement the following helper methods that help with
    // access parents and children in the heap.

    /** @return the index of the left child of the node at the given inex. */
    private static int getLeftChild(int index) {
        // TODO: implement me!
        return -1;
    }

    /** @return the index of the right child of the node at the given inex. */
    private static int getRightChild(int index) {
        // TODO: implement me!
        return -1;
    }

    /** @return the index of the parent of the node at the given index. */
    private static int getParent(int index) {
        // TOOD: implement me!
        return -1;
    }

    /** @return the number of elements in the queue */
    public int size() {
        return size;
    }

    /** @return the maximum element in the queue */
    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("Priority queue is empty");
        } else {
            return data[0];
        }
    }

    // Part 2: Implement add(T v) that adds v to the queue, respecting the
    // heap property. You'll need to implement a "percolate up" helper method
    // along the way!

    /**
     * Adds value v to this priority queue.
     * @param v the value to add */
    public void add(T v) {
        // TODO: implement me!
    }

    // Part 3: Implement poll() that removes and returns the maximum element
    // in the queue, maintaining the heap property of the queue. You'll also
    // need to implement a "percolate down" helper, too.

    /** Removes and returns the maximum element of this priority queue.  */
    public T poll() {
        // TODO: implement me!
        return null;
    }

    // Part 4: Write a main method that demonstrates that your priority
    // queue works! Try to cover a variety of use cases in your driver.
    public static void main(String[] args) {
        // TODO: implement me!
    }
}
~~~