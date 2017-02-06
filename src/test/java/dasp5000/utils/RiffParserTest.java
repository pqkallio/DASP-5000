/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.utils;

import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author pqkallio
 */
public class RiffParserTest {
    private File file;
    private RiffParser parser;
    private AudioHeader ah;
    private DynamicArray<Integer>[] channels;
    
    @Before
    public void setUp() throws UnsupportedAudioFileException, IOException {
        this.file = new File(ClassLoader.getSystemResource("test.wav").getPath());
        this.parser = new RiffParser(file);
        this.ah = this.parser.getHeader();
        this.channels = this.parser.getChannels();
    }
    
    @After
    public void tearDown() {
    }    
    
    @Test
    public void headerFormatOk() {
        assertEquals(ah.getFormat(), "WAVE");
    }
    
    @Test
    public void blockAlignOk() {
        assertEquals(ah.getBlockAlign(), 4);
    }
    
    @Test
    public void headerBitsPerSampleOk() {
        assertEquals(ah.getBitsPerSample(), 16);
    }
    
    @Test
    public void headerNumChannelsOk() {
        assertEquals(ah.getNumberOfChannels(), 2);
    }
    
    @Test
    public void headerSampleRateOk() {
        assertEquals(ah.getSampleRate(), 44100);
    }
    
    @Test
    public void headerByteRateOk() {
        assertEquals(ah.getByteRate(), 176400);
    }
    
    @Test
    public void headerDataLengthInBytesOk() {
        assertEquals(ah.getDataLengthInBytes(), 352800);
    }
    
    @Test
    public void channelAmountEqualsNumChannels() {
        assertEquals(channels.length, ah.getNumberOfChannels());
    }
    
    @Test
    public void channelSizesEqual() {
        assertEquals(channels[0].size(), channels[1].size());
    }
    
//    @Test
//    public void channelSizesEqualDataSize() {
//        System.out.println(channels[0].size() + ", " + channels[1].size());
//        int dataSize = channels[1].size();
//        assertEquals(ah.getDataLengthInBytes(), dataSize);
//    }
}
