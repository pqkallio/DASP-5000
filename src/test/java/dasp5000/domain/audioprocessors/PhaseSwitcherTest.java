
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PhaseSwitcherTest {
    AudioHeader ah1;
    AudioHeader ah2;
    int[] originalMonoSamples;
    int[][] originalStereoSamples;
    AudioContainer mono1;
    AudioContainer stereo1;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.ah1 = new AudioHeader("test", 1, 44100, 3, 4, 16, 10);
        this.ah2 = new AudioHeader("test", 2, 44100, 3, 4, 16, 10);
        this.originalMonoSamples = new int[10];
        this.originalStereoSamples = new int[2][10];
        DynamicArray<Integer>[] monoChannel1 = createMonoData1();
        DynamicArray<Integer>[] stereoChannel1 = createStereoData1();
        this.mono1 = new AudioContainer(ah1);
        this.mono1.setChannels(monoChannel1);
        Analyzer.analyse(mono1);
        this.stereo1 = new AudioContainer(ah2);
        this.stereo1.setChannels(stereoChannel1);
        Analyzer.analyse(stereo1);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void phaseSwitchingMonoAudioWorks() {
        for (int i = 0; i < 10; i++) {
            assertEquals((int)mono1.getChannels()[0].get(i), originalMonoSamples[i]);
        }
        new PhaseSwitcher(mono1).process();
        for (int i = 0; i < 10; i++) {
            assertEquals((int)mono1.getChannels()[0].get(i), -1 * originalMonoSamples[i]);
        }
    }
    
    @Test
    public void phaseSwitchingStereoAudioWorks() {
        for (int i = 0; i < 10; i++) {
            assertEquals((int)stereo1.getChannels()[0].get(i), originalStereoSamples[0][i]);
            assertEquals((int)stereo1.getChannels()[1].get(i), originalStereoSamples[1][i]);
        }
        new PhaseSwitcher(stereo1).process();
        for (int i = 0; i < 10; i++) {
            assertEquals((int)stereo1.getChannels()[0].get(i), -1 * originalStereoSamples[0][i]);
            assertEquals((int)stereo1.getChannels()[1].get(i), -1 * originalStereoSamples[1][i]);
        }
    }

    private DynamicArray<Integer>[] createMonoData1() {
        DynamicArray<Integer>[] channels = new DynamicArray[1];
        channels[0] = new DynamicArray<>(Integer.class);
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                channels[0].add(i * 100);
                originalMonoSamples[i] = i * 100;
            } else {
                channels[0].add(-1 * i * 100);
                originalMonoSamples[i] = -1 * i * 100;
            }
        }
        return channels;
    }

    private DynamicArray<Integer>[] createStereoData1() {
        DynamicArray<Integer>[] channels = new DynamicArray[2];
        channels[0] = new DynamicArray<>(Integer.class);
        channels[1] = new DynamicArray<>(Integer.class);
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                channels[0].add(i * 100);
                channels[1].add((int)Math.pow(2, i));
                originalStereoSamples[0][i] = i * 100;
                originalStereoSamples[1][i] = (int)Math.pow(2, i);
            } else {
                channels[0].add(-1 * i * 100);
                channels[1].add(-1 * (int)Math.pow(2, i));
                originalStereoSamples[0][i] = -1 * i * 100;
                originalStereoSamples[1][i] = -1 * (int)Math.pow(2, i);
            }
        }
        return channels;
    }
}
