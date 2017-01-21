/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.utils;

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
public class DecibelConverterTest {
    
    public DecibelConverterTest() {
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
     * Test of sampleValueToDecibels method, of class DecibelConverter.
     */
    @Test
    public void testSampleValueToDecibels() {
        System.out.println("sampleValueToDecibels");
        int sampleValue = 0;
        int maxValue = 0;
        double expResult = 0.0;
        double result = DecibelConverter.sampleValueToDecibels(sampleValue, maxValue);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dBFSToSampleValue method, of class DecibelConverter.
     */
    @Test
    public void testDBFSToSampleValue() {
        System.out.println("dBFSToSampleValue");
        double dBFS = 0.0;
        int maxValue = 0;
        double expResult = 0.0;
        double result = DecibelConverter.dBFSToSampleValue(dBFS, maxValue);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
