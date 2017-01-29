
package dasp5000.domain.audioprocessors;

import dasp5000.domain.DynamicArray;

/**
 * A class for analysing audio data.
 * 
 * @author Petri Kallio
 */
public class Analyzer {
    
    /**
     * Analyses the given DynamicArray of words. Returns the analysis as 
     * an AudioAnalysis object.
     * 
     * @param words
     * @return 
     */
    public static AudioAnalysis analyse(DynamicArray<Integer> words) {
        int peakValue = Integer.MIN_VALUE;
        int minimumValue = Integer.MAX_VALUE;
        int samples = 0;
        for (int i = 0; i < words.size(); i++) {
            samples++;
            int val = Math.abs(words.get(i));
            if (val > peakValue) {
                peakValue = val;
            }
            if (val < minimumValue) {
                minimumValue = val;
            }
        }
        return new AudioAnalysis(peakValue, minimumValue, samples);
    }
}
