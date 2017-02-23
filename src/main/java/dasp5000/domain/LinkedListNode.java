
package dasp5000.domain;

/**
 * A generic node of a LinkedList object
 * 
 * @author Petri Kallio
 */
public class LinkedListNode<T> {
    private T object;
    private LinkedListNode succ;
    private LinkedListNode pred;

    /**
     * Creates a new node and assigns the object given as parameter to it
     * 
     * @param object the object to add
     * @param succ the new node's successor
     * @param pred the new node's predecessor
     */
    public LinkedListNode(T object, LinkedListNode succ, LinkedListNode pred) {
        this.object = object;
        setSuccessor(succ);
        setPredecessor(pred);
    }

    /**
     * Get the object associated with the node
     * 
     * @return the node's object
     */
    public T getObject() {
        return object;
    }

    /**
     * Get the node's predecessor
     * 
     * @return the node's predecessor
     */
    public LinkedListNode getPred() {
        return pred;
    }

    /**
     * Get the node's successor
     * 
     * @return the node's successor
     */
    public LinkedListNode getSucc() {
        return succ;
    }

    /**
     * Set the node's predecessor
     * 
     * @param pred the new predecessor
     */
    public void setPred(LinkedListNode pred) {
        this.pred = pred;
    }

    /**
     * Set the node's successor
     * 
     * @param succ the new successor
     */
    public void setSucc(LinkedListNode succ) {
        this.succ = succ;
    }

    private void setSuccessor(LinkedListNode succ) {
        this.succ = succ;
        if (this.succ != null) {
            this.succ.setPred(this);
        }
    }
    
    private void setPredecessor(LinkedListNode pred) {
        this.pred = pred;
        if (this.pred != null) {
            this.pred.setSucc(this);
        }
    }
}
