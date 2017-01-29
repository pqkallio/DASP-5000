/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain;

import dasp5000.domain.AudioAnalysis;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pqkallio
 */
public class AudioAnalysisTest {
    private AudioAnalysis analysis;
    
    public AudioAnalysisTest() {
    }
    
    @Before
    public void setUp() {
        this.analysis = new AudioAnalysis(1, 2, 3);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetMinimumSampleValue() {
        assertEquals(analysis.getMinimumSampleValue(), 2);
    }

    @Test
    public void testGetPeakSampleValue() {
        assertEquals(analysis.getPeakSampleValue(), 1);
    }

    @Test
    public void testGetSamples() {
        assertEquals(analysis.getSamples(), 3);
    }
    
}
