package dasp5000.domain;

import java.nio.BufferUnderflowException;
import java.nio.BufferOverflowException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author pqkallio
 */
public class QueueTest {
    private Queue<String> q;
    private int queueSize;

    @Before
    public void setUp() {
        this.queueSize = 5;
        this.q = new Queue(String.class, queueSize);
    }
    
    @Test
    public void atFirstItsEmpty() {
        assertTrue(q.empty());
    }
    
    @Test
    public void notEmptyAnymore() {
        q.enqueue("Hoh");
        assertFalse(q.empty());
    }
    
    @Test
    public void notFullUntilFull() {
        for (int i = 0; i < 5; i++) {
            assertFalse(q.full());
            q.enqueue("Hoh");
        }
        assertTrue(q.full());
    }
    
    @Test
    public void notEmptyUntilEmpty() {
        String pre = "Hoh";
        for (int i = 0; i < 5; i++) {
            String string = pre + i;
            q.enqueue(string);
        }
        for (int i = 0; i < 5; i++) {
            assertFalse(q.empty());
            q.dequeue();
        }
        assertTrue(q.empty());
    }
    
    @Test
    public void theQueueIsEmptietFromTheHead() {
        String pre = "Hoh";
        for (int i = 0; i < 5; i++) {
            String string = pre + i;
            q.enqueue(string);
        }
        for (int i = 0; i < 5; i++) {
            String string = q.dequeue();
            assertEquals(string, pre + i);
        }
        assertTrue(q.empty());
    }
    
    @Test(expected = BufferUnderflowException.class)
    public void bufferUnderflowExceptionIfNothingInQueueV1() {
        q.dequeue();
    }
    @Test(expected = BufferUnderflowException.class)
    public void bufferUnderflowExceptionIfNothingInQueueV2() {
        q.enqueue("hoho");
        q.dequeue();
        q.dequeue();
    }
    @Test(expected = BufferOverflowException.class)
    public void bufferOverflowExceptionIfQueueFull() {
        for (int i = 0; i < 6; i++) {
            q.enqueue("hoho");
        }
    }
}
