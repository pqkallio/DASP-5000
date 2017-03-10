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
public class SpectrumAnalysisSampleTest {
    
    @Test
    public void sampleStartOk() {
        SpectrumAnalysisSample saa = new SpectrumAnalysisSample(500, 50);
        assertEquals(500, saa.getSampleStart());
    }
}
