package rit.cs;

import java.util.LinkedList;
import java.util.List;

/**
 * A queue implementation that uses a Java List to represent the structure.
 * @param <T> The type of data the queue will hold
 * @author Sean Strout @ RIT CS
 */
public class QueueList<T> implements Queue<T> {
    /** The underlying implementation of the queue is a List */
    private List<T> queue;

    /**
     * Create an empty queue.
     */
    public QueueList() {
        this.queue = new LinkedList<>();
    }

    @Override
    public T back() {
        assert !empty();
        return this.queue.get(this.queue.size() - 1);
    }

    @Override
    public T dequeue() {
        assert !empty();
        T element = this.queue.get(0);
        this.queue.remove(0);
        return element;
    }

    @Override
    public boolean empty() {
        return this.queue.size() == 0;
    }

    @Override
    public void enqueue(T element) {
        this.queue.add(element);
    }

    @Override
    public T front() {
        assert !empty();
        return this.queue.get(0);
    }
}
