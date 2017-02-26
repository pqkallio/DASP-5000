
package dasp5000.domain;

/**
 * A single moment's samples' frequency and magnitude.
 *
 * @author Petri Kallio
 */
public class LoudnessSample implements Comparable<LoudnessSample>{
    private int index;
    private int windowSize;
    private double frequency;
    private double[] magnitude;

    /**
     * Create a new loudness sample.
     * 
     * @param index the samples' index
     * @param windowSize window size
     * @param frequency the frequency
     * @param data the magnitudes on every channel
     */
    public LoudnessSample(int index, int windowSize, double frequency, 
            double... data) {
        this.index = index;
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

    /**
     * Get the frequency.
     * 
     * @return the frequency
     */
    public double getFrequency() {
        return frequency;
    }

    /**
     * Get the magnitudes.
     * 
     * @return the magnitudes
     */
    public double[] getMagnitude() {
        return magnitude;
    }

    /**
     * Compare the LoudnessSample with another. If the samples' indices are 
     * equal, then the comparison is based on the samples' frequencies.
     * 
     * @param o
     * @return integer
     */
    @Override
    public int compareTo(LoudnessSample o) {
        int dif = this.index - o.index;
        if (dif != 0) {
            return dif;
        }
        return (int)(this.frequency - o.frequency);
    }
}
