/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author pqkallio
 */
public class AudioHeaderTest {
    private AudioHeader ah;
    
    @Before
    public void setUp() {
        this.ah = new AudioHeader("test", 1, 2, 3, 4, 5, 6);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void bitsPerSampleIsCorrectlySet() {
        assertEquals(ah.getBitsPerSample(), 5);
    }
    @Test
    public void formatIsCorrectlySet() {
        assertEquals(ah.getFormat(), "test");
    }
    @Test
    public void numChannelsIsCorrectlySet() {
        assertEquals(ah.getNumberOfChannels(), 1);
    }
    @Test
    public void sampleRateIsCorrectlySet() {
        assertEquals(ah.getSampleRate(), 2);
    }
    @Test
    public void byteRateIsCorrectlySet() {
        assertEquals(ah.getByteRate(), 3);
    }
    @Test
    public void blockAlignIsCorrectlySet() {
        assertEquals(ah.getBlockAlign(), 4);
    }
    @Test
    public void dataLengthIsCorrectlySet() {
        assertEquals(ah.getDataLengthInBytes(), 6);
    }
}
