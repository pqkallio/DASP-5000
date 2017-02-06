
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
public class StereoAudio implements AudioContainer {
    private AudioAnalysis audioAnalysis;
    private DynamicArray<Integer>[] audioData;
    private final AudioFormat audioFormat;

    /**
     * Creates a new AudioContainer object.
     * 
     * @param audioFormat The AudioFormat object of the corresponding AudioInputStream.
     */
    public StereoAudio(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
        this.audioData = new DynamicArray[2];
    }
    
    /**
     * Sets the AudioAnalysis object that contains important data used in 
     * processing the audio data.
     * 
     * @param audioAnalysis AudioAnalysis object
     */
    @Override
    public void setAudioAnalysis(AudioAnalysis audioAnalysis) {
        this.audioAnalysis = audioAnalysis;
    }

    /**
     * Sets the array of DynamicArrays that contains all the channels of the 
     * audio.
     * 
     * @param audioData an array of DynamicArrays of integers
     */
    @Override
    public void setChannels(DynamicArray<Integer>[] audioData) {
        this.audioData[0] = audioData[0];
        this.audioData[1] = audioData[1];
    }
    
    /**
     * Get the number of channels of the audio data.
     * 
     * @return the number of channels
     */
    @Override
    public int getNumberOfChannels() {
        return this.audioFormat.getChannels();
    }
    
    /**
     * Get the amount of bits used to record one audio sample.
     * 
     * @return the number of bits per sample
     */
    @Override
    public int getBitsPerAudioSample() {
        return this.audioFormat.getSampleSizeInBits();
    }
    
    /**
     * Get the sample rate of the audio (e.g. 44.1 kHz or 44100 samples per second.)
     * 
     * @return the sample rate
     */
    @Override
    public float getSampleRate() {
        return this.audioFormat.getSampleRate();
    }
    
    /**
     * Returns the endiannes of the audio data.
     * 
     * @return true if big-endian, false if little-endian
     */
    @Override
    public boolean isBigEndian() {
        return this.audioFormat.isBigEndian();
    }

    /**
     * Returns the DynamicArray object used to store the audio's left channel's 
     * data.
     * 
     * @return a DynamicArray of integers
     */
    @Override
    public DynamicArray<Integer> getLeftChannel() {
        return audioData[0];
    }
    
    /**
     * Returns the DynamicArray object used to store the audio's right channel's 
     * data.
     * 
     * @return a DynamicArray of integers
     */
    @Override
    public DynamicArray<Integer> getRightChannel() {
        return audioData[1];
    }

    /**
     * Get the audio-specific analysis data as AudioAnalysis object.
     * 
     * @return AudioAnalysis object
     */
    @Override
    public AudioAnalysis getAudioAnalysis() {
        return audioAnalysis;
    }

    /**
     * Get the AudioFormat object used to store administrative information about 
     * the audio.
     * 
     * @return AudioFormat object
     */
    @Override
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    /**
     * Sets the DynamicArray object used to store the audio's left channel's 
     * data.
     * 
     * @param audioData a DynamicArray of integers
     */
    @Override
    public void setLeftChannel(DynamicArray<Integer> audioData) {
        this.audioData[0] = audioData;
    }

    /**
     * Sets the DynamicArray object used to store the audio's right channel's 
     * data.
     * 
     * @param audioData a DynamicArray of integers
     */
    @Override
    public void setRightChannel(DynamicArray<Integer> audioData) {
        this.audioData[1] = audioData;
    }

    /**
     * Get the audio data as an array of DynamicArrays of integers
     * 
     * @return an array of DynamicArrays of integers
     */
    @Override
    public DynamicArray<Integer>[] getChannels() {
        return this.audioData;
    }
    
}
