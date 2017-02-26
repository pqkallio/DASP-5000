
package dasp5000.domain.audioprocessors;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * An audio processing class that switches the phase of the audio.
 * 
 * @author Petri Kallio
 */
public class PhaseSwitcher extends AudioProcessor {
    
    /**
     * Creates a new PhaseSwitcher object.
     * 
     * @param audioContainer an AudioContainer object
     */
    public PhaseSwitcher(AudioContainer audioContainer) {
        super(audioContainer);
    }
    
    @Override
    protected void processSample(int sampleIndex, int channelIndex, int... samples) {
        int newValue = -1 * samples[0];
        super.audioContainers[0].getChannels()[channelIndex]
                                .replace(sampleIndex, newValue);
    }

    @Override
    protected void analyseAudio() {
        // do nothing since the analysis doesn't change
    }

    @Override
    protected int samples() {
        return super.audioContainers[0].getSamplesPerChannel();
    }
}
