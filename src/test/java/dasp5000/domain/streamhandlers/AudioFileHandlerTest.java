/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain.streamhandlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
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
public class AudioFileHandlerTest {
    
    public AudioFileHandlerTest() {
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

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionIfFileNameIsNull() throws FileNotFoundException {
        new AudioFileHandler(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExceptionIfFileNameIsEmpty() throws FileNotFoundException {
        new AudioFileHandler("");
    }
    
    @Test(expected = FileNotFoundException.class)
    public void fileNotFoundExceptionIfFileNotValid() throws UnsupportedAudioFileException, IOException {
        new AudioFileHandler("test");
    }
}
