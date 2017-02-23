
package dasp5000.domain;

/**
 *
 * @author Petri Kallio
 */
public class LoudnessSample implements Comparable<LoudnessSample>{
    private int startSample;
    private int windowSize;
    private double frequency;
    private double[] magnitude;

    public LoudnessSample(int sample, int windowSize, double frequency, 
            double... data) {
        this.startSample = sample;
        this.windowSize = windowSize;
        this.frequency = frequency;
        this.magnitude = insertData(data);
    }    

    private double[] insertData(double... data) {
        double[] mags = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            mags[i] = data[i];
        }
        return mags;
    }

    public double getFrequency() {
        return frequency;
    }

    public double[] getMagnitude() {
        return magnitude;
    }

    @Override
    public int compareTo(LoudnessSample o) {
        int dif = this.startSample - o.startSample;
        if (dif != 0) {
            return dif;
        }
        return (int)(this.frequency - o.frequency);
    }
}
