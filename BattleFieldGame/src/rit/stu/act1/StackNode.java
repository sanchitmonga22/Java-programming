package rit.stu.act1;
import rit.cs.Node;
import rit.cs.Stack;
/**
 * A stack implementation that uses a Node to represent the structure.
 * @param <T> The type of data the stack will hold
 * @author Sean Strout @ RIT CS
 * @author Sanchit Monga
 */
public class StackNode<T> implements Stack<T> {
    /**
     * Create an empty stack.
     */
    private Node<T> stack;
    public StackNode() {
        this.stack=new Node(null,null);
    }
    /**
     *Checking whether the stack is full or not
     */
    @Override
    public boolean empty() {
        if(this.stack.getData()==null){
            return true;
        }
        return false;
    }

    /**
     * Returns the element
     * @return returns the element removed from the front of the stack
     */
    @Override
    public T pop() {
        assert !empty();
        T element = this.stack.getData();
        Node<T> node=this.stack.getNext();
        this.stack=node;
        return element;
    }
    /**
     * Pushes the lement on the top of the stack
     * @param element The new data element: Adds the given element on the top of the stack
     */
    @Override
    public void push(T element) {
        Node<T> node = new Node(element, this.stack);
        this.stack = node;
    }
    /**
     *
     * @return The top elememnt of the stack
     */
    @Override
    public T top() {
        assert !empty();
        return (this.stack.getData());
    }
}
