
package dasp5000.domain.audiocontainers;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.DynamicArray;
import javax.sound.sampled.AudioFormat;

/**
 * An interface that all the classes that store information about audio must
 * implement.
 *
 * @author Petri Kallio
 */
public interface AudioContainer {
    /**
     * Set the AudioAnalysis object of the AudioContainer object
     * 
     * @param audioAnalysis AudioAnalysis object
     */
    public void setAudioAnalysis(AudioAnalysis audioAnalysis);
    
    /**
     * Set the AudioContainer object's audio channel data by passing an array 
     * of DynamicArrays.
     * 
     * @param channels DynamicArrays containing the Audio .
     */
    public void setChannels(DynamicArray<Integer>[] channels);
    
    /**
     * Get the number of channels of the AudioContainer
     * 
     * @return the number of channels
     */
    public int getNumberOfChannels();
    
    /**
     * Get the amount of bits used to store a single audio sample.
     * 
     * @return the number of bits per audio sample
     */
    public int getBitsPerAudioSample();
    
    /**
     * Get the amount of samples stored to record one second of audio.
     * 
     * @return the sample rate
     */
    public float getSampleRate();
    
    /**
     * Get the endianness of the audio data.
     * 
     * @return true if big-endian, false if little-endian
     */
    public boolean isBigEndian();
    
    /**
     * Get the audio's AudioAnalysis object
     * 
     * @return AudioAnalysis object
     */
    public AudioAnalysis getAudioAnalysis();
    
    /**
     * Get the audio's AudioFormat object.
     * 
     * @return AudioFormat object
     */
    public AudioFormat getAudioFormat();
    
    /**
     * Set the left channel's data.
     * 
     * @param audioData the audio samples as a DynamicArray
     */
    public void setLeftChannel(DynamicArray<Integer> audioData);
    
    /**
     * Set the right channel's data.
     * 
     * @param audioData the audio samples as a DynamicArray
     */
    public void setRightChannel(DynamicArray<Integer> audioData);
    
    /**
     * Get the left channel's data.
     * 
     * @return a Dynamic Array of samples
     */
    public DynamicArray<Integer> getLeftChannel();
    
    /**
     * Get the right channel's data.
     * 
     * @return a Dynamic Array of samples
     */
    public DynamicArray<Integer> getRightChannel();
    
    /**
     * Get the AudioContainer's audio channel's samples as an array of 
     * DynamicArrays.
     * 
     * @return an array of DynamicArrays
     */
    public DynamicArray<Integer>[] getChannels();
}