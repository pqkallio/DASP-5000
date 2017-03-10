/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author pqkallio
 */
public class WaveHeaderFieldsTest {
    @Test
    public void isBigEndianOk1() {
        Assert.assertTrue(WaveHeaderFields.CHUNK_ID.isBigEndian());
    }
    @Test
    public void isBigEndianOk2() {
        Assert.assertFalse(WaveHeaderFields.CHUNK_SIZE.isBigEndian());
    }
}
