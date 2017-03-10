
package dasp5000.domain.audioprocessors;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.FFT;
import dasp5000.domain.LoudnessSample;
import dasp5000.domain.SpectrumAnalysisSample;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.utils.DecibelConverter;

/**
 * A Spectrum analyzer.
 *
 * @author Petri Kallio
 */
public class SpectrumAnalyzer {
    private AudioContainer audioContainer;
    private double[][] channelData;
    private double[][] imaginaryData;
    private int numChannels;
    private int samplesPerChannel;
    private int windowSize;
    private int windowsToCreate;
    private int channelLength;
    private int sampleRate;

    /**
     * Constructor
     * 
     * @param audioContainer the AudioContainer
     */
    public SpectrumAnalyzer(AudioContainer audioContainer) {
        this.windowSize = (int)Math.pow(2, 15);
        this.audioContainer = audioContainer;
        this.numChannels = audioContainer.getNumberOfChannels();
        this.samplesPerChannel = audioContainer.getSamplesPerChannel();
        this.sampleRate = audioContainer.getSampleRate();
        int maxSample = (int)Math.pow(2, this.audioContainer.getBitsPerAudioSample()) / 2;
        DynamicArray<Integer>[] channels = audioContainer.getChannels();
        this.windowsToCreate = (int)(Math.ceil(1.0 * samplesPerChannel / this.windowSize));
        this.channelLength = windowsToCreate * windowSize;
        this.channelData = new double[numChannels][channelLength];
        this.imaginaryData = new double[numChannels][channelLength];
        int i;
        for (i = 0; i < channels[0].size(); i++) {
            for (int j = 0; j < numChannels; j++) {
                channelData[j][i] = 1.0 * channels[j].get(i) / maxSample;
                imaginaryData[j][i] = 0;
            }
        }
        for (; i < this.channelData[0].length; i++) {
            for (int j = 0; j < numChannels; j++) {
                channelData[j][i] = 0;
                imaginaryData[j][i] = 0;
            }
        }
    }
    
    /**
     * Analyse a AudioContainer's audio data
     */
    public void process() {
        FFT fft = new FFT(windowSize);
        for (int i = 0; i < channelLength; i += windowSize) {
            for (int j = 0; j < numChannels; j++) {
                fft.transform(channelData[j], imaginaryData[j], i);
            }
        }
    }

    /**
     * Get the window size
     * 
     * @return the window size
     */
    public int getWindowSize() {
        return windowSize;
    }
    
    /**
     * Get the analysis as an array of SpectrumAnalysisSamples
     * 
     * @return an array of SpectrumAnalysisSamples
     */
    public SpectrumAnalysisSample[] getAnalysis() {
        double max = findMax();
        SpectrumAnalysisSample[] analysis = new SpectrumAnalysisSample[windowsToCreate];
        int analysisIndex = 0;
        for (int i = 0; i < channelData[0].length; i += windowSize) {
            SpectrumAnalysisSample sas = new SpectrumAnalysisSample(i, windowSize);
            LoudnessSample ls = null;
            for (int j = i; j < i + windowSize / 2; j++) {
                double[] magnitudes = new double[channelData.length];
                double freq = ((j - i) * sampleRate / 2) / (windowSize / 2);
                for (int k = 0; k < channelData.length; k++) {
                    double magnitude = Math.sqrt(channelData[k][j] * 
                                                 channelData[k][j] + 
                                                 imaginaryData[k][j] * 
                                                 imaginaryData[k][j]);
                    
                    magnitudes[k] = DecibelConverter.fftMagnitudeToDecibels(magnitude, max);
                }
                ls = new LoudnessSample(i, windowSize, freq, magnitudes);
                sas.addLoudnessSample(ls);
            }
            analysis[analysisIndex] = sas;
            analysisIndex++;
        }
        return analysis;
    }

    /**
     * Returns the amount of samples per channel
     * 
     * @return samples per channel
     */
    public int getSamplesPerChannel() {
        return samplesPerChannel;
    }

    /**
     * Get the audio container
     * 
     * @return AudioContainer
     */
    public AudioContainer getAudioContainer() {
        return audioContainer;
    }

    private double findMax() {
        double max = 0;
        for (int i = 0; i < channelData[0].length; i += windowSize) {
            for (int j = i; j < i + windowSize / 2; j++) {
                for (int k = 0; k < channelData.length; k++) {
                    double magnitude = Math.sqrt(channelData[k][j] * 
                                                 channelData[k][j] + 
                                                 imaginaryData[k][j] * 
                                                 imaginaryData[k][j]);
                    
                    if (magnitude > max) {
                        max = magnitude;
                    }
                }
            }
        }
        return max;
    }
}
