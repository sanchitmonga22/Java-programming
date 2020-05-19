package rit.cs;

/**
 * An interface to a generic stack (FILO).
 * @param <T> The type of data the stack will hold
 * @author Sean Strout @ RIT CS
 */
public interface Stack<T> {

    /**
     * Check if the stack is currently empty or not.
     * @return true if empty, false otherwise
     */
    public boolean empty();
    /**
     * Remove and return the top element in the stack.
     * @rit.pre stack must not be empty
     * @throws AssertionError if stack empty
     * @return the front element
     */
    public T pop();

    /**
     * Add a new element to the top of the stack.
     * @param element The new data element
     */
    public void push(T element);

    /**
     * Get the top element on the stack.
     * @rit.pre stack must not be empty
     * @throws AssertionError if stack empty
     * @return The top element
     */
    public T top();
}
