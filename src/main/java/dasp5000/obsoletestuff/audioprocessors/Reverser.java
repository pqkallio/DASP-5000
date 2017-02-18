
package dasp5000.obsoletestuff.audioprocessors;

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
        DynamicArray<Integer>[] data = audioContainer.getChannels();
        int samples = audioContainer.getSamplesPerChannel();
        int j = samples - 1;
        for (int k = 0; k < samples / 2; k++) {
            for (int i = 0; i < audioContainer.getNumberOfChannels(); i++) {
                switchSamples(k, j, data[i]);
            }
            j--;
        }
    }

    private void switchSamples(int i, int j, DynamicArray<Integer> data) {
        int temp = data.get(i);
        data.replace(i, data.get(j));
        data.replace(j, temp);
    }
}
