package rit.cs;

/**
 * An interface to a generic queue (FIFO).
 * @param <T> The type of data the queue will hold
 * @author Sean Strout @ RIT CS
 */
public interface Queue<T> {
    /**
     * Get the last element in the queue.
     * @rit.pre queue must not be empty
     * @throws AssertionError if queue empty
     * @return the back element
     */
    public T back();

    /**
     * Remove and return the front element in the queue.
     * @rit.pre queue must not be empty
     * @throws AssertionError if queue empty
     * @return the front element
     */
    public T dequeue();

    /**
     * Check if the queue is currently empty or not.
     * @return true if empty, false otherwise
     */
    public boolean empty();

    /**
     * Add a new element to the back of the queue.
     * @param element The new data element
     */
    public void enqueue(T element);

    /**
     * Get the front element in the queue.
     * @rit.pre queue must not be empty
     * @throws AssertionError if queue empty
     * @return the front element
     */
    public T front();
}
