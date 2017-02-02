
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.domain.audiocontainers.MonoAudio;
import dasp5000.domain.audiocontainers.StereoAudio;

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
    public static AudioAnalysis analyse(AudioContainer audioContainer) {
        korjaa!
        AudioAnalysis analysis = null;
        if (audioContainer.getClass() == MonoAudio.class) {
            analysis = analyseMonoAudio(audioContainer);
        } else if (audioContainer.getClass() == StereoAudio.class) {
            analysis = analyseStereoAudio(audioContainer); 
        }
        
        return analysis;
    }

    private static AudioAnalysis analyseMonoAudio(AudioContainer audioContainer) {
        DynamicArray<Integer> audioData = audioContainer.getLeftChannel();
        int peakValue = Integer.MIN_VALUE;
        int minimumValue = Integer.MAX_VALUE;
        int samples = 0;
        for (int i = 0; i < audioData.size(); i++) {
            samples++;
            int val = Math.abs(audioData.get(i));
            if (val > peakValue) {
                peakValue = val;
            }
            if (val < minimumValue) {
                minimumValue = val;
            }
        }
        return new AudioAnalysis(peakValue, minimumValue, samples);
    }

    private static AudioAnalysis analyseStereoAudio(AudioContainer audioContainer) {
        DynamicArray<Integer> leftChannel = audioContainer.getLeftChannel();
        DynamicArray<Integer> rightChannel = audioContainer.getRightChannel();
        int peakValue = Integer.MIN_VALUE;
        int minimumValue = Integer.MAX_VALUE;
        int samples = 0;
        for (int i = 0; i < leftChannel.size(); i++) {
            samples++;
            int biggerVal = Math.max(Math.abs(leftChannel.get(i)), 
                    Math.abs(rightChannel.get(i)));
            int smallerVal = Math.min(Math.abs(leftChannel.get(i)), 
                    Math.abs(rightChannel.get(i)));
            if (biggerVal > peakValue) {
                peakValue = biggerVal;
            }
            if (smallerVal < minimumValue) {
                minimumValue = biggerVal;
            }
        }
        return new AudioAnalysis(peakValue, minimumValue, samples);
    }
}
