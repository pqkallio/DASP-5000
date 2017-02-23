
package dasp5000.domain;

/**
 *
 * @author Petri Kallio
 */
public class SpectrumAnalysisSample {
    private int sampleStart;
    private int windowSize;
    private LoudnessSample[] samples;
    private int sampleIndex;

    public SpectrumAnalysisSample(int sampleStart, int windowSize) {
        this.sampleStart = sampleStart;
        this.windowSize = windowSize;
        this.samples = new LoudnessSample[windowSize / 2];
        this.sampleIndex = 0;
    }
    
    public void addLoudnessSample(LoudnessSample sample) {
        this.samples[sampleIndex] = sample;
    }

    public LoudnessSample[] getSamples() {
        return samples;
    }
}
