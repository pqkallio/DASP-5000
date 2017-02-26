/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pqkallio
 */
public class AudioProcessorTest {
    private APAImp ap;
    private AudioContainer ac;
    private AudioHeader ah;
    private DynamicArray<Integer>[] channels;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.ah = new AudioHeader("test", 2, 2, 3, 4, 5, 10);
        this.ac = new AudioContainer(ah);
        this.channels = createChannels();
        this.ac.setChannels(channels);
        this.ap = new APAImp(this.ac);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExIfNoAudioContainers() {
        new APAImp();
    }
    
    @Test
    public void samplesEqualsTheNumberOfTheBeast() {
        assertEquals(ap.samples(), channels[0].size());
    }
    
    @Test
    public void unprocessedAllIsFine() {
        for (int i = 0; i < ac.getChannels().length; i++) {
            for (int j = 0; j < ac.getChannels()[i].size(); j++) {
                assertEquals((int)ac.getChannels()[i].get(j), j);
            }
        }
    }
    
    @Test
    public void processedAllIsFine() {
        ap.process();
        for (int i = 0; i < ac.getChannels().length; i++) {
            for (int j = 0; j < ac.getChannels()[i].size(); j++) {
                assertEquals((int)ac.getChannels()[i].get(j), 125);
            }
        }
    }

    private DynamicArray<Integer>[] createChannels() {
        DynamicArray<Integer>[] c = new DynamicArray[2];
        c[0] = new DynamicArray<>(Integer.class);
        c[1] = new DynamicArray<>(Integer.class);
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < 10; j++) {
                c[i].add(j);
            }
        }
        return c;
    }
    
    private class APAImp extends AudioProcessor {

        public APAImp(AudioContainer... acs) {
            super(acs);
        }
        
        @Override
        protected void processSample(int sampleIndex, int channelIndex, int... samples) {
            super.audioContainers[0].getChannels()[channelIndex].replace(sampleIndex, 125);
        }

        @Override
        protected void analyseAudio() {
            
        }

        @Override
        protected int samples() {
            return super.audioContainers[0].getSamplesPerChannel();
        }
        
    }
}
