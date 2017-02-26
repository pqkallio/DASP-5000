
package dasp5000.domain.audioprocessors;

import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * An abstract class that all AudioProcessors must extend.
 * 
 * @author Petri Kallio
 */
public abstract class AudioProcessor {
    protected AudioContainer[] audioContainers;
    protected boolean fullMono;

    /**
     * Constructor
     * 
     * @param audioContainers the AudioContainers to process
     * @throws IllegalArgumentException if no AudioContainers are given as parameter
     */
    public AudioProcessor(AudioContainer... audioContainers) throws IllegalArgumentException {
        if (audioContainers.length == 0) {
            throw new IllegalArgumentException("No AudioContainers supplied");
        }
        this.fullMono = true;
        this.audioContainers = new AudioContainer[audioContainers.length];
        for (int i = 0; i < this.audioContainers.length; i++) {
            AudioContainer ac = audioContainers[i];
            if (ac.getNumberOfChannels() > 1) {
                this.fullMono = false;
            } 
            this.audioContainers[i] = ac;
        }
    }
    
    /**
     * Process one sample of the AudioContainers' audio data.
     * 
     * @param sampleIndex the index of the sample
     * @param channelIndex the index of the channel
     * @param samples the samples to process
     */
    protected abstract void processSample(int sampleIndex, int channelIndex, int... samples);
    
    /**
     * Re-analyse the audio after processing.
     */
    protected abstract void analyseAudio();
    
    /**
     * Get the amount of samples to process.
     * 
     * @return the amount of samples to process
     */
    protected abstract int samples();
    
    /**
     * Processes the audio data.
     */
    public void process() {
        int[] samplesToProcessLeft = new int[audioContainers.length];
        int[] samplesToProcessRight = new int[audioContainers.length];
        int upto = samples();
        for (int j = 0; j < upto; j++) {
            for (int i = 0; i < audioContainers.length; i++) {
                AudioContainer ac = audioContainers[i];
                getSamples(ac, i, j, samplesToProcessLeft, samplesToProcessRight);
            }
            processSamples(j, samplesToProcessLeft, samplesToProcessRight);
        }
        analyseAudio();
    }

    private void getSamples(AudioContainer ac, int arrayIndex, int sampleIndex, int[] samplesToProcessLeft, int[] samplesToProcessRight) {
        if (ac.getSamplesPerChannel() > sampleIndex) {
            samplesToProcessLeft[arrayIndex] = ac.getLeftChannel().get(sampleIndex);
            if (!fullMono) {
                samplesToProcessRight[arrayIndex] = ac.getRightChannel().get(sampleIndex);
            }
        } else {
            samplesToProcessLeft[arrayIndex] = 0;
            samplesToProcessRight[arrayIndex] = 0;
        }
    }

    private void processSamples(int sampleIndex, int[] samplesToProcessLeft, int[] samplesToProcessRight) {
        processSample(sampleIndex, 0, samplesToProcessLeft);
        if (!fullMono) {
            processSample(sampleIndex, 1, samplesToProcessRight);
        }
    }
}
