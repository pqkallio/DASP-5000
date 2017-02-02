
package dasp5000.domain.audioprocessors;

import dasp5000.domain.audiocontainers.MonoAudio;
import dasp5000.domain.DynamicArray;
import javax.sound.sampled.AudioFormat;

/**
 * Mixes the signals together
 * 
 * @author Petri Kallio
 */
public class Mixer implements AudioProcessor {
    private DynamicArray<MonoAudio> audioContainers;
    private MonoAudio mix;
    private long mixLength;
    private int bitsPerSample;

    public Mixer(MonoAudio... audioContainers) {
        if (audioContainers.length == 0) {
            throw new IllegalArgumentException("No AudioContainers supplied");
        }
        this.mixLength = 0;
        this.bitsPerSample = audioContainers[0].getBitsPerAudioSample();
        this.audioContainers = new DynamicArray<>(MonoAudio.class);
        for (int i = 0; i < audioContainers.length; i++) {
            MonoAudio ac = audioContainers[i];
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

    public MonoAudio getMix() {
        return mix;
    }
    
    public void addAudioContainer(MonoAudio audioContainer) {
        this.audioContainers.add(audioContainer);
        this.mixLength = longestAudio();
    }

    @Override
    public void process() {
        DynamicArray<Integer> wordMix = new DynamicArray<>(Integer.class);
        int maxSample = (int)Math.pow(2, bitsPerSample) / 2;
        for (long i = 0; i < mixLength; i++) {
            double mixValue = 0;
            for (int j = 0; j < audioContainers.size(); j++) {
                MonoAudio ac = audioContainers.get(j);
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
        this.mix.setAudioData(wordMix);
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
