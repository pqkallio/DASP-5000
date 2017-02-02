
package dasp5000.domain.audiocontainers;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.DynamicArray;
import javax.sound.sampled.AudioFormat;

/**
 *
 * @author Petri Kallio
 */
public interface AudioContainer {
    public void setAudioAnalysis(AudioAnalysis audioAnalysis);
    public void setAudioData(DynamicArray<Integer>... audioData);
    public int getNumberOfChannels();
    public int getBitsPerAudioSample();
    public float getSampleRate();
    public boolean isBigEndian();
    public AudioAnalysis getAudioAnalysis();
    public AudioFormat getAudioFormat();
    public void setLeftChannel(DynamicArray<Integer> audioData);
    public void setRightChannel(DynamicArray<Integer> audioData);
    public DynamicArray<Integer> getLeftChannel();
    public DynamicArray<Integer> getRightChannel();
}