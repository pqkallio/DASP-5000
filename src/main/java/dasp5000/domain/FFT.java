
package dasp5000.domain;

/**
 * A class that performs a Fast Fourier transform on audio data.
 * 
 * @author Petri Kallio
 */
public class FFT {
    private int windowsize;
    private int powerOfTwoOfWindowSize;
    private double[] cosValues;
    private double[] sinValues;
    
    /**
     * Constructor. The window size must be a power of two.
     * 
     * @param windowsize the window size
     */
    public FFT(int windowsize) {
        this.windowsize = windowsize;
        this.powerOfTwoOfWindowSize = (int)(Math.log(windowsize) / Math.log(2));
        
        /* check that the windowsize is a power of two,
           and if not, throw an IllegalArgumentException */
        if (windowsize != (1 << powerOfTwoOfWindowSize)) {
            throw new IllegalArgumentException();
        }
        
        this.cosValues = new double[windowsize / 2];
        this.sinValues = new double[windowsize / 2];
        
        for (int i = 0; i < windowsize / 2; i++) {
            cosValues[i] = Math.cos(-2 * Math.PI * i / windowsize);
            sinValues[i] = Math.sin(-2 * Math.PI * i / windowsize);
        }
    }

    /**
     * Get the window size in use.
     * 
     * @return the window size
     */
    public int getWindowSize() {
        return windowsize;
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
        int n1, n2;
        double t1, t2;
        
        int j = 0;
        n2 = windowsize / 2;
        for (int i = 1; i < windowsize - 1; i++) {
            n1 = n2;
            while (j >= n1) {
                j -= n1;
                n1 /= 2;
            }
            
            j += n1;
            
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
        
        for (int i = 0; i < powerOfTwoOfWindowSize; i++) {
            n1 = n2;
            n2 *= 2;
            int a = 0;
            
            for (j = 0; j < n1; j++) {
                double cosValue = cosValues[a];
                double sinValue = sinValues[a];
                a += 1 << (powerOfTwoOfWindowSize - i - 1);
                
                for (int k = j; k < windowsize; k = k + n2) {
                    t1 = cosValue * realParts[k + n1 + startIndex] - 
                            sinValue * imaginaryParts[k + n1 + startIndex];
                    t2 = sinValue * realParts[k + n1 + startIndex] + 
                            cosValue * imaginaryParts[k + n1 + startIndex];
                    realParts[k + n1 + startIndex] = 
                            realParts[k + startIndex] - t1;
                    imaginaryParts[k + n1 + startIndex] = 
                            imaginaryParts[k + startIndex] - t2;
                    realParts[k + startIndex] = 
                            realParts[k + startIndex] + t1;
                    imaginaryParts[k + startIndex] = 
                            imaginaryParts[k + startIndex] + t2;
                }
            }
        }
    }
}
