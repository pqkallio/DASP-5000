/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain.audioprocessors;

import dasp5000.obsoletestuff.audioprocessors.PhaseSwitcher;
import dasp5000.controllers.AudioController;
import dasp5000.domain.audiocontainers.AudioContainer;
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
public class PhaseSwitcherTest {

    private AudioContainer ac;
    private AudioContainer acControl;
    
    @Before
    public void setUp() throws UnsupportedAudioFileException, IOException {
        AudioController controller = new AudioController(ClassLoader.getSystemResource("test.wav").getPath());
        AudioController controller2 = new AudioController(ClassLoader.getSystemResource("test.wav").getPath());
        this.ac = controller.getAudioContainer();
        this.acControl = controller2.getAudioContainer();
    }
    
    @After
    public void tearDown() {
    }    
    
    @Test
    public void atFirstSamplesEqual() {
        PhaseSwitcher s = new PhaseSwitcher(ac);
        for (int i = 0; i < this.ac.getLeftChannel().size(); i++) {
            assertEquals((int)ac.getLeftChannel().get(i), 
                    (int)acControl.getLeftChannel().get(i));
        }
    }
    
    @Test
    public void samplesAreSwitched() {
        PhaseSwitcher s = new PhaseSwitcher(ac);
        s.process();
        for (int i = 0; i < this.ac.getLeftChannel().size(); i++) {
            int controlSample = -1 * acControl.getLeftChannel().get(i);
            assertEquals((int)ac.getLeftChannel().get(i), 
                    controlSample);
        }
    }
}
