
package dasp5000.domain;

/**
 * A class that performs a Fast Fourier transform on audio data.
 * 
 * @author Petri Kallio
 */
public class FFT {
    private int windowsize;
    private int m;
    private double[] cos;
    private double[] sin;
    private double window[];
    
    /**
     * Constructor. The window size must be a power of two.
     * 
     * @param windowsize the window size
     */
    public FFT(int windowsize) {
        this.windowsize = windowsize;
        this.m = (int)(Math.log(windowsize) / Math.log(2));
        
        if (windowsize != (1 << m)) {
            throw new IllegalArgumentException();
        }
        
        this.cos = new double[windowsize / 2];
        this.sin = new double[windowsize / 2];
        
        for (int i = 0; i < windowsize / 2; i++) {
            cos[i] = Math.cos(-2 * Math.PI * i / windowsize);
            sin[i] = Math.sin(-2 * Math.PI * i / windowsize);
        }
        
        makeWindow();
    }

    private void makeWindow() {
        this.window = new double[this.windowsize];
        for (int i = 0; i < window.length; i++) {
            window[i] = 0.42 - 0.5 * Math.cos(2 * Math.PI * i / (windowsize - 1)) 
                    + 0.08 * Math.cos(4 * Math.PI * i / (windowsize - 1));
        }
    }

    /**
     * Get the window size in use.
     * 
     * @return the window size
     */
    public double[] getWindow() {
        return window;
    }
    
    /**
     * Run the fast Fourier transform on place to the given data. The data is 
     * given in two arrays of doubles: realParts contains the real parts of the
     * complex numbers to be processed and imaginaryParts contain the imaginary 
     * parts. The transformation is made within the indices 
     * [startIndex, ..., (startIndex + windowSize)].
     * 
     * @param realParts the real parts
     * @param imaginaryParts the imaginary parts
     * @param startIndex the start index of the transformation
     */
    public void transform(double[] realParts, double[] imaginaryParts, int startIndex) {
        int i, j, k, n1, n2, a;
        double c, s, t1, t2;
        
        j = 0;
        n2 = windowsize / 2;
        for (i = 1; i < windowsize - 1; i++) {
            n1 = n2;
            while (j >= n1) {
                j = j - n1;
                n1 = n1 / 2;
            }
            j = j + n1;
            
            if (i < j) {
                t1 = realParts[i + startIndex];
                realParts[i + startIndex] = realParts[j + startIndex];
                realParts[j + startIndex] = t1;
                t1 = imaginaryParts[i + startIndex];
                imaginaryParts[i + startIndex] = imaginaryParts[j + startIndex];
                imaginaryParts[j + startIndex] = t1;
            }
        }
        
        n1 = 0;
        n2 = 1;
        
        for (i = 0; i < m; i++) {
            n1 = n2;
            n2 = n2 + n2;
            a = 0;
            
            for (j = 0; j < n1; j++) {
                c = cos[a];
                s = sin[a];
                a += 1 << (m - i - 1);
                
                for (k = j; k < windowsize; k = k + n2) {
                    t1 = c * realParts[k + n1 + startIndex] - s * imaginaryParts[k + n1 + startIndex];
                    t2 = s * realParts[k + n1 + startIndex] + c * imaginaryParts[k + n1 + startIndex];
                    realParts[k + n1 + startIndex] = realParts[k + startIndex] - t1;
                    imaginaryParts[k + n1 + startIndex] = imaginaryParts[k + startIndex] - t2;
                    realParts[k + startIndex] = realParts[k + startIndex] + t1;
                    imaginaryParts[k + startIndex] = imaginaryParts[k + startIndex] + t2;
                }
            }
        }
    }
}
