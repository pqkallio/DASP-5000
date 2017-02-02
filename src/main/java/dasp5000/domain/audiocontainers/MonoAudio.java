
package dasp5000.domain.audiocontainers;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.DynamicArray;
import javax.sound.sampled.AudioFormat;

/**
 * A class to contain all the information needed to process an opened 
 * AudioInputStream's data.
 * 
 * @author Petri Kallio
 */
public class MonoAudio implements AudioContainer {
    private AudioAnalysis audioAnalysis;
    private DynamicArray<Integer> audioData;
    private final AudioFormat audioFormat;

    /**
     * Creates a new AudioContainer object.
     * 
     * @param audioFormat The AudioFormat object of the corresponding AudioInputStream.
     */
    public MonoAudio(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }
    
    /**
     * Sets the AudioAnalysis object that contains important data used in 
     * processing the audio data.
     * 
     * @param audioAnalysis 
     */
    @Override
    public void setAudioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
    }

    /**
     * Sets the DynamicArray that contains all the words that are composed of
     * a AudioInputStream's bytes based on its bit depth (e.g. 16 bits per sample).
     * 
     * @param audioData Contains bytes combined as words
     */
    @Override
    public void setAudioData(DynamicArray<Integer>... audioData) {
        this.audioData = audioData[0];
    }
    
    /**
     * Get the number of channels of the audio data.
     * 
     * @return 
     */
    @Override
    public int getNumberOfChannels() {
        return this.audioFormat.getChannels();
    }
    
    /**
     * Get the bit depth of the audio, meaning the amount of bits used to 
     * represent one audio sample.
     * 
     * @return 
     */
    @Override
    public int getBitsPerAudioSample() {
        return this.audioFormat.getSampleSizeInBits();
    }
    
    /**
     * Get the sample rate of the audio (e.g. 44.1 kHz or 44100 samples per second.
     * 
     * @return 
     */
    @Override
    public float getSampleRate() {
        return this.audioFormat.getSampleRate();
    }
    
    /**
     * Returns true if the audio samples are stored in big-endian form and false
     * if they are stored in little-endian form.
     * 
     * @return 
     */
    @Override
    public boolean isBigEndian() {
        return this.audioFormat.isBigEndian();
    }

    /**
     * Returns the DynamicArray object used to store the audio's bytes as words.
     * 
     * @return 
     */
    @Override
    public DynamicArray<Integer> getLeftChannel() {
        return audioData;
    }
    
    @Override
    public DynamicArray<Integer> getRightChannel() {
        return audioData;
    }
    
    /**
     * Get the audio-specific analysis data as AudioAnalysis object.
     * 
     * @return 
     */
    @Override
    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    /**
     * Get the AudioFormat object.
     * 
     * @return 
     */
    @Override
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    @Override
    public void setLeftChannel(DynamicArray<Integer> audioData) {
        this.audioData = audioData;
    }

    @Override
    public void setRightChannel(DynamicArray<Integer> audioData) {
        this.audioData = audioData;
    }
    
}
