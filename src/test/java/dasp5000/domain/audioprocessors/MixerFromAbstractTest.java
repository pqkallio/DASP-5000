
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

public class MixerFromAbstractTest {
    AudioHeader ah1;
    AudioHeader ah2;
    AudioHeader ah3;
    AudioContainer mono1;
    AudioContainer stereo1;
    AudioContainer stereo2;
    MixerFromAbstract m;
    
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
        this.ah3 = new AudioHeader("test", 2, 44100, 3, 4, 16, 10);
        DynamicArray<Integer>[] monoChannel1 = createMonoData1();
        DynamicArray<Integer>[] monoChannel2 = createMonoData2();
        DynamicArray<Integer>[] stereoChannel1 = createStereoData1();
        DynamicArray<Integer>[] stereoChannel2 = createStereoData2();
        this.mono1 = new AudioContainer(ah1);
        this.mono1.setChannels(monoChannel1);
        Analyzer.analyse(mono1);
        this.stereo1 = new AudioContainer(ah2);
        this.stereo1.setChannels(stereoChannel1);
        Analyzer.analyse(stereo1);
        this.stereo2 = new AudioContainer(ah3);
        this.stereo2.setChannels(stereoChannel2);
        Analyzer.analyse(stereo2);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void oneChannelAloneMixesOk() {
        MixerFromAbstract m = new MixerFromAbstract(mono1);
        m.process();
        AudioContainer ac = m.getMix();
        assertEquals(ac.getNumberOfChannels(), 1);
        for (int i = 0; i < 10; i++) {
            assertEquals((int)ac.getChannels()[0].get(i), 100);
        }
    }

    private DynamicArray<Integer>[] createMonoData1() {
        DynamicArray<Integer>[] channels = new DynamicArray[1];
        channels[0] = new DynamicArray<>(Integer.class);
        for (int i = 0; i < 10; i++) {
            channels[0].add(100);
        }
        return channels;
    }

    private DynamicArray<Integer>[] createStereoData1() {
        DynamicArray<Integer>[] channels = new DynamicArray[2];
        channels[0] = new DynamicArray<>(Integer.class);
        channels[1] = new DynamicArray<>(Integer.class);
        for (int i = 0; i < 10; i++) {
            channels[0].add(100);
            channels[1].add(0);
        }
        return channels;
    }
    
    private DynamicArray<Integer>[] createStereoData2() {
        DynamicArray<Integer>[] channels = new DynamicArray[2];
        channels[0] = new DynamicArray<>(Integer.class);
        channels[1] = new DynamicArray<>(Integer.class);
        for (int i = 0; i < 10; i++) {
            channels[0].add(0);
            channels[1].add(100);
        }
        return channels;
    }

    private DynamicArray<Integer>[] createMonoData2() {
        DynamicArray<Integer>[] channels = new DynamicArray[1];
        channels[0] = new DynamicArray<>(Integer.class);
        for (int i = 0; i < 10; i += 2) {
            channels[0].add(50);
        }
        return channels;
    }
}
