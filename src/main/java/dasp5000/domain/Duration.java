
package dasp5000.domain;

/**
 *
 * 
 * @author Petri Kallio
 */
public class Duration {
    private int samples;
    private int samplingFrequency;

    public Duration(int samples, int samplingFrequency) {
        this.samples = samples;
        this.samplingFrequency = samplingFrequency;
    }
    
    public int getHours() {
        return (this.samples / this.samplingFrequency) / 3600;
    }
    
    public int getMinutes() {
        return ((this.samples / this.samplingFrequency) % 3600) / 60;
    }
    
    public double getSeconds() {
        return (1.0 * this.samples / this.samplingFrequency) % 60;
    }

    public int getSamples() {
        return samples;
    }

    public int getSamplingFrequency() {
        return samplingFrequency;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public void setSamplingFrequency(int samplingFrequency) {
        this.samplingFrequency = samplingFrequency;
    }

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
