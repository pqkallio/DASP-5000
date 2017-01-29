/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.DynamicArray;
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
    private Analyzer analyzer;
    private DynamicArray<Integer> data;
    private DynamicArray<Integer> single;
    
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
        this.analyzer = new Analyzer();
        this.data = new DynamicArray<>(Integer.class);
        this.data.add(-3);
        this.data.add(87);
        this.data.add(15);
        this.data.add(-100);
        this.data.add(1000);
        this.data.add(10);
        this.single = new DynamicArray<>(Integer.class);
        this.single.add(0);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionIfParameterForAnalyseIsNull() {
        AudioAnalysis analysis = this.analyzer.analyse(null);
    }
    
    @Test
    public void thePeakValueIsMinimumForEmptyParameter() {
        AudioAnalysis analysis = this.analyzer.analyse(new DynamicArray<Integer>(Integer.class));
        assertEquals(analysis.getPeakSampleValue(), Integer.MIN_VALUE);
    }
    
    @Test
    public void theMinimumValueIsMaximumForEmptyParameter() {
        AudioAnalysis analysis = this.analyzer.analyse(new DynamicArray<Integer>(Integer.class));
        assertEquals(analysis.getMinimumSampleValue(), Integer.MAX_VALUE);
    }
    
    @Test
    public void theSamplesValueIsZeroForEmptyParameter() {
        AudioAnalysis analysis = this.analyzer.analyse(new DynamicArray<Integer>(Integer.class));
        assertEquals(analysis.getSamples(), 0);
    }
    
    @Test
    public void thePeakValueIsSetProperlyForPopulatedArray() {
        AudioAnalysis analysis = this.analyzer.analyse(data);
        assertEquals(analysis.getPeakSampleValue(), 1000);
    }
    
    @Test
    public void theMinimumValueIsSetProperlyForPopulatedArray() {
        AudioAnalysis analysis = this.analyzer.analyse(data);
        assertEquals(analysis.getMinimumSampleValue(), 3);
    }
    
    @Test
    public void theSamplesValueIsProperlySetForPopulatedArray() {
        AudioAnalysis analysis = this.analyzer.analyse(data);
        assertEquals(analysis.getSamples(), 6);
    }
    
    @Test
    public void thePeakValueIsSetProperlyForSingleValueArray() {
        AudioAnalysis analysis = this.analyzer.analyse(single);
        assertEquals(analysis.getPeakSampleValue(), 0);
    }
    
    @Test
    public void theMinimumValueIsSetProperlyForSingleValueArray() {
        AudioAnalysis analysis = this.analyzer.analyse(single);
        assertEquals(analysis.getMinimumSampleValue(), 0);
    }
    
    @Test
    public void theSamplesValueIsProperlySetForSingleValueArray() {
        AudioAnalysis analysis = this.analyzer.analyse(single);
        assertEquals(analysis.getSamples(), 1);
    }

    @Test
    public void testAnalyse() {
        System.out.println("analyse");
        DynamicArray<Integer> words = null;
        AudioAnalysis expResult = null;
        AudioAnalysis result = Analyzer.analyse(words);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}
