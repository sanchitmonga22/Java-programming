package rit.cs;

/**
 * A singly linked node that holds a generic type of data.
 * @param <T> The user's data
 * @author Sean Strout @ RIT CS
 */
public class Node<T> {
    /** User data */
    private T data;
    /** Next node link */
    private Node<T> next;
    /**
     * Construct a new Node object.
     * @param data The user data to be stored
     * @param next The link to the next Node (null if none)
     */
    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }
    /**
     * Get the successor Node.
     * @return the next Node (null if none)
     */
    public Node<T> getNext() {
        return next;
    }
    /**
     * Get the Node's data.
     * @return the user data
     */
    public T getData() {
        return data;
    }
    /**
     * Change the Node's successor.
     * @param next the Node's new next link
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * Change the Node's data.
     * @param data the Node's new data
     */
    public void setData(T data) {
        this.data = data;
    }
}