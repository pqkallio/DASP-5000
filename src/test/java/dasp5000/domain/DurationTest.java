
package dasp5000.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DurationTest {
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void noDuration() {
        Duration d = new Duration(0, 44100);
        assertEquals(d.getSamples(), 0);
        assertEquals(d.getSamplingFrequency(), 44100);
        assertEquals(d.getHours(), 0);
        assertEquals(d.getMinutes(), 0);
        assertEquals(d.getSeconds(), 0, 1);
    }
    
    @Test
    public void oneSecondV1() {
        Duration d = new Duration(44100, 44100);
        assertEquals(d.getSamples(), 44100);
        assertEquals(d.getSamplingFrequency(), 44100);
        assertEquals(d.getHours(), 0);
        assertEquals(d.getMinutes(), 0);
        assertEquals(d.getSeconds(), 1, 1);
    }
    
    @Test
    public void oneSecondV2() {
        Duration d = new Duration(48000, 48000);
        assertEquals(d.getSamples(), 48000);
        assertEquals(d.getSamplingFrequency(), 48000);
        assertEquals(d.getHours(), 0);
        assertEquals(d.getMinutes(), 0);
        assertEquals(d.getSeconds(), 1, 1);
    }
    
    @Test
    public void oneMinuteV1() {
        Duration d = new Duration(60 * 44100, 44100);
        assertEquals(d.getSamples(), 60 * 44100);
        assertEquals(d.getSamplingFrequency(), 44100);
        assertEquals(d.getHours(), 0);
        assertEquals(d.getMinutes(), 1);
        assertEquals(d.getSeconds(), 0, 1);
    }
    
    @Test
    public void oneMinuteV2() {
        Duration d = new Duration(60 * 48000, 48000);
        assertEquals(d.getSamples(), 60 * 48000);
        assertEquals(d.getSamplingFrequency(), 48000);
        assertEquals(d.getHours(), 0);
        assertEquals(d.getMinutes(), 1);
        assertEquals(d.getSeconds(), 0, 1);
    }
    
    @Test
    public void oneHourV1() {
        Duration d = new Duration(3600 * 44100, 44100);
        assertEquals(d.getSamples(), 3600 * 44100);
        assertEquals(d.getSamplingFrequency(), 44100);
        assertEquals(d.getHours(), 1);
        assertEquals(d.getMinutes(), 0);
        assertEquals(d.getSeconds(), 0, 1);
    }
    
    @Test
    public void oneHourV2() {
        Duration d = new Duration(3600 * 48000, 48000);
        assertEquals(d.getSamples(), 3600 * 48000);
        assertEquals(d.getSamplingFrequency(), 48000);
        assertEquals(d.getHours(), 1);
        assertEquals(d.getMinutes(), 0);
        assertEquals(d.getSeconds(), 0, 1);
    }
    
    @Test
    public void halfAnHourV1() {
        Duration d = new Duration(1800 * 44100, 44100);
        assertEquals(d.getSamples(), 1800 * 44100);
        assertEquals(d.getSamplingFrequency(), 44100);
        assertEquals(d.getHours(), 0);
        assertEquals(d.getMinutes(), 30);
        assertEquals(d.getSeconds(), 0, 1);
    }
    
    @Test
    public void halfAnHourV2() {
        Duration d = new Duration(1800 * 48000, 48000);
        assertEquals(d.getSamples(), 1800 * 48000);
        assertEquals(d.getSamplingFrequency(), 48000);
        assertEquals(d.getHours(), 0);
        assertEquals(d.getMinutes(), 30);
        assertEquals(d.getSeconds(), 0, 1);
    }
    
    @Test
    public void oneFifteenV1() {
        Duration d = new Duration(75 * 44100, 44100);
        assertEquals(d.getSamples(), 75 * 44100);
        assertEquals(d.getSamplingFrequency(), 44100);
        assertEquals(d.getHours(), 0);
        assertEquals(d.getMinutes(), 1);
        assertEquals(d.getSeconds(), 15, 1);
    }
    
    @Test
    public void oneFifteenV2() {
        Duration d = new Duration(75 * 48000, 48000);
        assertEquals(d.getSamples(), 75 * 48000);
        assertEquals(d.getSamplingFrequency(), 48000);
        assertEquals(d.getHours(), 0);
        assertEquals(d.getMinutes(), 1);
        assertEquals(d.getSeconds(), 15, 1);
    }
    
    @Test
    public void setSamplesOk() {
        Duration d = new Duration(44100, 44100);
        d.setSamples(88200);
        assertEquals(88200, d.getSamples());
    }
    
    @Test
    public void setSamplingFreqOk() {
        Duration d = new Duration(44100, 44100);
        d.setSamplingFrequency(88200);
        assertEquals(88200, d.getSamplingFrequency());
    }
    
    @Test
    public void toStringOk() {
        Duration d = new Duration(44100, 44100);
        String res = "00:00:01,000";
        assertEquals(res, d.toString());
    }
}
