/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain.audioprocessors;

import dasp5000.obsoletestuff.audioprocessors.Mixer;
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
public class MixerTest {
    
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
    
    @Test(expected = IllegalArgumentException.class)
    public void ifNoAudioContainersIllegalArgumentEx() {
        new Mixer(new AudioContainer[] {});
    }
}
