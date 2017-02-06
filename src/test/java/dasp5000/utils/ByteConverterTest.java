/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.utils;

import dasp5000.domain.DynamicArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pqkallio
 */
public class ByteConverterTest {
    private ByteConverter littleEndian8bit;
    private ByteConverter bigEndian8bit;
    private ByteConverter littleEndian16bit;
    private ByteConverter bigEndian16bit;
    private ByteConverter littleEndian32bit;
    private ByteConverter bigEndian32bit;
    
    public ByteConverterTest() {
    }
    
    @Before
    public void setUp() {
        this.littleEndian8bit = new ByteConverter(8, false);
        this.bigEndian8bit = new ByteConverter(8, true);
        this.littleEndian16bit = new ByteConverter(16, false);
        this.bigEndian16bit = new ByteConverter(16, true);
        this.littleEndian32bit = new ByteConverter(32, false);
        this.bigEndian32bit = new ByteConverter(32, true);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void atFirstTheWordArrayIsEmpty() {
        assertTrue(littleEndian16bit.getWords().size() == 0);
    }
    
    @Test
    public void addingBytesUpdatesTheWordArrayCorrectly16bit() {
        littleEndian16bit.insertBytesToWordArray(new byte[] {1, 2, 3, 4}, 4);
        assertEquals(littleEndian16bit.getWords().size(), 2);
    }
    
    @Test
    public void addingBytesUpdatesTheWordArrayCorrectly8bit() {
        littleEndian8bit.insertBytesToWordArray(new byte[] {1, 2, 3, 4}, 4);
        assertEquals(littleEndian8bit.getWords().size(), 4);
    }
    
    @Test
    public void addingBytesUpdatesTheWordArrayCorrectly32bit() {
        littleEndian32bit.insertBytesToWordArray(new byte[] {1, 2, 3, 4}, 4);
        assertEquals(littleEndian32bit.getWords().size(), 1);
    }
    
    @Test
    public void forLittleEndian16bitTheWordsAreCorrectlyFormed() {
        byte[] bytes = {1, 2};
        littleEndian16bit.insertBytesToWordArray(bytes, 2);
        int word = littleEndian16bit.getWords().get(0);
        assertEquals(word, 513);
    }
    
    @Test
    public void forLittleEndian16bitTheWordsAreCorrectlyFormedv2() {
        byte[] bytes = {2, 1};
        littleEndian16bit.insertBytesToWordArray(bytes, 2);
        int word = littleEndian16bit.getWords().get(0);
        assertEquals(word, 258);
    }
    
    @Test
    public void forBigEndian16bitTheWordsAreCorrectlyFormed() {
        byte[] bytes = {2, 1};
        bigEndian16bit.insertBytesToWordArray(bytes, 2);
        int word = bigEndian16bit.getWords().get(0);
        assertEquals(word, 513);
    }
    
    @Test
    public void forBigEndian16bitTheWordsAreCorrectlyFormedv2() {
        byte[] bytes = {1, 2};
        bigEndian16bit.insertBytesToWordArray(bytes, 2);
        int word = bigEndian16bit.getWords().get(0);
        assertEquals(word, 258);
    }
    
    @Test
    public void forLittleEndian32bitTheWordsAreCorrectlyFormed() {
        byte[] bytes = {1, 2, 3, 4};
        littleEndian32bit.insertBytesToWordArray(bytes, 4);
        int word = littleEndian32bit.getWords().get(0);
        assertEquals(word, 67305985);
    }
    
    @Test
    public void forLittleEndian32bitTheWordsAreCorrectlyFormedv2() {
        byte[] bytes = {4, 3, 2, 1};
        littleEndian32bit.insertBytesToWordArray(bytes, 4);
        int word = littleEndian32bit.getWords().get(0);
        assertEquals(word, 16909060);
    }
    
    @Test
    public void forBigEndian32bitTheWordsAreCorrectlyFormed() {
        byte[] bytes = {4, 3, 2, 1};
        bigEndian32bit.insertBytesToWordArray(bytes, 4);
        int word = bigEndian32bit.getWords().get(0);
        assertEquals(word, 67305985);
    }
    
    @Test
    public void forBigEndian32bitTheWordsAreCorrectlyFormedv2() {
        byte[] bytes = {1, 2, 3, 4};
        bigEndian32bit.insertBytesToWordArray(bytes, 4);
        int word = bigEndian32bit.getWords().get(0);
        assertEquals(word, 16909060);
    }
    
    @Test
    public void setWordsSetsCorrectly() {
        DynamicArray<Integer> array = new DynamicArray<>(Integer.class);
        bigEndian16bit.setWords(array);
        assertEquals(bigEndian16bit.getWords(), array);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentExIfByteToIntConversionBytesNParamLt1() {
        ByteConverter.byteToIntConversion(new byte[] {}, 0, true);
    }
    
    @Test
    public void byteToIntLittleEndianCorrect1() {
        byte[] bytes = {1, 2};
        int value = ByteConverter.byteToIntConversion(bytes, 2, false);
        assertEquals(value, 513);
    }
    
    @Test
    public void byteToIntLittleEndianCorrect2() {
        byte[] bytes = {2, 1};
        int value = ByteConverter.byteToIntConversion(bytes, 2, false);
        assertEquals(value, 258);
    }
    
    @Test
    public void byteToIntBigEndianCorrect1() {
        byte[] bytes = {1, 2};
        int value = ByteConverter.byteToIntConversion(bytes, 2, true);
        assertEquals(value, 258);
    }
    
    @Test
    public void byteToIntBigEndianCorrect2() {
        byte[] bytes = {2, 1};
        int value = ByteConverter.byteToIntConversion(bytes, 2, true);
        assertEquals(value, 513);
    }
    
    @Test
    public void convertWordToBytes16BitLittleEndianWorksCorrectly1() {
        byte[] bytes = {1, 2};
        littleEndian16bit.insertBytesToWordArray(bytes, 2);
        byte[] ret = littleEndian16bit.convertWordsToBytes();
        assertEquals(ret[0], 1);
        assertEquals(ret[1], 2);
    }
    
    @Test
    public void convertWordToBytes16BitLittleEndianWorksCorrectly2() {
        byte[] bytes = {2, 1};
        littleEndian16bit.insertBytesToWordArray(bytes, 2);
        byte[] ret = littleEndian16bit.convertWordsToBytes();
        assertEquals(ret[0], 2);
        assertEquals(ret[1], 1);
    }
    
//    @Test
//    public void convertWordToBytes16BitBigEndianWorksCorrectly1() {
//        byte[] bytes = {1, 2};
//        bigEndian16bit.insertBytesToWordArray(bytes, 2);
//        byte[] ret = bigEndian16bit.convertWordsToBytes();
//        assertEquals(ret[0], 1);
//        assertEquals(ret[1], 2);
//    }
//    
//    @Test
//    public void convertWordToBytes16BitBigEndianWorksCorrectly2() {
//        byte[] bytes = {2, 1};
//        bigEndian16bit.insertBytesToWordArray(bytes, 2);
//        byte[] ret = bigEndian16bit.convertWordsToBytes();
//        assertEquals(ret[0], 2);
//        assertEquals(ret[1], 1);
//    }
}
