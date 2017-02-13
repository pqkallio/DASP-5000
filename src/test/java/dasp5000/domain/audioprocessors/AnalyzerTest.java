/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain.audioprocessors;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import javax.sound.sampled.AudioFormat;
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
    private DynamicArray<Integer> data;
    private DynamicArray<Integer> single;
    private AudioContainer audioContainer;
    private DynamicArray<Integer>[] channels;
    
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
        this.data = new DynamicArray<>(Integer.class);
        this.data.add(-3);
        this.data.add(87);
        this.data.add(15);
        this.data.add(-100);
        this.data.add(1000);
        this.data.add(10);
        this.single = new DynamicArray<>(Integer.class);
        this.single.add(0);
        this.audioContainer = new AudioContainer(new AudioFormat(44100, 16, 1, true, false));
        this.channels = new DynamicArray[1];
    }
    
    @After
    public void tearDown() {
    }
    
    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionIfParameterForAnalyseIsNull() {
        Analyzer.analyse(null);
    }
    
    @Test
    public void thePeakValueIsMinimumForEmptyParameter() {
        channels[0] = new DynamicArray<>(Integer.class);
        this.audioContainer.setChannels(channels);
        Analyzer.analyse(this.audioContainer);
        assertEquals(this.audioContainer.getAudioAnalysis().getPeakSampleValue(), Integer.MIN_VALUE);
    }
    
    @Test
    public void theMinimumValueIsMaximumForEmptyParameter() {
        channels[0] = new DynamicArray<>(Integer.class);
        this.audioContainer.setChannels(channels);
        Analyzer.analyse(this.audioContainer);
        assertEquals(audioContainer.getAudioAnalysis().getMinimumSampleValue(), Integer.MAX_VALUE);
    }
    
    @Test
    public void theSamplesValueIsZeroForEmptyParameter() {
        channels[0] = new DynamicArray<>(Integer.class);
        this.audioContainer.setChannels(channels);
        Analyzer.analyse(this.audioContainer);
        assertEquals(audioContainer.getAudioAnalysis().getSamples(), 0);
    }
    
    @Test
    public void thePeakValueIsSetProperlyForPopulatedArray() {
        channels[0] = data;
        this.audioContainer.setChannels(channels);
        Analyzer.analyse(this.audioContainer);
        assertEquals(this.audioContainer.getAudioAnalysis().getPeakSampleValue(), 1000);
    }
    
    @Test
    public void theMinimumValueIsSetProperlyForPopulatedArray() {
        channels[0] = data;
        this.audioContainer.setChannels(channels);
        Analyzer.analyse(this.audioContainer);
        assertEquals(audioContainer.getAudioAnalysis().getMinimumSampleValue(), 3);
    }
    
    @Test
    public void theSamplesValueIsProperlySetForPopulatedArray() {
        channels[0] = data;
        this.audioContainer.setChannels(channels);
        Analyzer.analyse(this.audioContainer);
        assertEquals(audioContainer.getAudioAnalysis().getSamples(), 6);
    }
    
    @Test
    public void thePeakValueIsSetProperlyForSingleValueArray() {
        channels[0] = single;
        this.audioContainer.setChannels(channels);
        Analyzer.analyse(this.audioContainer);
        assertEquals(audioContainer.getAudioAnalysis().getPeakSampleValue(), 0);
    }
    
    @Test
    public void theMinimumValueIsSetProperlyForSingleValueArray() {
        channels[0] = single;
        this.audioContainer.setChannels(channels);
        Analyzer.analyse(this.audioContainer);
        assertEquals(audioContainer.getAudioAnalysis().getMinimumSampleValue(), 0);
    }
    
    @Test
    public void theSamplesValueIsProperlySetForSingleValueArray() {
        channels[0] = single;
        this.audioContainer.setChannels(channels);
        Analyzer.analyse(this.audioContainer);
        assertEquals(audioContainer.getAudioAnalysis().getSamples(), 1);
    }

}
