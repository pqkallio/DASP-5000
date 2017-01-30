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
    
    @Test
    public void ifSampleValueIsHalfTheMaxValueTheResultIs0dBFS() {
        assertEquals(DecibelConverter.sampleValueToDecibels(63556 / 2, 63556), 0, 0);
    }
    
    @Test
    public void ifSampleValueIsAQuarterOfTheMAxValueTheResultIsApproxMinus6dBFS() {
        assertEquals(DecibelConverter.sampleValueToDecibels(63556 / 4, 63556), -6, 0.1);
    }
    
    @Test
    public void ifdBFSValueIsZeroTheSampleValueEqualsMaxValueDividedByTwo() {
        assertEquals(DecibelConverter.dBFSToSampleValue(0, 63556), 63556 / 2);
    }
    
    @Test
    public void ifdBFSValueIsMinus6TheSampleValueApproximatesToMaxValueDividedByFour() {
        assertEquals(DecibelConverter.dBFSToSampleValue(-6.0, 63556), 15926);
    }
}
