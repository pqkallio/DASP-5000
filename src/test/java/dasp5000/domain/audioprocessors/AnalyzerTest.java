/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain.audioprocessors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author petrikallio
 */
public class AnalyzerTest {
    
    public AnalyzerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Process method, of class Analyzer.
     */
    @Test
    public void testProcess() {
        System.out.println("Process");
        byte[] bytes = null;
        Analyzer instance = new Analyzer();
        instance.Process(bytes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPeakValue method, of class Analyzer.
     */
    @Test
    public void testGetPeakValue() {
        System.out.println("getPeakValue");
        Analyzer instance = new Analyzer();
        int expResult = 0;
        int result = instance.getPeakValue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinValue method, of class Analyzer.
     */
    @Test
    public void testGetMinValue() {
        System.out.println("getMinValue");
        Analyzer instance = new Analyzer();
        int expResult = 0;
        int result = instance.getMinValue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRMS method, of class Analyzer.
     */
    @Test
    public void testGetRMS() {
        System.out.println("getRMS");
        Analyzer instance = new Analyzer();
        int expResult = 0;
        int result = instance.getRMS();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
