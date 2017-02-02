
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.audiocontainers.MonoAudio;
import dasp5000.domain.DynamicArray;
import dasp5000.utils.DecibelConverter;


/**
 * An audio processing class that normalizes the given data.
 * 
 * @author Petri Kallio
 */
public class Normalizer implements AudioProcessor {
    private final MonoAudio audioContainer;
    private double dBFSMaxLevel;
    
    /**
     * Constructs a new Normalizer object.
     * 
     * @param audioContainer The AudioContainer that contains the data to process.
     * @param dBFSMaxLevel The desired maximum level of audio in dBFS scale.
     */
    public Normalizer(MonoAudio audioContainer, double dBFSMaxLevel) {
        this.audioContainer = audioContainer;
        this.dBFSMaxLevel = dBFSMaxLevel;
    }
    
    @Override
    public void process() {
        double expansionMultiplier = calculateExpansionMultiplier();
        DynamicArray<Integer> data = audioContainer.getLeftChannel();
        for (int i = 0; i < data.size(); i++) {
            int newValue = (int)(data.get(i) * expansionMultiplier);
            data.replace(i, newValue);
        }
        audioContainer.setAudioAnalysis(calculateNewAnalysis(data));
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

    private AudioAnalysis calculateNewAnalysis(DynamicArray<Integer> data) {
        AudioAnalysis analysis = Analyzer.analyse(data);
        return analysis;
    }

    public void setdBFSMaxLevel(double dBFSMaxLevel) {
        this.dBFSMaxLevel = dBFSMaxLevel;
    }
}