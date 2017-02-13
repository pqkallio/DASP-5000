
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * A class for analysing audio data.
 * 
 * @author Petri Kallio
 */
public class Analyzer {
    
    /**
     * Analyses the given AudioContainer object's audio data and saves the 
     * analysis to the AudioContainer object. 
     * 
     * @param audioContainer the AudioContainer object to be analysed 
     */
    public static void analyse(AudioContainer audioContainer) {
        AudioAnalysis analysis = null;
        analysis = analyseAudio(audioContainer);
        audioContainer.setAudioAnalysis(analysis);
    }

    private static AudioAnalysis analyseAudio(AudioContainer audioContainer) {
        DynamicArray<Integer>[] audioData = audioContainer.getChannels();
        int peakValue = Integer.MIN_VALUE;
        int minimumValue = Integer.MAX_VALUE;
        int samples = 0;
        for (int i = 0; i < audioData[0].size(); i++) {
            samples++;
            for (int j = 0; j < audioData.length; j++) {
                int val = Math.abs(audioData[j].get(i));
                if (val > peakValue) {
                    peakValue = val;
                }
                if (val < minimumValue) {
                    minimumValue = val;
                }
            }
        }
        return new AudioAnalysis(peakValue, minimumValue, samples);
    }
}
