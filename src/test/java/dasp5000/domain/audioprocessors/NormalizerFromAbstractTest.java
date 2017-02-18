
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

public class NormalizerFromAbstractTest {
    AudioHeader ah1;
    AudioHeader ah2;
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
    public void normalizingMonoAudioWorks() {
        assertEquals(900, this.mono1.getAudioAnalysis().getPeakSampleValue());
        new NormalizerFromAbstract(mono1, 0).process();
        assertEquals(32768, this.mono1.getAudioAnalysis().getPeakSampleValue());
    }
    @Test
    public void settingNewDBFSWorks() {
        assertEquals(900, this.mono1.getAudioAnalysis().getPeakSampleValue());
        NormalizerFromAbstract n = new NormalizerFromAbstract(mono1, 0);
        n.setdBFSMaxLevel(-6);
        n.process();
        assertEquals(16422, this.mono1.getAudioAnalysis().getPeakSampleValue());
    }
    @Test
    public void normalizingMonoAudioWorks2() {
        assertEquals(900, this.mono1.getAudioAnalysis().getPeakSampleValue());
        new NormalizerFromAbstract(mono1, -6).process();
        assertEquals(16422, this.mono1.getAudioAnalysis().getPeakSampleValue());
    }
    @Test
    public void normalizingMonoAudioWorks3() {
        assertEquals(900, this.mono1.getAudioAnalysis().getPeakSampleValue());
        new NormalizerFromAbstract(mono1, 6).process();
        assertEquals(65380, this.mono1.getAudioAnalysis().getPeakSampleValue());
    }
    
    @Test
    public void normalizingStereoAudioWorks() {
        assertEquals(900, this.stereo1.getAudioAnalysis().getPeakSampleValue());
        new NormalizerFromAbstract(stereo1, 0).process();
        assertEquals(32768, this.stereo1.getAudioAnalysis().getPeakSampleValue());
    }
    @Test
    public void normalizingStereoAudioWorks2() {
        assertEquals(900, this.stereo1.getAudioAnalysis().getPeakSampleValue());
        new NormalizerFromAbstract(stereo1, -12).process();
        assertEquals(8230, this.stereo1.getAudioAnalysis().getPeakSampleValue());
    }
    @Test
    public void normalizingStereoAudioWorks3() {
        assertEquals(900, this.stereo1.getAudioAnalysis().getPeakSampleValue());
        new NormalizerFromAbstract(stereo1, 12).process();
        assertEquals(130451, this.stereo1.getAudioAnalysis().getPeakSampleValue());
    }

    private DynamicArray<Integer>[] createMonoData1() {
        DynamicArray<Integer>[] channels = new DynamicArray[1];
        channels[0] = new DynamicArray<>(Integer.class);
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                channels[0].add(i * 100);
            } else {
                channels[0].add(-1 * i * 100);
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
            } else {
                channels[0].add(-1 * i * 100);
                channels[1].add(-1 * (int)Math.pow(2, i));
            }
        }
        return channels;
    }
}
