
package dasp5000.domain.audioprocessors;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * An audio processing class that reverses the audio data.
 * 
 * @author Petri Kallio
 */
public class Reverser extends AudioProcessor {
    private int[] backIndexes;
    
    /**
     * Creates a new Reverser object.
     * 
     * @param audioContainer the AudioContainer object which audio is to be 
     * reversed.
     */
    public Reverser(AudioContainer audioContainer) {
        super(audioContainer);
        int numChannels = audioContainer.getNumberOfChannels();
        int audioLength = audioContainer.getSamplesPerChannel();
        this.backIndexes = new int[numChannels];
        for (int i = 0; i < numChannels; i++) {
            this.backIndexes[i] = audioLength - 1;
        }
    }

    @Override
    protected void processSample(int frontIndex, int channelIndex, int... samples) {
        DynamicArray<Integer> channel = audioContainers[0].getChannels()[channelIndex];
        int temp = channel.get(backIndexes[channelIndex]);
        channel.replace(backIndexes[channelIndex], samples[0]);
        channel.replace(frontIndex, temp);
        backIndexes[channelIndex]--;
    }

    @Override
    protected void analyseAudio() {
        // do nothing, 'cause the analysis doesn't change
    }

    @Override
    protected int samples() {
        return super.audioContainers[0].getSamplesPerChannel() / 2;
    }
}
