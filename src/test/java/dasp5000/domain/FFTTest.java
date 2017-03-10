
package dasp5000.domain;

import dasp5000.controllers.AudioController;
import dasp5000.domain.audiocontainers.AudioContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author pqkallio
 */
public class FFTTest {
    private double[] reals;
    private double[] imaginaries;
    private int n;
    
    @Before
    public void setUp() {
        this.n = 256;
        this.reals = new double[n];
        this.imaginaries = new double[n];
        for (int i = 0; i < n; i++) {
            reals[i] = 100;
            imaginaries[i] = 0;
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void badWindowSizeResultsToIllegalArgumentException() {
        FFT fft = new FFT(40);
    }
    
    @Test
    public void getWindowsizeReturnsCorrectly() {
        FFT fft = new FFT(8);
        assertEquals(fft.getWindowSize(), 8);
    }
    
    @Test
    public void onlyValuesWithinTheWindowAreChanged1() {
        FFT fft = new FFT(8);
        fft.transform(reals, imaginaries, 0);
        for (int i = 0; i < 8; i++) {
            assertNotEquals(reals[i], 100);
            assertNotEquals(imaginaries[i], 0);
        }
        for (int i = 8; i < n; i++) {
            assertEquals((int)reals[i], 100);
            assertEquals((int)imaginaries[i], 0);
        }
    }
    
    @Test
    public void onlyValuesWithinTheWindowAreChanged2() {
        FFT fft = new FFT(8);
        fft.transform(reals, imaginaries, 1);
        assertEquals((int)reals[0], 100);
        assertEquals((int)imaginaries[0], 0);
        for (int i = 1; i < 9; i++) {
            assertNotEquals(reals[i], 100);
            assertNotEquals(imaginaries[i], 0);
        }
        for (int i = 9; i < n; i++) {
            assertEquals((int)reals[i], 100);
            assertEquals((int)imaginaries[i], 0);
        }
    }
}
