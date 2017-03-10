
package dasp5000.domain.audioprocessors;

import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * Mixes multiple audio signals together, creating a single audio signal.
 * 
 * @author Petri Kallio
 */
public class Mixer extends AudioProcessor {
    private final AudioContainer mix;
    private final int mixLength;
    private final int bitsPerSample;
    private final int maxSample;

    /**
     * Creates a new Mixer object that can mix the signals of the 
     * AudioContainers given as parameters.
     * 
     * @param audioContainers the AudioContainer's which signals are to be mixed
     */
    public Mixer(AudioContainer... audioContainers) {
        super(audioContainers);
        this.mixLength = longestAudio();
        this.bitsPerSample = audioContainers[0].getBitsPerAudioSample();
        AudioHeader header = createHeader();
        this.mix = new AudioContainer(header);
        DynamicArray<Integer>[] channels = initiateChannels();
        this.mix.setChannels(channels);
        this.maxSample = (int)Math.pow(2, bitsPerSample) / 2;
    }

    /**
     * Get the mixed audio signal as a AudioContainer object.
     * 
     * @return AudioContainer object
     */
    public AudioContainer getMix() {
        return mix;
    }

    private int longestAudio() {
        int maxLength = 0;
        for (int i = 0; i < super.audioContainers.length; i++) {
            int samples = super.audioContainers[i].getAudioAnalysis().getSamples();
            if (samples > maxLength) {
                maxLength = samples;
            }
        }
        return maxLength;
    }
    
    private static double sampleToDouble(int sample, int max) {
        return 1.0 * sample / max;
    }

    private static int doubleToSample(double mix, int maxSample) {
        return (int)(mix * maxSample);
    }

    private AudioHeader createHeader() {
        AudioContainer c = super.audioContainers[0];
        int bytesPerSample = c.getBitsPerAudioSample() / 8;
        int channels = fullMono ? 1 : 2;
        int sampleRate = c.getSampleRate();
        AudioHeader header = new AudioHeader(
                "WAVE", channels, sampleRate, 
                channels * bytesPerSample * sampleRate, 
                channels * bytesPerSample, c.getBitsPerAudioSample(), 
                bytesPerSample * channels * mixLength);
        
        return header;
    }
    
    private DynamicArray<Integer>[] initiateChannels() {
        DynamicArray<Integer>[] channels = new DynamicArray[super.fullMono ? 1 : 2];
        for (int i = 0; i < channels.length; i++) {
            channels[i] = new DynamicArray<>(Integer.class);
        }
        return channels;
    }

    @Override
    protected void processSample(int sampleIndex, int channelIndex, int... samples) {
        if (channelIndex < mix.getChannels().length) {
            int sample = mixSamples(samples, maxSample);
            mix.getChannels()[channelIndex].add(sample);
        }
    }

    @Override
    protected void analyseAudio() {
        Analyzer.analyse(mix);
    }

    @Override
    protected int samples() {
        return this.mixLength;
    }
    
    /**
     * Get the mix sample of the given samples.
     * 
     * @param samples the samples to mix
     * @param maxSample the maximum sample value
     * @return the mixed sample
     */
    public static int mixSamples(int samples[], int maxSample) {
        double sample;
        double newValue;
        double mixValue = 0;
        for (int i = 0; i < samples.length; i++) {
            sample = sampleToDouble(samples[i], maxSample);
            newValue = mixValue + sample;
            if (newValue > 0) {
                mixValue = newValue - mixValue * sample;
            } else {
                mixValue = newValue + mixValue * sample;
            }
        }
        return doubleToSample(mixValue, maxSample);
    }
}
