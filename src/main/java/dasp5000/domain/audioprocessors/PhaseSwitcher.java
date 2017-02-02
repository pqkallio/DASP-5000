
package dasp5000.domain.audioprocessors;

import dasp5000.domain.audiocontainers.MonoAudio;
import dasp5000.domain.DynamicArray;

/**
 * An audio processing class that switches the phase of the audio.
 * 
 * @author Petri Kallio
 */
public class PhaseSwitcher implements AudioProcessor {
    private final MonoAudio audioContainer;
    
    public PhaseSwitcher(MonoAudio audioContainer) {
        this.audioContainer = audioContainer;
    }
    
    @Override
    public void process() {
        DynamicArray<Integer> data = audioContainer.getLeftChannel();
        for (int i = 0; i < data.size(); i++) {
            int newValue = -1 * data.get(i);
            data.replace(i, newValue);
        }
    }
    
}
