
package dasp5000.domain;

import java.lang.reflect.Array;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

/**
 * A generic queue structure that enqueues data to the queue's end and
 * dequeues from the queues start.
 * 
 * @author Petri Kallio
 */
public class Queue<T> {
    private T[] queue;
    private int head;
    private int tail;
    private int size;

    /**
     * The constructor that defines the queues size.
     * 
     * @param objectClass the Class of objects that the queue contains
     * @param size the size of the queue
     */
    public Queue(Class<T> objectClass, int size) {
        this.size = size + 1;
        this.queue = (T[])Array.newInstance(objectClass, this.size);
        this.head = 0;
        this.tail = 0;
    }
    
    /**
     * Is the queue empty or not?
     * 
     * @return true if empty, false otherwise
     */
    public boolean empty() {
        return head == tail;
    }
    
    /**
     * Is the queue full?
     * 
     * @return true if full, false otherwise
     */
    public boolean full() {
        return (tail + 1) % queue.length == head;
    }
    
    /**
     * Add an object to the queues tail
     * @param object The object to add
     */
    public void enqueue(T object) {
        if (full()) {
            throw new BufferOverflowException();
        }
        queue[tail] = object;
        tail = (tail + 1) % size;
    }
    
    /**
     * Remove the first object from the queue.
     * @return the queue's first object
     */
    public T dequeue() {
        if (empty()) {
            throw new BufferUnderflowException();
        }
        T object = queue[head];
        head = (head + 1) % size;
        return object;
    }
}
