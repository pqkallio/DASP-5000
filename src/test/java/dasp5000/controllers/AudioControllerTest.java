/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.controllers;

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
    private String outputFilePath;
    
    public AudioControllerTest() {
    }
    
    @Before
    public void setUp() throws UnsupportedAudioFileException, IOException {
        this.outputFilePath = ClassLoader.getSystemResource("test.wav")
                .getPath().split("test")[0] + "testoutput.wav";
        this.audioController = new AudioController(ClassLoader.getSystemResource("test.wav").getPath());
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
    
}
