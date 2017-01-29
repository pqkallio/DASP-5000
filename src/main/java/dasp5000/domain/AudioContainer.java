
package dasp5000.domain;

import javax.sound.sampled.AudioFormat;

/**
 * A class to contain all the information needed to process an opened 
 * AudioInputStream's data.
 * 
 * @author Petri Kallio
 */
public class AudioContainer {
    private AudioAnalysis audioAnalysis;
    private DynamicArray<Integer> words;
    private final AudioFormat audioFormat;

    /**
     * Creates a new AudioContainer object.
     * 
     * @param audioFormat The AudioFormat object of the corresponding AudioInputStream.
     */
    public AudioContainer(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }
    
    /**
     * Sets the AudioAnalysis object that contains important data used in 
     * processing the audio data.
     * 
     * @param audioAnalysis 
     */
    public void setAudioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
    }

    /**
     * Sets the DynamicArray that contains all the words that are composed of
     * a AudioInputStream's bytes based on its bit depth (e.g. 16 bits per sample).
     * 
     * @param words Contains bytes combined as words
     */
    public void setWords(DynamicArray<Integer> words) {
        this.words = words;
    }
    
    /**
     * Get the number of channels of the audio data.
     * 
     * @return 
     */
    public int getNumberOfChannels() {
        return this.audioFormat.getChannels();
    }
    
    /**
     * Get the bit depth of the audio, meaning the amount of bits used to 
     * represent one audio sample.
     * 
     * @return 
     */
    public int getBitDepth() {
        return this.audioFormat.getSampleSizeInBits();
    }
    
    /**
     * Get the sample rate of the audio (e.g. 44.1 kHz or 44100 samples per second.
     * 
     * @return 
     */
    public float getSampleRate() {
        return this.audioFormat.getSampleRate();
    }
    
    /**
     * Returns true if the audio samples are stored in big-endian form and false
     * if they are stored in little-endian form.
     * 
     * @return 
     */
    public boolean isBigEndian() {
        return this.audioFormat.isBigEndian();
    }

    /**
     * Returns the DynamicArray object used to store the audio's bytes as words.
     * 
     * @return 
     */
    public DynamicArray<Integer> getWords() {
        return words;
    }

    /**
     * Get the audio-specific analysis data as AudioAnalysis object.
     * 
     * @return 
     */
    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    /**
     * Get the AudioFormat object.
     * 
     * @return 
     */
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }
    
}
