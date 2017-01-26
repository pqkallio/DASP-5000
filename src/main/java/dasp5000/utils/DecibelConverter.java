
package dasp5000.utils;

public class DecibelConverter {
    
    public static double sampleValueToDecibels(int sampleValue, int maxValue) {
        System.out.println(sampleValue);
        if (Math.abs(sampleValue) < 2) {
            return 20 * Math.log10(1.0 * sampleValue / maxValue);
        }
        return 20 * Math.log10(1.0 * sampleValue / (maxValue / 2));
    }
    
    public static int dBFSToSampleValue(double dBFS, int maxValue) {
        return (int)(Math.pow(10, dBFS / 20) * (maxValue / 2));
    }
}
