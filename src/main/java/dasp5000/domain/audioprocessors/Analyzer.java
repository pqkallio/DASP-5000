
package dasp5000.domain.audioprocessors;

public class Analyzer implements AudioProcessor {
    private long bytesRead;
    private int peakValue;
    private int minVal;
    private long sqrSum;
    
    public Analyzer() {
        this.bytesRead = 0;
        this.peakValue = 0;
        this.minVal = 32768;
        this.sqrSum = 0;
    }
    
    @Override
    public void Process(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            int val = Math.abs(bytes[i]);
            if (val > this.peakValue) {
                this.peakValue = bytes[i];
            }
            if (val < this.minVal) {
                this.minVal = bytes[i];
            }
            this.sqrSum += Math.pow(val, 2);
            this.bytesRead++;
        }
    }

    public int getPeakValue() {
        return peakValue;
    }
    
    public int getRMS() {
        return (int)(Math.sqrt(this.sqrSum / this.bytesRead));
    }
    
}
