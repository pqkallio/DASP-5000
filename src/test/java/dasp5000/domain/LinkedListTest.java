
package dasp5000.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pqkallio
 */
public class LinkedListTest {
    private LinkedList<String> stringList;
    private LinkedList<Integer> intList;
    
    @Before
    public void setUp() {
        this.stringList = new LinkedList<>();
        this.intList = new LinkedList<>();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void listsAreEmptyAtFirst() {
        assertNull(stringList.getFirst());
        assertNull(intList.getFirst());
    }
    
    @Test
    public void listsHaveOneObject() {
        this.stringList.add("Hopo");
        assertEquals("Hopo", stringList.getFirst());
        assertEquals("Hopo", stringList.getLast());
        
        this.intList.add(666);
        assertEquals(666, (int)intList.getFirst());
        assertEquals(666, (int)intList.getLast());
    }
    
    @Test
    public void listsHaveTwoObjects() {
        this.stringList.add("Hopo");
        this.stringList.add("Hessu");
        assertEquals("Hopo", stringList.getFirst());
        assertEquals("Hessu", stringList.getLast());
        
        this.intList.add(666);
        this.intList.add(667);
        assertEquals(666, (int)intList.getFirst());
        assertEquals(667, (int)intList.getLast());
    }
    
    @Test
    public void listsHaveThreeObjects() {
        this.stringList.add("Hopo");
        this.stringList.add("Mössö");
        this.stringList.add("Hessu");
        assertEquals("Hopo", stringList.getFirst());
        assertEquals("Hessu", stringList.getLast());
        
        this.intList.add(666);
        this.intList.add(667);
        assertEquals(666, (int)intList.getFirst());
        assertEquals(667, (int)intList.getLast());
    }
}
