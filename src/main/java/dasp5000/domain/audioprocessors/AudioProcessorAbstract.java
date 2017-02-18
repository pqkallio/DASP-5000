
package dasp5000.domain.audioprocessors;

import dasp5000.domain.audiocontainers.AudioContainer;

/**
 *
 * @author Petri Kallio
 */
public abstract class AudioProcessorAbstract {
    protected AudioContainer[] audioContainers;
    protected boolean fullMono;

    public AudioProcessorAbstract(AudioContainer... audioContainers) throws IllegalArgumentException {
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
    
    protected abstract void processSample(int sampleIndex, int channelIndex, int... samples);
    protected abstract void analyseAudio();
    protected abstract int samples();
    
    /**
     * Processes the audio data by setting the peak sample's value as the 
     * dBFSMaxLevel given as a constructor parameter and justifies the 
     * other samples in relation to the new maximum level.
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
