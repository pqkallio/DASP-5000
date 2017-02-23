
package dasp5000.domain;

/**
 * A generic circular doubly linked list
 * 
 * @author Petri Kallio
 */
public class LinkedList<T> {
    private LinkedListNode<T> first;

    /**
     * Creates a new LinkedList object and adds the first node
     * 
     * @param first the object to add
     */
    public LinkedList(T first) {
        createFirstNode(first);
    }

    /**
     * Creates a new empty LinkedList object
     */
    public LinkedList() {
        this(null);
    }

    /**
     * Returns the linked list's first object
     * 
     * @return the first object on the list
     */
    public T getFirst() {
        if (first == null) {
            return null;
        }
        return first.getObject();
    }
    
    /**
     * Returns the linked list's first object
     * 
     * @return the first object on the list
     */
    public T getLast() {
        if (first == null) {
            return null;
        }
        LinkedListNode last = first.getPred();
        return (T)last.getObject();
    }
    
    /**
     * Adds a new object to the list, placing it as the last object on the list
     * 
     * @param object the object to add
     */
    public void add(T object) {
        if (first == null) {
            createFirstNode(object);
        } else {
            LinkedListNode last = first.getPred();
            LinkedListNode node = new LinkedListNode(object, first, last);
        }
    }
    
    /**
     * Deletes the given object from the list
     * 
     * @param object the object to delete
     */
    public void delete(T object) {
        if (first == null) {
            return;
        }
        LinkedListNode node = first;
        do {
            if (node.getObject().equals(object)) {
                LinkedListNode next = node.getSucc();
                LinkedListNode prev = node.getPred();
                if (node.equals(first)) {
                    if (next.equals(node)) {
                        first = null;
                    } else {
                        next.setPred(prev);
                        prev.setSucc(next);
                        first = next;
                    }
                } else {
                    next.setPred(prev);
                    prev.setSucc(next);
                }
                return;
            }
            node = node.getSucc();
        } while (!node.equals(first));
    }
    
    private void createFirstNode(T object) {
        if (object == null) {
            this.first = null;
        } else {
            LinkedListNode<T> node = new LinkedListNode(object, null, null);
            node.setPred(node);
            node.setSucc(node);
            this.first = node;
        }
    }
}
