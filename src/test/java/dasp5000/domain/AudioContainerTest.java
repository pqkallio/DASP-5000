/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain;

import dasp5000.domain.audiocontainers.MonoAudio;
import javax.sound.sampled.AudioFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pqkallio
 */
public class AudioContainerTest {
    private MonoAudio audioContainer;
    private AudioFormat audioFormat;
    private AudioAnalysis audioAnalysis;
    private DynamicArray<Integer> words;
    
    public AudioContainerTest() {
    }
    
    @Before
    public void setUp() {
        this.audioFormat = new AudioFormat(44100, 16, 2, true, false);
        this.audioContainer = new MonoAudio(audioFormat);
        this.audioAnalysis = new AudioAnalysis(1, 2, 3);
        this.words = new DynamicArray<>(Integer.class);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void audioFormatIsCorrectlySet() {
        assertEquals(audioFormat, audioContainer.getAudioFormat());
    }
    
    @Test
    public void firstAudioAnalysisIsNull() {
        assertNull(audioContainer.getAudioAnalysis());
    }
    
    @Test
    public void testSetAudioAnalysis() {
        audioContainer.setAudioAnalysis(audioAnalysis);
        assertEquals(audioContainer.getAudioAnalysis(), audioAnalysis);
    }

    @Test
    public void firstGetWordsReturnsNull() {
        assertNull(audioContainer.getLeftChannel());
    }
    
    @Test
    public void testSetWords() {
        DynamicArray<Integer>[] data = new DynamicArray[1];
        data[0] = words;
        audioContainer.setChannels(data);
        assertEquals(audioContainer.getLeftChannel(), words);
    }

    @Test
    public void testGetNumberOfChannels() {
        assertEquals(audioContainer.getNumberOfChannels(), 2);
    }

    @Test
    public void testGetBitDepth() {
        assertEquals(audioContainer.getBitsPerAudioSample(), 16);
    }

    @Test
    public void testGetSampleRate() {
        assertEquals(audioContainer.getSampleRate(), 44100.0, 0);
    }

    @Test
    public void testIsBigEndian() {
        assertFalse(audioContainer.isBigEndian());
    }

    @Test
    public void testGetAudioFormat() {
        assertEquals(audioContainer.getAudioFormat(), audioFormat);
    }
    
}
