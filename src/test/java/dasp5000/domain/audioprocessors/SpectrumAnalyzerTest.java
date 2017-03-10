
package dasp5000.domain.audioprocessors;

import dasp5000.controllers.AudioController;
import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.SpectrumAnalysisSample;
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
public class SpectrumAnalyzerTest {
    private AudioContainer ac;
    private SpectrumAnalyzer sa;
    
    @Before
    public void setUp() {
        AudioHeader ah = new AudioHeader("test", 2, 16, 16, 16, 16, 256);
        this.ac = new AudioContainer(ah);
        DynamicArray<Integer>[] channels = new DynamicArray[2];
        for (int i = 0; i < 2; i++) {
            channels[i] = new DynamicArray<>(Integer.class);
        }
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 2; j++) {
                channels[j].add(100);
            }
        }
        ac.setChannels(channels);
        this.sa = new SpectrumAnalyzer(ac);
    }
    
    @Test
    public void windowSizeIsCorrect() {
        assertEquals(sa.getWindowSize(), (int)Math.pow(2, 15));
    }
    
    @Test
    public void audioContainerIsCorrect() {
        assertEquals(sa.getAudioContainer(), this.ac);
    }
    
    @Test
    public void samplesPerChannelsIsCorrect() {
        assertEquals(sa.getSamplesPerChannel(), ac.getSamplesPerChannel());
    }
    
    @Test
    public void getAnalysisIsOk() {
        sa.process();
        SpectrumAnalysisSample[] samples = sa.getAnalysis();
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < samples.length; i++) {
            for (int j = 0; j < samples[i].getSamples().length; j++) {
                for (int k = 0; k < samples[i].getSamples()[j].getMagnitude().length; k++) {
                    if (samples[i].getSamples()[j].getMagnitude()[k] > max) {
                        max = samples[i].getSamples()[j].getMagnitude()[k];
                    }
                }
            }
        }
        assertEquals(max, 0, 0.1);
    }
}
