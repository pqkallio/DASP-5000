/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.controllers;

import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import java.io.File;
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
public class AudioControllerTest {
    private AudioController audioController;
    private AudioController acFromAudioContainer;
    private String outputFilePath;
    
    public AudioControllerTest() {
    }
    
    @Before
    public void setUp() throws UnsupportedAudioFileException, IOException {
        this.outputFilePath = ClassLoader.getSystemResource("test.wav")
                .getPath().split("test")[0] + "testoutput.wav";
        this.audioController = new AudioController(ClassLoader.getSystemResource("test.wav").getPath());
        AudioHeader ah = new AudioHeader("test", 2, 16, 16, 16, 16, 100);
        AudioContainer auCo = new AudioContainer(ah);
        DynamicArray<Integer>[] channels = new DynamicArray[2];
        for (int i = 0; i < 2; i++) {
            channels[i] = new DynamicArray<>(Integer.class);
        }
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < channels.length; j++) {
                channels[j].add(100);
            }
        }
        auCo.setChannels(channels);
        this.acFromAudioContainer = new AudioController(auCo);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void afterSetupAudioContainerIsNotNull() {
        assertNotNull(audioController.getAudioContainer());
    }
    
    @Test
    public void writingToFileSucceeds() throws UnsupportedAudioFileException, IOException {
        File output = new File(outputFilePath);
        if (output.exists()) {
            output.delete();
        }
        assertFalse(output.exists());
        audioController.writeToFile(outputFilePath);
        assertTrue(output.exists());
    }
    
    @Test
    public void audioAnalysisDone() {
        assertNotNull(this.acFromAudioContainer.getAudioContainer().getAudioAnalysis());
    }
    
    @Test
    public void withFileParameterAllIsFine() throws IOException, UnsupportedAudioFileException {
        File file = new File(ClassLoader.getSystemResource("test.wav").getPath());
        AudioController acFromFile = new AudioController(file);
        assertNotNull(acFromFile.getAudioContainer().getAudioAnalysis());
    }
    
    @Test
    public void filenameOk1() {
        assertEquals(audioController.getFileName(), ClassLoader.getSystemResource("test.wav").getPath());
    }
    
    @Test
    public void filenameOk2() throws IOException, UnsupportedAudioFileException {
        File file = new File(ClassLoader.getSystemResource("test.wav").getPath());
        AudioController acFromFile = new AudioController(file);
        assertEquals(acFromFile.getFileName(), ClassLoader.getSystemResource("test.wav").getPath());
    }
    
    @Test
    public void filenameOk3() {
        assertNull(acFromAudioContainer.getFileName());
    }
}
