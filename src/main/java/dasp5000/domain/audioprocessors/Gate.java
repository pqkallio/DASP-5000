
package dasp5000.domain.audioprocessors;

import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * A gate processor that suppresses the samples below a given threshold level.
 * 
 * @author Petri Kallio
 */
public class Gate extends AudioProcessor {
    private int threshold;
    private int attack;
    private int release;
    private int sustain;
    private int attackCountDown;
    private int releaseCountUp;
    private int sustainCountDown;
    private final int numChannels;
    
    /**
     * Constructor
     * 
     * @param audioContainer the AudioContainer to process
     * @param threshold the threshold sample
     * @param attack attack time in samples
     * @param sustain sustain time in samples
     * @param release release time in samples
     */
    public Gate(AudioContainer audioContainer, int threshold, int attack, int sustain, int release) {
        super(audioContainer);
        this.numChannels = audioContainer.getNumberOfChannels();
        this.threshold = threshold * numChannels;
        this.attack = attack * numChannels;
        this.release = release * numChannels;
        this.attackCountDown = this.attack;
        this.releaseCountUp = 0;
        this.sustain = sustain * numChannels;
        this.sustainCountDown = this.sustain;
    }
    
    @Override
    protected void processSample(int sampleIndex, int channelIndex, int... samples) {
        if (Math.abs(samples[0]) < threshold) {
            if (sustainCountDown > 0) {
                sustainCountDown--;
            } else if (attackCountDown > 0) {
                attackCountDown--;
                releaseCountUp = 0;
                int newSample = (int)(1.0 * samples[0] * (1.0 * attackCountDown / attack));
                super.audioContainers[0].getChannels()[channelIndex]
                                        .replace(sampleIndex, newSample);
            } else {
                super.audioContainers[0].getChannels()[channelIndex]
                                        .replace(sampleIndex, 0);
            }
        } else {
            sustainCountDown = sustain;
            if (releaseCountUp < release) {
                releaseCountUp++;
                attackCountDown = attack;
                int newSample = (int)(1.0 * samples[0] * (1.0 * releaseCountUp / release));
                super.audioContainers[0].getChannels()[channelIndex]
                                        .replace(sampleIndex, newSample);
            }
        }
    }

    @Override
    protected void analyseAudio() {
        Analyzer.analyse(super.audioContainers[0]);
    }

    @Override
    protected int samples() {
        return super.audioContainers[0].getSamplesPerChannel();
    }

    /**
     * Get the attack time in samples.
     * 
     * @return the attack time in samples
     */
    public int getAttack() {
        return attack / numChannels;
    }

    /**
     * Get the release time in samples
     * 
     * @return the release time
     */
    public int getRelease() {
        return release / numChannels;
    }

    /**
     * Get the threshold as a sample level
     * 
     * @return the threshold
     */
    public int getThreshold() {
        return threshold / numChannels;
    }

    /**
     * Set the attack time in samples
     * 
     * @param attack attack time in samples
     */
    public void setAttack(int attack) {
        this.attack = attack * numChannels;
    }

    /**
     * Set the release time in samples
     * 
     * @param release the release time
     */
    public void setRelease(int release) {
        this.release = release * numChannels;
    }

    /**
     * Set the threshold sample level
     * 
     * @param threshold the threshold sample level
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold * numChannels;
    }
    
}
