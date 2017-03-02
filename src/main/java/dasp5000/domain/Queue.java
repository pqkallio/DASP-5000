
package dasp5000.domain;

import java.lang.reflect.Array;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

/**
 *
 * @author pqkallio
 */
public class Queue<T> {
    private T[] queue;
    private int head;
    private int tail;
    private int size;

    public Queue(Class<T> objectClass, int size) {
        this.size = size + 1;
        this.queue = (T[])Array.newInstance(objectClass, this.size);
        this.head = 0;
        this.tail = 0;
    }
    
    public boolean empty() {
        return head == tail;
    }
    
    public boolean full() {
        return (tail + 1) % queue.length == head;
    }
    
    public void enqueue(T object) {
        if (full()) {
            throw new BufferOverflowException();
        }
        queue[tail] = object;
        tail = (tail + 1) % size;
    }
    
    public T dequeue() {
        if (empty()) {
            throw new BufferUnderflowException();
        }
        T object = queue[head];
        head = (head + 1) % size;
        return object;
    }
}
