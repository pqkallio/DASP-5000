/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain.audioprocessors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author petrikallio
 */
public class AudioProcessorTest {
    
    public AudioProcessorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Process method, of class AudioProcessor.
     */
    @Test
    public void testProcess() {
        System.out.println("Process");
        byte[] byteArray = null;
        AudioProcessor instance = new AudioProcessorImpl();
        instance.Process(byteArray);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class AudioProcessorImpl implements AudioProcessor {

        public void Process(byte[] byteArray) {
        }
    }
    
}
