package rit.stu.act1;
import rit.cs.Node;
import rit.cs.Queue;
/**
 * A queue implementation that uses a Node to represent the structure.
 * @param <T> The type of data the queue will hold
 * @author Sean Strout @ RIT CS
 * @author Sanchit Monga
 */
public class QueueNode<T> implements Queue<T> {
    private Node<T> queue;// represents the front of the queue
    private Node<T> back;// represents the back node of the queue
    /**
     * Create an empty queue.
     */
    public QueueNode() {
        this.queue=new Node(null,null);
        this.back=new Node(null,null);
    }

    /**
     *
     * @return: the back element of the queue
     */
    @Override
    public T back()
    {
        assert !empty();
        return this.back.getData();
    }
    /**
     *
     * @return returns and removes the last element of the queue
     */
    @Override
    public T dequeue() {
        assert !empty();
        T element = this.queue.getData();
        this.queue=this.queue.getNext();
        return element;
    }
    /**
     *
     * @return : whether the  queue is empty or not
     */
    @Override
    public boolean empty() {
        if(this.queue==null){
            return true;
        }
        else if(this.queue.getData()==null){
            return true;
        }
        return false;
    }
    /**
     *
     * @param element The new data element: Adds the given element at the end of the queue
     */
    @Override
    public void enqueue(T element) {
        Node<T> node=new Node<T>(element,null);
        if (empty()) {
            this.queue=node;
        }
        else{
            this.back.setNext(node);
        }
        this.back=node;
    }
    /**
     *
     * @return : Returns the front  element of the queue but doensnt remove it
     */
    @Override
    public T front() {
        assert !empty();
        return this.queue.getData();
    }
}
