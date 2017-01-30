
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioContainer;
import dasp5000.domain.DynamicArray;

/**
 * An audio processing class that reverses the audio data.
 * 
 * @author Petri Kallio
 */
public class Reverser implements AudioProcessor {
    private final AudioContainer audioContainer;

    public Reverser(AudioContainer audioContainer) {
        this.audioContainer = audioContainer;
    }
    
    @Override
    public void process() {
        DynamicArray<Integer> data = audioContainer.getWords();
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