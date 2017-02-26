
package dasp5000.domain;

/**
 * A class to transform an amount of samples to time.
 * 
 * @author Petri Kallio
 */
public class Duration {
    private int samples;
    private int samplingFrequency;

    /**
     * The constructor.
     * 
     * @param samples the amount of samples
     * @param samplingFrequency samples per second
     */
    public Duration(int samples, int samplingFrequency) {
        this.samples = samples;
        this.samplingFrequency = samplingFrequency;
    }
    
    /**
     * Get the rounded down hours of the duration.
     * 
     * @return rounded down hours
     */
    public int getHours() {
        return (this.samples / this.samplingFrequency) / 3600;
    }
    
    /**
     * Get the rounded down minutes of the duration, hours excluded.
     * 
     * @return rounded down minutes
     */
    public int getMinutes() {
        return ((this.samples / this.samplingFrequency) % 3600) / 60;
    }
    
    /**
     * Get the seconds of the duration, minutes and hours excluded.
     * 
     * @return 
     */
    public double getSeconds() {
        return (1.0 * this.samples / this.samplingFrequency) % 60;
    }

    /**
     * Get the samples.
     * 
     * @return the samples
     */
    public int getSamples() {
        return samples;
    }

    /**
     * Get the sampling frequency.
     * 
     * @return the sampling frequency
     */
    public int getSamplingFrequency() {
        return samplingFrequency;
    }

    /**
     * Set the samples.
     * 
     * @param samples the samples
     */
    public void setSamples(int samples) {
        this.samples = samples;
    }

    /**
     * Set the sampling frequency
     * 
     * @param samplingFrequency the sampling frequency
     */
    public void setSamplingFrequency(int samplingFrequency) {
        this.samplingFrequency = samplingFrequency;
    }

    /**
     * Get the string representation of the duration.
     * 
     * @return the string representation of the duration
     */
    @Override
    public String toString() {
        int hours = getHours();
        int mins = getMinutes();
        double secs = getSeconds();
        String string = addZeroIfNeeded(hours) + hours + ":";
        string += addZeroIfNeeded(mins) + mins + ":";
        string += addZeroIfNeeded(secs) + String.format("%.3f", secs);
        return string;
    }

    private String addZeroIfNeeded(double integer) {
        if (integer < 10) {
            return "0";
        }
        return "";
    }
}
