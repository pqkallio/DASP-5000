
package dasp5000.domain;

import dasp5000.domain.audioprocessors.AudioAnalysis;
import javax.sound.sampled.AudioFormat;

public class AudioContainer {
    private AudioAnalysis audioAnalysis;
    private DynamicArray<Integer> words;
    private final AudioFormat audioFormat;

    public AudioContainer(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }
    
    public void setAudioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
    }

    public void setWords(DynamicArray<Integer> words) {
        this.words = words;
    }
    
    public int getNumberOfChannels() {
        return this.audioFormat.getChannels();
    }
    
    public int getBitDepth() {
        return this.audioFormat.getSampleSizeInBits();
    }
    
    public float getSampleRate() {
        return this.audioFormat.getSampleRate();
    }
    
    public boolean isBigEndian() {
        return this.audioFormat.isBigEndian();
    }

    public DynamicArray<Integer> getWords() {
        return words;
    }

    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }
    
}
