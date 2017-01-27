
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioAnalysis;
import java.util.ArrayList;

public class Analyzer {
    
    public static AudioAnalysis analyse(ArrayList<Integer> words) {
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
