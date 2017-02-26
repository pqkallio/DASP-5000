
package dasp5000.domain;

/**
 * A sample that contains all the LoudnessSamples in defined moment of time.
 *
 * @author Petri Kallio
 */
public class SpectrumAnalysisSample {
    private int sampleStart;
    private int windowSize;
    private LoudnessSample[] samples;
    private int sampleIndex;

    /**
     * Constructor
     * 
     * @param sampleStart the starting point of the sample
     * @param windowSize the sample's window size
     */
    public SpectrumAnalysisSample(int sampleStart, int windowSize) {
        this.sampleStart = sampleStart;
        this.windowSize = windowSize;
        this.samples = new LoudnessSample[windowSize / 2];
        this.sampleIndex = 0;
    }
    
    /**
     * Adds a loudness sample.
     * 
     * @param sample a LoudnessSample
     */
    public void addLoudnessSample(LoudnessSample sample) {
        this.samples[sampleIndex] = sample;
        sampleIndex++;
    }

    /**
     * Get the LoudnessSamples as an array.
     * 
     * @return an array of LoudnessSamples
     */
    public LoudnessSample[] getSamples() {
        return samples;
    }

    /**
     * Get the sample's starting point.
     * 
     * @return the starting point
     */
    public int getSampleStart() {
        return sampleStart;
    }
}
