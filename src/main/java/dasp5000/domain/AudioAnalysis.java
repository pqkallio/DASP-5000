
package dasp5000.domain;

public class AudioAnalysis {
    private final int peakSampleValue;
    private final int minimumSampleValue;
    private final long samples;

    public AudioAnalysis(int peakSampleValue, int minimumSampleValue,
            long samples) {
        this.peakSampleValue = peakSampleValue;
        this.minimumSampleValue = minimumSampleValue;
        this.samples = samples;
    }

    public int getMinimumSampleValue() {
        return minimumSampleValue;
    }

    public int getPeakSampleValue() {
        return peakSampleValue;
    }

    public long getSamples() {
        return samples;
    }
}