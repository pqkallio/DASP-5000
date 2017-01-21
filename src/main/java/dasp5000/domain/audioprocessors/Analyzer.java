
package dasp5000.domain.audioprocessors;

public class Analyzer implements AudioProcessor {
    private long bytesRead;
    private int peakValue;
    private int minValue;
    private long squareSum;
    
    public Analyzer() {
        this.bytesRead = 0;
        this.peakValue = 0;
        this.minValue = 32768;
        this.squareSum = 0;
    }
    
    @Override
    public void Process(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            int val = Math.abs(bytes[i]);
            if (val > this.peakValue) {
                this.peakValue = bytes[i];
            }
            if (val < this.minValue) {
                this.minValue = bytes[i];
            }
            this.squareSum += Math.pow(val, 2);
            this.bytesRead++;
        }
    }

    public int getPeakValue() {
        return peakValue;
    }
    
    public int getMinValue() {
        return minValue;
    }
    
    public int getRMS() {
        return (int)(Math.sqrt(this.squareSum / this.bytesRead));
    }
    
}
