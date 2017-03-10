/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author pqkallio
 */
public class LoudnessSampleTest {
    private LoudnessSample ls;
    
    @Before
    public void setUp() {
        this.ls = new LoudnessSample(0, 0, 22050, new double[] {0,0,0});
    }
    
    @Test
    public void freqIsOk() {
        assertEquals((int)ls.getFrequency(), 22050);
    }
    
    @Test
    public void freqIsOk2() {
        assertNotEquals((int)ls.getFrequency(), 2205);
    }
    
    @Test
    public void comparingWorks1() {
        LoudnessSample ls2 = new LoudnessSample(1, 0, 22049, new double[] {0,0,0});
        assertEquals(ls.compareTo(ls2), -1);
    }
    
    @Test
    public void comparingWorks2() {
        LoudnessSample ls2 = new LoudnessSample(0, 0, 22049, new double[] {0,0,0});
        assertEquals(ls.compareTo(ls2), 1);
    }
    
    @Test
    public void comparingWorks3() {
        LoudnessSample ls2 = new LoudnessSample(1, 0, 22049, new double[] {0,0,0});
        assertEquals(ls2.compareTo(ls), 1);
    }
    
    @Test
    public void comparingWorks4() {
        LoudnessSample ls2 = new LoudnessSample(0, 0, 22050, new double[] {0,0,0});
        assertEquals(ls.compareTo(ls2), 0);
    }
}
