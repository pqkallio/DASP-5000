/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain.audioprocessors;

import dasp5000.obsoletestuff.audioprocessors.Normalizer;
import dasp5000.controllers.AudioController;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.utils.DecibelConverter;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pqkallio
 */
public class NormalizerTest {
    private AudioContainer ac;
    
    @Before
    public void setUp() throws UnsupportedAudioFileException, IOException {
        AudioController controller = new AudioController(ClassLoader.getSystemResource("test.wav").getPath());
        this.ac = controller.getAudioContainer();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void normalizingWorks() {
        Normalizer n = new Normalizer(ac, -0.5);
        double dbfsNow = DecibelConverter.sampleValueToDecibels(
                ac.getAudioAnalysis().getPeakSampleValue(), 
                (int)Math.pow(2, ac.getBitsPerAudioSample()));
        assertNotEquals(dbfsNow, -0.5, 0.1);
        n.process();
        dbfsNow = DecibelConverter.sampleValueToDecibels(
                ac.getAudioAnalysis().getPeakSampleValue(), 
                (int)Math.pow(2, ac.getBitsPerAudioSample()));
        assertEquals(dbfsNow, -0.5, 0.1);
    }
    
    @Test
    public void normalizingWorks2() {
        Normalizer n = new Normalizer(ac, -0.5);
        double dbfsNow = DecibelConverter.sampleValueToDecibels(
                ac.getAudioAnalysis().getPeakSampleValue(), 
                (int)Math.pow(2, ac.getBitsPerAudioSample()));
        assertNotEquals(dbfsNow, -0.5, 0.1);
        n.setdBFSMaxLevel(-16.0);
        assertNotEquals(dbfsNow, -16.0, 0.1);
        n.process();
        dbfsNow = DecibelConverter.sampleValueToDecibels(
                ac.getAudioAnalysis().getPeakSampleValue(), 
                (int)Math.pow(2, ac.getBitsPerAudioSample()));
        assertEquals(dbfsNow, -16.0, 0.1);
    }
}
