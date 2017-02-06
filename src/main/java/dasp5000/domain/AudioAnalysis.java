
package dasp5000.domain;

/**
 * A class that serves as a container to store the data obtained from analysing
 * audio data.
 * 
 * @author Petri Kallio
 */
public class AudioAnalysis {
    private final int peakSampleValue;
    private final int minimumSampleValue;
    private final long samples;

    /**
     * Constructor that creates a new AudioAnalysis object and saves the values
     * given as parameters for further use.
     * 
     * @param peakSampleValue The maximum sample value measured from audio data
     * @param minimumSampleValue The minimum sample value measured from audio data
     * @param samples The amount of samples that the audio data consists of
     */
    public AudioAnalysis(int peakSampleValue, int minimumSampleValue,
            long samples) {
        this.peakSampleValue = peakSampleValue;
        this.minimumSampleValue = minimumSampleValue;
        this.samples = samples;
    }

    /**
     * Returns the minimum sample value of the analysed audio.
     * 
     * @return the minimum sample value
     */
    public int getMinimumSampleValue() {
        return minimumSampleValue;
    }

    /**
     * Returns the peak (i.e. maximum) sample value of the analysed audio.
     * 
     * @return the peak sample value
     */
    public int getPeakSampleValue() {
        return peakSampleValue;
    }

    /**
     * Returns the amount of samples in audio data.
     * 
     * @return the amount of samples
     */
    public long getSamples() {
        return samples;
    }
}