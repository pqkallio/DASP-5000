
package dasp5000.obsoletestuff.audioprocessors;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.domain.audioprocessors.Analyzer;
import dasp5000.utils.DecibelConverter;


/**
 * An audio processing class that normalizes the given data.
 * 
 * @author Petri Kallio
 */
public class Normalizer implements AudioProcessor {
    private final AudioContainer audioContainer;
    private double dBFSMaxLevel;
    
    /**
     * Constructs a new Normalizer object.
     * 
     * @param audioContainer The AudioContainer that contains the data to process.
     * @param dBFSMaxLevel The desired maximum level of audio in dBFS scale.
     */
    public Normalizer(AudioContainer audioContainer, double dBFSMaxLevel) {
        this.audioContainer = audioContainer;
        this.dBFSMaxLevel = dBFSMaxLevel;
    }
    
    /**
     * Processes the audio data by setting the peak sample's value as the 
     * dBFSMaxLevel given as a constructor parameter and justifies the 
     * other samples in relation to the new maximum level.
     */
    @Override
    public void process() {
        double expansionMultiplier = calculateExpansionMultiplier();
        DynamicArray<Integer>[] data = audioContainer.getChannels();
        int samples = audioContainer.getSamplesPerChannel();
        for (int j = 0; j < samples; j++) {
            for (int i = 0; i < audioContainer.getNumberOfChannels(); i++) {
                int newValue = (int)(data[i].get(j) * expansionMultiplier);
                data[i].replace(j, newValue);
            }
        }
        Analyzer.analyse(audioContainer);
    }

    private double calculateExpansionMultiplier() {
        int max = (int)Math.pow(2, audioContainer.getBitsPerAudioSample());
        int dBFSMaxInSampleValue 
                = DecibelConverter.dBFSToSampleValue(dBFSMaxLevel, 
                        max);
        int currentPeakSampleValue 
                = audioContainer.getAudioAnalysis().getPeakSampleValue();
        return 1.0 * dBFSMaxInSampleValue / currentPeakSampleValue;
    }

    /**
     * Set the maximum level of the audio data.
     * 
     * @param dBFSMaxLevel the maximum level of the audio data.
     */
    public void setdBFSMaxLevel(double dBFSMaxLevel) {
        this.dBFSMaxLevel = dBFSMaxLevel;
    }
}