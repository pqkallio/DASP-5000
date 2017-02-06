
package dasp5000.domain.audioprocessors;

import dasp5000.domain.audiocontainers.MonoAudio;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import javax.sound.sampled.AudioFormat;

/**
 * Mixes multiple audio signals together, creating a single audio signal.
 * 
 * @author Petri Kallio
 */
public class Mixer implements AudioProcessor {
    private DynamicArray<AudioContainer> audioContainers;
    private AudioContainer mix;
    private long mixLength;
    private int bitsPerSample;

    /**
     * Creates a new Mixer object that can mix the signals of the 
     * AudioContainers given as parameters.
     * 
     * @param audioContainers the AudioContainer's which signals are to be mixed
     */
    public Mixer(AudioContainer... audioContainers) {
        if (audioContainers.length == 0) {
            throw new IllegalArgumentException("No AudioContainers supplied");
        }
        this.mixLength = 0;
        this.bitsPerSample = audioContainers[0].getBitsPerAudioSample();
        this.audioContainers = new DynamicArray<>(AudioContainer.class);
        for (int i = 0; i < audioContainers.length; i++) {
            AudioContainer ac = audioContainers[i];
            if (ac.getBitsPerAudioSample() != this.bitsPerSample) {
                throw new IllegalArgumentException("The AudioContainers' "
                        + "sample resolution must match");
            }
            this.audioContainers.add(ac);
            long samples = ac.getAudioAnalysis().getSamples();
            if (samples > this.mixLength) {
                this.mixLength = samples;
            }
        }
        AudioFormat audioFormat 
                = copyAudioFormatProperties(audioContainers[0].getAudioFormat());
        this.mix = new MonoAudio(audioFormat);
    }

    /**
     * Get the mixed audio signal as a AudioContainer object.
     * 
     * @return AudioContainer object
     */
    public AudioContainer getMix() {
        return mix;
    }
    
    /**
     * Add a new AudioContainer to the mix.
     * 
     * @param audioContainer an AudioContainer object
     */
    public void addAudioContainer(AudioContainer audioContainer) {
        this.audioContainers.add(audioContainer);
        this.mixLength = longestAudio();
    }

    /**
     * Mixes the signals of the AudioContainer objects to a new audio signal.
     */
    @Override
    public void process() {
        DynamicArray<Integer> wordMix = new DynamicArray<>(Integer.class);
        int maxSample = (int)Math.pow(2, bitsPerSample) / 2;
        for (long i = 0; i < mixLength; i++) {
            double mixValue = 0;
            for (int j = 0; j < audioContainers.size(); j++) {
                AudioContainer ac = audioContainers.get(j);
                if (ac.getAudioAnalysis().getSamples() > i) {
                    double sample = sampleToDouble(ac.getLeftChannel().get((int)i), 
                            maxSample);
                    double newValue = mixValue + sample;
                    if (newValue > 0) {
                        mixValue = newValue - mixValue * sample;
                    } else {
                        mixValue = newValue + mixValue * sample;
                    }
                }
            }
            int mixedSample = doubleToSample(mixValue, maxSample);
            wordMix.add(mixedSample);
        }
        DynamicArray<Integer>[] theMix = new DynamicArray[1];
        theMix[0] = wordMix;
        this.mix.setChannels(theMix);
    }

    private long longestAudio() {
        long maxLength = 0;
        for (int i = 0; i < audioContainers.size(); i++) {
            long samples = audioContainers.get(i).getAudioAnalysis().getSamples();
            if (samples > maxLength) {
                maxLength = samples;
            }
        }
        return maxLength;
    }
    
    private double sampleToDouble(int sample, int max) {
        return 1.0 * sample / max;
    }

    private int doubleToSample(double mix, int maxSample) {
        return (int)(mix * maxSample);
    }

    private AudioFormat copyAudioFormatProperties(AudioFormat audioFormat) {
        AudioFormat newAudioFormat = new AudioFormat(audioFormat.getSampleRate(), 
                bitsPerSample, 
                audioFormat.getChannels(), true, 
                audioFormat.isBigEndian());
        return newAudioFormat;
    }
}
