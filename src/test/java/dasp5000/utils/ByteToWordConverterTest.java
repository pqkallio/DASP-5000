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
public class ByteToWordConverterTest {
    
    public ByteToWordConverterTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testInsertBytesToWordArray() {
        System.out.println("insertBytesToWordArray");
        byte[] bytes = null;
        int byteAmount = 0;
        ByteToWordConverter instance = null;
        instance.insertBytesToWordArray(bytes, byteAmount);
        fail("The test case is a prototype.");
    }

    @Test
    public void testConvertWordsToBytes() {
        System.out.println("convertWordsToBytes");
        ByteToWordConverter instance = null;
        byte[] expResult = null;
        byte[] result = instance.convertWordsToBytes();
        assertArrayEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetWords() {
        System.out.println("getWords");
        ByteToWordConverter instance = null;
        DynamicArray<Integer> expResult = null;
        DynamicArray<Integer> result = instance.getWords();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetWords() {
        System.out.println("setWords");
        DynamicArray<Integer> words = null;
        ByteToWordConverter instance = null;
        instance.setWords(words);
        fail("The test case is a prototype.");
    }
    
}
