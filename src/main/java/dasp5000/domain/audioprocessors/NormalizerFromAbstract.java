
package dasp5000.domain.audioprocessors;

import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.utils.DecibelConverter;

/**
 * An audio processing class that normalizes the given data.
 * 
 * @author Petri Kallio
 */
public class NormalizerFromAbstract extends AudioProcessorAbstract {
    private double dBFSMaxLevel;
    private double expansionMultiplier;
    
    /**
     * Constructs a new Normalizer object.
     * 
     * @param audioContainer The AudioContainer that contains the data to process.
     * @param dBFSMaxLevel The desired maximum level of audio in dBFS scale.
     */
    public NormalizerFromAbstract(AudioContainer audioContainer, double dBFSMaxLevel) {
        super(audioContainer);
        this.dBFSMaxLevel = dBFSMaxLevel;
        this.expansionMultiplier = calculateExpansionMultiplier();
    }
    
    private double calculateExpansionMultiplier() {
        int max = (int)Math.pow(2, super.audioContainers[0].getBitsPerAudioSample());
        int dBFSMaxInSampleValue 
                = DecibelConverter.dBFSToSampleValue(dBFSMaxLevel, 
                        max);
        int currentPeakSampleValue 
                = super.audioContainers[0].getAudioAnalysis().getPeakSampleValue();
        return 1.0 * dBFSMaxInSampleValue / currentPeakSampleValue;
    }

    /**
     * Set the maximum level of the audio data.
     * 
     * @param dBFSMaxLevel the maximum level of the audio data.
     */
    public void setdBFSMaxLevel(double dBFSMaxLevel) {
        this.dBFSMaxLevel = dBFSMaxLevel;
        this.expansionMultiplier = calculateExpansionMultiplier();
    }

    @Override
    protected void processSample(int sampleIndex, int channelIndex, int... samples) {
        int newValue = (int)(samples[0] * expansionMultiplier);
        super.audioContainers[0].getChannels()[channelIndex].replace(sampleIndex, newValue);
    }

    @Override
    protected void analyseAudio() {
        Analyzer.analyse(super.audioContainers[0]);
    }

    @Override
    protected int samples() {
        return super.audioContainers[0].getSamplesPerChannel();
    }
}