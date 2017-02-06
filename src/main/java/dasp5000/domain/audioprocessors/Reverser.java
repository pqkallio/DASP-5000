
package dasp5000.domain.audioprocessors;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * An audio processing class that reverses the audio data.
 * 
 * @author Petri Kallio
 */
public class Reverser implements AudioProcessor {
    private final AudioContainer audioContainer;

    /**
     * Creates a new Reverser object.
     * 
     * @param audioContainer the AudioContainer object which audio is to be 
     * reversed.
     */
    public Reverser(AudioContainer audioContainer) {
        this.audioContainer = audioContainer;
    }
    
    @Override
    public void process() {
        DynamicArray<Integer> data = audioContainer.getLeftChannel();
        int j = data.size() - 1;
        for (int i = 0; i < data.size() / 2; i++) {
            switchSamples(i, j, data);
            j--;
        }
    }

    private void switchSamples(int i, int j, DynamicArray<Integer> data) {
        int temp = data.get(i);
        data.replace(i, data.get(j));
        data.replace(j, temp);
    }
}
