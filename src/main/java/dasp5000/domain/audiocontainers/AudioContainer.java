
package dasp5000.domain.audiocontainers;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;

/**
 * An object to handle audio data.
 *
 * @author Petri Kallio
 */
public class AudioContainer {
    private AudioAnalysis audioAnalysis;
    private DynamicArray<Integer>[] audioData;
    private AudioHeader audioHeader;

    /**
     * A constructor with no AudioHeader.
     */
    public AudioContainer() {
        this(null);
    }
    
    /**
     * A constructor with AudioHeader
     * @param header the AudioHeader object
     */
    public AudioContainer(AudioHeader header) {
        if (header != null) {
            this.audioData = new DynamicArray[header.getNumberOfChannels()];
        }
        this.audioHeader = header;
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
        this.audioData = audioData;
    }
    
    /**
     * Get the number of channels of the audio data.
     * 
     * @return the number of channels
     */
    public int getNumberOfChannels() {
        return this.audioHeader.getNumberOfChannels();
    }
    
    /**
     * Get the amount of bits used to record one audio sample.
     * 
     * @return the amount of bits per sample
     */
    public int getBitsPerAudioSample() {
        return this.audioHeader.getBitsPerSample();
    }
    
    /**
     * Get the sample rate of the audio (e.g. 44.1 kHz or 44100 samples per second.)
     * 
     * @return the sample rate
     */
    public int getSampleRate() {
        return this.audioHeader.getSampleRate();
    }

    /**
     * Returns the DynamicArray object used to store the left channel's audio as 
     * integers.
     * 
     * @return a DynamicArray of integers
     */
    public DynamicArray<Integer> getLeftChannel() {
        if (checkAudioData(0)) {
            return audioData[0];
        }
        return null;
    }
    
    /**
     * Returns the DynamicArray object used to store the right channel's audio as 
     * integers.
     * 
     * @return a DynamicArray of integers
     */
    public DynamicArray<Integer> getRightChannel() {
        if (getNumberOfChannels() == 1) {
            return audioData[0];
        }
        if (checkAudioData(1)) {
            return audioData[1];
        }
        return null;
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
     * Set the left audio channel's data.
     * 
     * @param audioData a DynamicArray of integers
     */
    public void setLeftChannel(DynamicArray<Integer> audioData) {
        if (checkAudioData(0)) {
            this.audioData[0] = audioData;
        }
    }

    /**
     * Set the right audio channel's data.
     * 
     * @param audioData a DynamicArray of integers
     */
    public void setRightChannel(DynamicArray<Integer> audioData) {
        if (checkAudioData(1)) {
            this.audioData[1] = audioData;
        }
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
        if (this.audioData == null) {
            this.audioData = new DynamicArray[audioHeader.getNumberOfChannels()];
        }
    }
    
    /**
     * Get the amount of samples per channel
     * 
     * @return samples per channel
     */
    public int getSamplesPerChannel() {
        if (this.audioData == null || this.audioData.length == 0) {
            return 0;
        }
        
        return this.audioData[0].size();
    }

    private boolean checkAudioData(int i) {
        if (this.audioData == null || this.audioData.length <= i) {
            return false;
        }
        return true;
    }
}