
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioContainer;
import dasp5000.domain.DynamicArray;

/**
 * An audio processing class that switches the phase of the audio.
 * 
 * @author Petri Kallio
 */
public class PhaseSwitcher implements AudioProcessor {
    private final AudioContainer audioContainer;
    
    public PhaseSwitcher(AudioContainer audioContainer) {
        this.audioContainer = audioContainer;
    }
    
    @Override
    public void process() {
        DynamicArray<Integer> data = audioContainer.getWords();
        for (int i = 0; i < data.size(); i++) {
            int newValue = -1 * data.get(i);
            data.replace(i, newValue);
        }
    }
    
}
