
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioContainer;
import dasp5000.domain.DynamicArray;
import javax.sound.sampled.AudioFormat;

/**
 * Mixes the signals together
 * 
 * @author Petri Kallio
 */
public class Mixer implements AudioProcessor {
    private DynamicArray<AudioContainer> audioContainers;
    private AudioContainer mix;
    private long mixLength;
    private int bitsPerSample;

    public Mixer(AudioContainer... audioContainers) {
        if (audioContainers.length == 0) {
            throw new IllegalArgumentException("No AudioContainers supplied");
        }
        this.mixLength = 0;
        this.bitsPerSample = audioContainers[0].getBitDepth();
        this.audioContainers = new DynamicArray<>(AudioContainer.class);
        for (int i = 0; i < audioContainers.length; i++) {
            AudioContainer ac = audioContainers[i];
            if (ac.getBitDepth() != this.bitsPerSample) {
                throw new IllegalArgumentException("The AudioContainers' "
                        + "sample resolution must match");
            }
            this.audioContainers.add(ac);
            long samples = ac.getAudioAnalysis().getSamples();
            if (samples > this.mixLength) {
                this.mixLength = samples;
            }
        }
        this.mixLength = longestAudio();
        AudioFormat audioFormat 
                = copyAudioFormatProperties(audioContainers[0].getAudioFormat());
        this.mix = new AudioContainer(audioFormat);
    }

    public AudioContainer getMix() {
        return mix;
    }
    
    public void addAudioContainer(AudioContainer audioContainer) {
        this.audioContainers.add(audioContainer);
        this.mixLength = longestAudio();
    }

    @Override
    public void process() {
        DynamicArray<Integer> wordMix = new DynamicArray<>(Integer.class);
        int maxSample = (int)Math.pow(2, bitsPerSample) / 2;
        for (long i = 0; i < mixLength; i++) {
            double mix = 0;
            for (int j = 0; j < audioContainers.size(); j++) {
                AudioContainer ac = audioContainers.get(j);
                if (ac.getAudioAnalysis().getSamples() > i) {
                    double sample = sampleToDouble(ac.getWords().get((int)i), 
                            maxSample);
                    mix = mix + sample - mix * sample;
                }
            }
            int mixedSample = doubleToSample(mix, maxSample);
            wordMix.add(mixedSample);
        }
        this.mix.setWords(wordMix);
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
