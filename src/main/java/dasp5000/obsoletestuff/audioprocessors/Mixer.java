
package dasp5000.obsoletestuff.audioprocessors;

import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * Mixes multiple audio signals together, creating a single audio signal.
 * 
 * @author Petri Kallio
 */
public class Mixer implements AudioProcessor {
    private DynamicArray<AudioContainer> audioContainers;
    private AudioContainer mix;
    private int mixLength;
    private int bitsPerSample;
    private boolean fullMono;

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
        this.fullMono = true;
        this.mixLength = 0;
        this.bitsPerSample = audioContainers[0].getBitsPerAudioSample();
        this.audioContainers = new DynamicArray<>(AudioContainer.class);
        for (int i = 0; i < audioContainers.length; i++) {
            AudioContainer ac = audioContainers[i];
            addToAudioContainers(ac);
        }
        AudioHeader header = createHeader();
        this.mix = new AudioContainer(header);
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
        DynamicArray<Integer>[] theMix = initiateChannels();
        int maxSample = (int)Math.pow(2, bitsPerSample) / 2;
        for (int i = 0; i < mixLength; i++) {
            int sample = mixChannel(0, i, maxSample);
            theMix[0].add(sample);
            if (!fullMono) {
                sample = mixChannel(1, i, maxSample);
                theMix[1].add(sample);
            }
        }
        this.mix.setChannels(theMix);
    }

    private int longestAudio() {
        int maxLength = 0;
        for (int i = 0; i < audioContainers.size(); i++) {
            int samples = audioContainers.get(i).getAudioAnalysis().getSamples();
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

    private AudioHeader createHeader() {
        AudioContainer c = audioContainers.get(0);
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

    private void addToAudioContainers(AudioContainer ac) {
        if (ac.getBitsPerAudioSample() != this.bitsPerSample) {
            throw new IllegalArgumentException("The AudioContainers' "
                    + "sample resolution must match");
        }
        this.audioContainers.add(ac);
        if (ac.getNumberOfChannels() > 1) {
            this.fullMono = false;
        }
        int samples = ac.getAudioAnalysis().getSamples();
        if (samples > this.mixLength) {
            this.mixLength = samples;
        }
    }

    private int mixChannel(int channel, int index, int maxSample) {
        double mixValue = 0;
        for (int j = 0; j < audioContainers.size(); j++) {
            AudioContainer ac = audioContainers.get(j);
            if (ac.getAudioAnalysis().getSamples() > index) {
                double sample;
                if (ac.getChannels().length > index) {
                    sample = sampleToDouble(ac.getChannels()[channel].get(index), 
                        maxSample);
                } else {
                    sample = sampleToDouble(ac.getChannels()[0].get(index), 
                        maxSample);
                }
                double newValue = mixValue + sample;
                if (newValue > 0) {
                    mixValue = newValue - mixValue * sample;
                } else {
                    mixValue = newValue + mixValue * sample;
                }
            }
        }
        return doubleToSample(mixValue, maxSample);
    }

    private DynamicArray<Integer>[] initiateChannels() {
        DynamicArray<Integer>[] channels = new DynamicArray[fullMono ? 1 : 2];
        for (int i = 0; i < channels.length; i++) {
            channels[i] = new DynamicArray<>(Integer.class);
        }
        return channels;
    }
}
