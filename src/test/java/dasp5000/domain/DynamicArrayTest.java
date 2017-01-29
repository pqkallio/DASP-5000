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
    
    public DynamicArrayTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAdd() {
        System.out.println("add");
        Object object = null;
        DynamicArray instance = null;
        instance.add(object);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGet() {
        System.out.println("get");
        int index = 0;
        DynamicArray instance = null;
        Object expResult = null;
        Object result = instance.get(index);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSize() {
        System.out.println("size");
        DynamicArray instance = null;
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
