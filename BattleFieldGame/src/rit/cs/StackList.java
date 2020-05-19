package rit.cs;

import java.util.LinkedList;
import java.util.List;

/**
 * A stack implementation that uses a Java List to represent the structure.
 * @param <T> The type of data the stack will hold
 * @author Sean Strout @ RIT CS
 */
public class StackList<T> implements Stack<T> {
    /** The underlying implementation of the stack is a List */
    private List<T> stack;
    /**
     * Create an empty stack.
     */
    public StackList(){
            this.stack = new LinkedList<>();
    }

    @Override
    public boolean empty() {
        return this.stack.size() == 0;
    }

    @Override
    public T pop() {
        assert !empty();
        T element = this.stack.get(0);
        this.stack.remove(0);
        return element;
    }

    @Override
    public void push(T element) {
        this.stack.add(0, element);
    }

    @Override
    public T top() {
        assert !empty();
        return this.stack.get(0);
    }
}
