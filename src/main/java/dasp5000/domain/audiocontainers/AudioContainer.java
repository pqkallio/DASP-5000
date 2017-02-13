
package dasp5000.domain.audiocontainers;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import javax.sound.sampled.AudioFormat;

/**
 * An object to handle audio data.
 *
 * @author Petri Kallio
 */
public class AudioContainer {
    private AudioAnalysis audioAnalysis;
    private final DynamicArray<Integer>[] audioData;
    private final AudioFormat audioFormat;
    private AudioHeader audioHeader;

    /**
     * Creates a new AudioContainer object.
     * 
     * @param audioFormat The AudioFormat object of the corresponding AudioInputStream.
     */
    public AudioContainer(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
        this.audioData = new DynamicArray[1];
        this.audioHeader = null;
    }
    
    /**
     * Sets the AudioAnalysis object that contains important data used in 
     * processing the audio data.
     * 
     * @param audioAnalysis AudioAnalysis object
     */
    public void setAudioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
    }

    /**
     * Sets the DynamicArray that contains all the words that are composed of
     * a AudioInputStream's bytes based on its bit depth (e.g. 16 bits per sample).
     * 
     * @param audioData Contains bytes combined as words
     */
    public void setChannels(DynamicArray<Integer>[] audioData) {
        this.audioData[0] = audioData[0];
    }
    
    /**
     * Get the number of channels of the audio data.
     * 
     * @return the number of channels
     */
    public int getNumberOfChannels() {
        return this.audioFormat.getChannels();
    }
    
    /**
     * Get the amount of bits used to record one audio sample.
     * 
     * @return the amount of bits per sample
     */
    public int getBitsPerAudioSample() {
        return this.audioFormat.getSampleSizeInBits();
    }
    
    /**
     * Get the sample rate of the audio (e.g. 44.1 kHz or 44100 samples per second.)
     * 
     * @return the sample rate
     */
    public float getSampleRate() {
        return this.audioFormat.getSampleRate();
    }
    
    /**
     * Returns the endianness of the audio samples.
     * 
     * @return true if big-endian, false if little-endian
     */
    public boolean isBigEndian() {
        return this.audioFormat.isBigEndian();
    }

    /**
     * Returns the DynamicArray object used to store the left channel's audio as 
     * integers.
     * 
     * @return a DynamicArray of integers
     */
    public DynamicArray<Integer> getLeftChannel() {
        return audioData[0];
    }
    
    /**
     * Returns the DynamicArray object used to store the right channel's audio as 
     * integers.
     * 
     * @return a DynamicArray of integers
     */
    public DynamicArray<Integer> getRightChannel() {
        return audioData[0];
    }
    
    /**
     * Get the audio-specific analysis data as AudioAnalysis object.
     * 
     * @return AudioAnalysis object
     */
    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    /**
     * Get the AudioFormat object that contains administrational information 
     * about the audio.
     * 
     * @return AudioFormat object
     */
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    /**
     * Set the left audio channel's data.
     * 
     * @param audioData a DynamicArray of integers
     */
    public void setLeftChannel(DynamicArray<Integer> audioData) {
        this.audioData[0] = audioData;
    }

    /**
     * Set the right audio channel's data.
     * 
     * @param audioData a DynamicArray of integers
     */
    public void setRightChannel(DynamicArray<Integer> audioData) {
        this.audioData[0] = audioData;
    }

    /**
     * Get all the audio data stored with the AudioContainer.
     * 
     * @return an array of DynamicArrays
     */
    public DynamicArray<Integer>[] getChannels() {
        return audioData;
    }
    
    /**
     * Returns AudioHeader object
     * 
     * @return AudioHeader object
     */
    public AudioHeader getAudioHeader() {
        return audioHeader;
    }

    /**
     * Set AudioHeader object
     * 
     * @param audioHeader AudioHeader object
     */
    public void setAudioHeader(AudioHeader audioHeader) {
        this.audioHeader = audioHeader;
    }
}