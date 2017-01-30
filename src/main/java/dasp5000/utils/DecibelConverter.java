
package dasp5000.utils;

/**
 * This class can be used to convert sample values into dBFS values and vice versa.
 * dbFS = decibels relative to full scale.
 * 
 * @author Petri Kallio
 */
public class DecibelConverter {
    
    /**
     * Returns the value of a given sample value as dBFS value.
     * 
     * @param sampleValue The value to convert
     * @param maxValue The maximum value to compare to
     * @return The sample value converted to dBFS
     */
    public static double sampleValueToDecibels(int sampleValue, int maxValue) {
        if (Math.abs(sampleValue) < 2) {
            return 20 * Math.log10(1.0 * sampleValue / maxValue);
        }
        return 20 * Math.log10(1.0 * sampleValue / (maxValue / 2));
    }
    
    /**
     * Returns the value of a given dBFS value as sample value.
     * 
     * @param dBFS The value to convert
     * @param maxValue The maximum value to compare the sample value to.
     * @return 
     */
    public static int dBFSToSampleValue(double dBFS, int maxValue) {
        return (int)(Math.pow(10, dBFS / 20) * (maxValue / 2));
    }
}
