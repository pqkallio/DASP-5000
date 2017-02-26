
package dasp5000.domain;

/**
 * A class to perform a Discrete Fourier Transform on data.
 *
 * @author Petri Kallio
 */
public class DFT {
    
    /**
     * Takes an array of samples between -1.0 and 1.0 and performs discrete 
     * fourier transform on them. The returned array contains the frequency,
     * its magnitude and its phase.
     * 
     * @param samples the samples to analyze
     * @return the analysed data as an array
     */
    public static double[][] transform(double[] samples) {
        int transformLength = 11025;
        double[][] analysis = new double[3][transformLength / 2];
        double[] cosPart = new double[transformLength / 2];
        double[] sinPart = new double[transformLength / 2];
        double arg;
        for (int i = 0; i < transformLength / 2; i++) {
            cosPart[i] = 0;
            sinPart[i] = 0;
            for (int j = 0; j < transformLength; j++) {
                arg = 2.0 * i * Math.PI * j / transformLength;
                sinPart[i] += samples[j] * -1 * Math.sin(arg);
                cosPart[i] += samples[j] * Math.cos(arg);
            }
            analysis[0][i] = 1.0 * i * 44100 / transformLength;
            analysis[1][i] = 20.0 * Math.log10(2.0 * Math.sqrt(sinPart[i] * sinPart[i] +
                                                               cosPart[i] * cosPart[i]) /
                                                               transformLength);
            analysis[2][i] = 180.0 * Math.atan2(sinPart[i], cosPart[i]) / Math.PI - 90.0;
        }
        return analysis;
    }
}
