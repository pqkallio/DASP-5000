
package dasp5000.domain.audioprocessors;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.FFT;
import dasp5000.domain.LoudnessSample;
import dasp5000.domain.SpectrumAnalysisSample;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
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

    public SpectrumAnalyzer(AudioContainer audioContainer) {
        this.windowSize = (int)Math.pow(2, 15);
        this.audioContainer = audioContainer;
        this.numChannels = audioContainer.getNumberOfChannels();
        this.samplesPerChannel = audioContainer.getSamplesPerChannel();
        int sampleRate = audioContainer.getSampleRate();
        int maxSample = (int)Math.pow(2, this.audioContainer.getBitsPerAudioSample()) / 2;
        DynamicArray<Integer>[] channels = audioContainer.getChannels();
        double windowsPerChannel = 1.0 * samplesPerChannel / this.windowSize;
        this.windowsToCreate = (int)(Math.ceil(1.0 * samplesPerChannel / this.windowSize));
        this.channelLength = windowsToCreate * windowSize;
        this.channelData = new double[numChannels][channelLength];
        this.imaginaryData = new double[numChannels][channelLength];
        int i = 0;
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
    
    public void process() {
        FFT fft = new FFT(windowSize);
        for (int i = 0; i < channelLength; i += windowSize) {
            for (int j = 0; j < numChannels; j++) {
                fft.fft(channelData[j], imaginaryData[j], i);
            }
        }
    }

    public double[][] getChannelData() {
        return channelData;
    }

    public double[][] getImaginaryData() {
        return imaginaryData;
    }

    public int getWindowSize() {
        return windowSize;
    }
    
    public SpectrumAnalysisSample[] getAnalysis() {
        SpectrumAnalysisSample[] analysis = new SpectrumAnalysisSample[windowsToCreate];
        int analysisIndex = 0;
        for (int i = 0; i < channelData[0].length; i += windowSize) {
            SpectrumAnalysisSample sas = new SpectrumAnalysisSample(i, windowSize);
            LoudnessSample ls = null;
            for (int j = i; j < i + windowSize / 2; j++) {
                double[] magnitudes = new double[channelData.length];
                double freq = ((j - i) * 44100 / 2) / (windowSize / 2);
                for (int k = 0; k < channelData.length; k++) {
                    double magnitude = Math.sqrt(channelData[k][j] * 
                                                 channelData[k][j] + 
                                                 imaginaryData[k][j] * 
                                                 imaginaryData[k][j]);
                    
                    magnitudes[k] = magnitude;
                }
                ls = new LoudnessSample(i, windowSize, freq, magnitudes);
                sas.addLoudnessSample(ls);
            }
            analysis[analysisIndex] = sas;
            analysisIndex++;
        }
        return analysis;
    }
}
