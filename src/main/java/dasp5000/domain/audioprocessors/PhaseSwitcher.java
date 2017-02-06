
package dasp5000.domain.audioprocessors;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * An audio processing class that switches the phase of the audio.
 * 
 * @author Petri Kallio
 */
public class PhaseSwitcher implements AudioProcessor {
    private final AudioContainer audioContainer;
    
    /**
     * Creates a new PhaseSwitcher object.
     * 
     * @param audioContainer an AudioContainer object
     */
    public PhaseSwitcher(AudioContainer audioContainer) {
        this.audioContainer = audioContainer;
    }
    
    /**
     * Switches the phase of the audio data by switching the polarity of each 
     * sample.
     */
    @Override
    public void process() {
        DynamicArray<Integer> data = audioContainer.getLeftChannel();
        for (int i = 0; i < data.size(); i++) {
            int newValue = -1 * data.get(i);
            data.replace(i, newValue);
        }
    }
    
}
