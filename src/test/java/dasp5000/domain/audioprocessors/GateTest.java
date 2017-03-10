/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author pqkallio
 */
public class GateTest {
    private AudioHeader ah1;
    private AudioHeader ah2;
    private int[] originalMonoSamples;
    private int[][] originalStereoSamples;
    private AudioContainer mono1;
    private AudioContainer stereo1;
    private Gate gate;
    
    @Before
    public void setUp() {
        this.ah1 = new AudioHeader("test", 1, 44100, 3, 4, 16, 10);
        this.ah2 = new AudioHeader("test", 2, 44100, 3, 4, 16, 10);
        this.originalMonoSamples = new int[100000];
        this.originalStereoSamples = new int[2][100000];
        DynamicArray<Integer>[] monoChannel1 = createMonoData1();
        DynamicArray<Integer>[] stereoChannel1 = createStereoData1();
        this.mono1 = new AudioContainer(ah1);
        this.mono1.setChannels(monoChannel1);
        Analyzer.analyse(mono1);
        this.stereo1 = new AudioContainer(ah2);
        this.stereo1.setChannels(stereoChannel1);
        Analyzer.analyse(stereo1);
        this.gate = new Gate(mono1, -48, 50, 200, 100);
    }
    
    private DynamicArray<Integer>[] createMonoData1() {
        DynamicArray<Integer>[] channels = new DynamicArray[1];
        channels[0] = new DynamicArray<>(Integer.class);
        for (int i = 0; i < 100000; i++) {
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
        for (int i = 0; i < 100000; i++) {
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
    
    @Test
    public void processingDoesThings() {
        gate.process();
        boolean same = true;
        
        for (int i = 0; i < originalMonoSamples.length; i++) {
            if (originalMonoSamples[i] != mono1.getLeftChannel().get(i)) {
                same = false;
            }
        }
        
        Assert.assertFalse(same);
    }
    
    @Test
    public void getAttackIsOk() {
        Assert.assertEquals(gate.getAttack(), 50);
    }
    @Test
    public void getReleaseIsOk() {
        Assert.assertEquals(gate.getRelease(), 100);
    }
    @Test
    public void getThresholdIsOk() {
        Assert.assertEquals(gate.getThreshold(), -48);
    }
    @Test
    public void setAttackIsOk() {
        gate.setAttack(200);
        Assert.assertEquals(gate.getAttack(), 200);
    }
    @Test
    public void setReleaseIsOk() {
        gate.setRelease(300);
        Assert.assertEquals(gate.getRelease(), 300);
    }
    @Test
    public void setThresholdIsOk() {
        gate.setThreshold(-6);
        Assert.assertEquals(gate.getThreshold(), -6);
    }
}
