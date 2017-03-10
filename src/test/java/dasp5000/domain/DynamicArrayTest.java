/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pqkallio
 */
public class DynamicArrayTest {
    private DynamicArray<Integer> dynamicArray;
    
    public DynamicArrayTest() {
    }
    
    @Before
    public void setUp() {
        this.dynamicArray = new DynamicArray<>(Integer.class);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void atFirstTheArrayIsEmpty() {
        assertEquals(dynamicArray.size(), 0);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void indexOutOfBoundsExceptionIfIndexIsLessThanZero() {
        dynamicArray.get(-1);
    }
    
    @Test
    public void afterAddingAnElementTheSizeIsUpdated() {
        assertEquals(dynamicArray.size(), 0);
        dynamicArray.add(1);
        assertEquals(dynamicArray.size(), 1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void indexOutOfBoundsExceptionIfIndexEqualsSize() {
        dynamicArray.add(1);
        assertEquals(dynamicArray.size(), 1);
        dynamicArray.get(dynamicArray.size());
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void indexOutOfBoundsExceptionIfIndexGreaterThanSize() {
        dynamicArray.add(1);
        assertEquals(dynamicArray.size(), 1);
        dynamicArray.get(dynamicArray.size() + 1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void replacingToInvalidIndexCausesIndexOutOfBoundsException1() {
        dynamicArray.replace(-1, 0);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void replacingToInvalidIndexCausesIndexOutOfBoundsException2() {
        dynamicArray.replace(0, 0);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void replacingToInvalidIndexCausesIndexOutOfBoundsException3() {
        dynamicArray.replace(1, 0);
    }
}
