
package dasp5000.domain.audioprocessors;

import dasp5000.domain.audiocontainers.AudioContainer;

public class Gate extends AudioProcessorAbstract {
    private int threshold;
    private int attack;
    private int release;
    private int attackCountDown;
    private int releaseCountDown;
    private boolean underAttack;
    private boolean underRelease;
    private boolean belowTheThreshold;
    
    public Gate(AudioContainer audioContainer, int threshold, int attack, int release) {
        super(audioContainer);
        this.threshold = threshold;
        this.attack = attack;
        this.release = release;
        this.attackCountDown = 0;
        this.releaseCountDown = 0;
        this.underAttack = false;
        this.underRelease = false;
        this.belowTheThreshold = false;
    }
    
    @Override
    protected void processSample(int sampleIndex, int channelIndex, int... samples) {
        if (samples[0] < threshold) {
            if (belowTheThreshold) {
                super.audioContainers[0]
                        .getChannels()[channelIndex]
                        .replace(sampleIndex, 0);
            } else {
                
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
