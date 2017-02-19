
package dasp5000.domain.audioprocessors;

import dasp5000.domain.audiocontainers.AudioContainer;

public class Gate extends AudioProcessorAbstract {
    private int threshold;
    private int attack;
    private int release;
    private int sustain;
    private int attackCountDown;
    private int releaseCountUp;
    private int sustainCountDown;
    
    public Gate(AudioContainer audioContainer, int threshold, int attack, int sustain, int release) {
        super(audioContainer);
        this.threshold = threshold;
        this.attack = attack;
        this.release = release;
        this.attackCountDown = attack;
        this.releaseCountUp = 0;
        this.sustain = sustain;
        this.sustainCountDown = sustain;
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

    public int getAttack() {
        return attack;
    }

    public int getRelease() {
        return release;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
    
}
